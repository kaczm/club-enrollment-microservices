package org.bp.parking;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Random;

import org.bp.parking.model.CancelParkingRequest;
import org.bp.parking.model.Parking;
import org.bp.parking.model.ParkingException;
import org.bp.parking.model.ParkingRequest;
import org.bp.parking.model.ParkingResponse;
import org.springframework.web.bind.annotation.PathVariable;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@org.springframework.web.bind.annotation.RestController
public class ParkingService {
	@Operation(summary = "parking operation", description = "operation for reserving a parking", responses = {
			@ApiResponse(responseCode = "200", description = "OK", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ParkingResponse.class)) }),
			@ApiResponse(responseCode = "400", description = "Bad Request", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class)) }) })
	@org.springframework.web.bind.annotation.PostMapping("/parking")
	public ParkingResponse parking(@org.springframework.web.bind.annotation.RequestBody ParkingRequest parkingRequest) {
		if (parkingRequest != null && parkingRequest.getCar() != null
				&& "".equals(parkingRequest.getCar().getPlate())) {

			throw new ParkingException("Plate cant be empty!");

		}
		ParkingResponse paymentResponse = new ParkingResponse();
		paymentResponse.setValid(true);
		Random random = new Random();
		paymentResponse.setTransactionId(random.ints(1, 100).findFirst().getAsInt());
		return paymentResponse;
	}

	@org.springframework.web.bind.annotation.PostMapping("/parkingCancel")
	public ParkingResponse parkingCancel(
			@org.springframework.web.bind.annotation.RequestBody CancelParkingRequest cancelParkingRequest) {
		if (cancelParkingRequest != null && cancelParkingRequest.getTransactionId() == 0) {
			throw new ParkingException("Transaction ID cant be equal to 0!");
		}
		ParkingResponse paymentResponse = new ParkingResponse();
		paymentResponse.setValid(false);
		paymentResponse.setTransactionId(cancelParkingRequest.getTransactionId());
		return paymentResponse;
	}

	@org.springframework.web.bind.annotation.GetMapping("/parking/{id}")
	public Parking parkingWithID(@PathVariable("id") String id) {
		Parking parking = new Parking();
		if ("2".equals(id))
			parking.setOccupied(true);
		else
			parking.setOccupied(false);

		return parking;
	}

}
