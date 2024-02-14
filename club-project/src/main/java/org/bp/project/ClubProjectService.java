package org.bp.project;

import org.bp.parking.CancelParkingRequest;
import org.bp.parking.ParkingRequest;
import org.apache.camel.builder.RouteBuilder;
import static org.apache.camel.model.rest.RestParamType.body;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.model.SagaPropagation;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.commons.logging.Log;
import org.bp.project.model.ClubRequest;
import org.bp.project.model.ClubResponse;
import org.bp.parking.ParkingResponse;
import org.bp.CancelEnrollRequest;
import org.bp.club.EnrollmentInfo;
import org.bp.project.model.Utils;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import java.math.BigDecimal;
import java.util.Random;
import java.util.HashMap;
import java.util.UUID;

import org.springframework.stereotype.Service;

import org.springframework.stereotype.Component;

@Component
public class ClubProjectService extends RouteBuilder {
	@org.springframework.beans.factory.annotation.Autowired
	ClubIdentifierService clubIdentifierService;

	@org.springframework.beans.factory.annotation.Autowired
	CreateResponseService createResponseService;

	@org.springframework.beans.factory.annotation.Value("${club.kafka.server}")
	private String clubKafkaServer;
	@org.springframework.beans.factory.annotation.Value("${club.service.type}")
	private String clubServiceType;
	@org.springframework.beans.factory.annotation.Value("${club.kafka.soap}")
	private String clubKafkaSoap;
	
	@Override
	public void configure() throws Exception {
		if (clubServiceType.equals("all") || clubServiceType.equals("gateway"))
			gateway();
		if (clubServiceType.equals("all") || clubServiceType.equals("enrollment"))
			enrollment();
		if (clubServiceType.equals("all") || clubServiceType.equals("parking"))
			parking();
		if (clubServiceType.equals("all") || clubServiceType.equals("createResponse"))
			createResponse();
	}

	private void gateway() {
		HashMap<String, ClubResponse> answers = new HashMap<>();

		restConfiguration().component("servlet").bindingMode(RestBindingMode.auto)
				.dataFormatProperty("prettyPrint", "true").enableCORS(true).contextPath("/api")
				// turn on swagger api-doc
				.apiContextPath("/api-doc").apiProperty("api.title", "Club Project API")
				.apiProperty("api.version", "1.0.0");

		// ustawienie endpointa post /club/enroll
		rest("/club").description("Club REST service").consumes("application/json").produces("application/json")
				.post("/enroll").description("Enroll to the club with parking reservation").type(ClubRequest.class)
				.outType(ClubResponse.class).param().name("body").type(body).description("Enroll to a club").endParam()
				.responseMessage().code(200).message("Enroll to the club with parking reservation successfully done")
				.endResponseMessage().to("direct:clubAndParking");

		// ustawienie endpoint get /club/enroll/{id}
		rest("/club").description("Club REST service").consumes("application/json").produces("application/json")
				.get("/enroll/{id}").description("Get information about enrollmenet").outType(ClubResponse.class)
				.description("Get info about enrollment").responseMessage().code(200)
				.message("Got info about enrollment").endResponseMessage().to("direct:getAnswer");

		from("direct:clubAndParking").routeId("clubAndParking").log("clubAndParking fired").process((exchange) -> {
			exchange.getMessage().setHeader("clubId", clubIdentifierService.getClubIdentifier());
		}).to("direct:clubRequest").to("direct:clubRequester");

		from("direct:clubRequester").routeId("clubRequester").log("clubRequester fired").process((exchange) -> {
			exchange.getMessage()
					.setBody(Utils.prepareClubResponse(exchange.getMessage().getHeader("clubId", String.class)));
		});

		from("direct:clubRequest").routeId("clubRequest").log("brokerTopic fired").marshal().json()
				.to("kafka:ClubRequestTopic?brokers=" + clubKafkaServer + "&groupId=" + clubServiceType);

		from("kafka:AnswersTopic?brokers=" + clubKafkaServer + "&groupId=" + clubServiceType).unmarshal()
				.json(JsonLibrary.Jackson, ClubResponse.class)
				.log(LoggingLevel.INFO, "test", "ODCZYT Z ANSWERSTOPIC ${body} and ${headers}").process((exchange) -> {
					ClubResponse clubResponse = exchange.getMessage().getBody(ClubResponse.class);
					answers.put(clubResponse.getId(), clubResponse);
				});

		from("direct:getAnswer").process((exchange) -> {
			String id = exchange.getIn().getHeader("id", String.class);
			ClubResponse clubResponse = answers.get(id);
			exchange.getMessage().setBody(clubResponse);
		});
	}

	private void enrollment() {
		final JaxbDataFormat jaxbEnrollment = new JaxbDataFormat(
				org.bp.club.EnrollmentInfo.class.getPackage().getName());

		from("kafka:ClubRequestTopic?brokers=" + clubKafkaServer + "&groupId=" + clubServiceType).routeId("enrollment")
				.log("fired enrollment").unmarshal().json(JsonLibrary.Jackson, ClubRequest.class)
				.process((exchange) -> {
					exchange.getMessage()
							.setBody(Utils.prepareEnrollClubRequest(exchange.getMessage().getBody(ClubRequest.class)));
				}).marshal(jaxbEnrollment).to("spring-ws:http://"+clubKafkaSoap+"/soap-api/service/club")
				.unmarshal(jaxbEnrollment).process((exchange) -> {
					ClubResponse clubResponse = new ClubResponse();
					clubResponse.setId(exchange.getMessage().getHeader("clubId", String.class));
					EnrollmentInfo enrollmentInfo = exchange.getMessage().getBody(EnrollmentInfo.class);
					clubResponse.setEnrollmentInfo(enrollmentInfo);
					exchange.getMessage().setBody(clubResponse);
				}).marshal().json().log(LoggingLevel.INFO, "test", "ODEBRANE ZE SOAPA ${body} and ${headers}")
				.setHeader("serviceType", constant("enrollment"))
				.to("kafka:ClubResponseTopic?brokers=" + clubKafkaServer + "&groupId=" + clubServiceType);
	}

	private void parking() {
		from("kafka:ClubRequestTopic?brokers=" + clubKafkaServer + "&groupId=" + clubServiceType).routeId("parking")
				.log("fired parking").unmarshal().json(JsonLibrary.Jackson, ClubRequest.class).process((exchange) -> {
					ParkingResponse parkingResponse = new ParkingResponse();
					parkingResponse.setValid(true);
					Random random = new Random();
					parkingResponse.setTransactionId(random.ints(1, 100).findFirst().getAsInt());
					ClubResponse clubResponse = new ClubResponse();
					clubResponse.setId(exchange.getMessage().getHeader("clubId", String.class));
					clubResponse.setParkingResponse(parkingResponse);
					exchange.getMessage().setBody(clubResponse);
				}).marshal().json().log(LoggingLevel.INFO, "test", "PO PARKINGU: ${body} and ${headers}")
				.setHeader("serviceType", constant("parking"))
				.to("kafka:ClubResponseTopic?brokers=" + clubKafkaServer + "&groupId=" + clubServiceType);
	}

	private void createResponse() {
		from("kafka:ClubResponseTopic?brokers=" + clubKafkaServer + "&groupId=" + clubServiceType)
				.routeId("ClubResponse").log("fired ClubResponse")
				.log(LoggingLevel.INFO, "test", "${body} and ${headers}").unmarshal()
				.json(JsonLibrary.Jackson, ClubResponse.class).process((exchange) -> {
					String clubId = exchange.getMessage().getHeader("clubId", String.class);
					boolean isReady = createResponseService.addClubResponse(clubId,
							exchange.getMessage().getBody(ClubResponse.class),
							exchange.getMessage().getHeader("serviceType", String.class));
					exchange.getMessage().setHeader("isReady", isReady);
				}).choice().when(header("isReady").isEqualTo(true)).to("direct:FinalizeAnswer").endChoice();

		from("direct:FinalizeAnswer").routeId("FinalizeAnswer").log("fired FinalizeAnswer").process((exchange) -> {
			String clubId = exchange.getMessage().getHeader("clubId", String.class);
			CreateResponseService.Response response = createResponseService.getResponse(clubId);
			ParkingResponse parkingResponse = response.parkingResponse.getParkingResponse();
			EnrollmentInfo enrollmentInfo = response.enrollmentResponse.getEnrollmentInfo();
			ClubResponse clubResponse = new ClubResponse();
			clubResponse.setId(clubId);
			clubResponse.setEnrollmentInfo(enrollmentInfo);
			clubResponse.setParkingResponse(parkingResponse);
			exchange.getMessage().setBody(clubResponse);
		}).marshal().json().log(LoggingLevel.INFO, "test", "WYSYLAM DO ANSWERSTOPIC ${body} and ${headers}")
				.to("kafka:AnswersTopic?brokers=" + clubKafkaServer + "&groupId=" + clubServiceType);
	}

}
