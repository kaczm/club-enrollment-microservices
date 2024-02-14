package org.bp.club;

import java.util.Random;

import org.bp.CancelEnrollRequest;
import org.bp.EnrollClubRequest;
import org.bp.types.EnrollmentInfo;
import org.bp.types.Fault;

@org.springframework.stereotype.Service
public class ClubEnrollmentEndpoint implements ClubEnrollment {

	@Override
	public EnrollmentInfo cancelEnroll(CancelEnrollRequest payload) throws ClubFaultMsg {
		if(payload != null && payload.getEnrollmentId() != 0)
		{
			EnrollmentInfo enrollmentInfo = new EnrollmentInfo();
			enrollmentInfo.setId(payload.getEnrollmentId());
			enrollmentInfo.setCost(new java.math.BigDecimal(0));
			return enrollmentInfo;
		}
		return null;	
	}

	@Override
	public EnrollmentInfo enrollClub(EnrollClubRequest payload) throws ClubFaultMsg {
		if(payload != null && payload.getClub() != null && payload.getPerson() != null)
		{
			EnrollmentInfo enrollmentInfo = new EnrollmentInfo();
			Random random = new Random();
			enrollmentInfo.setId(random.ints(1, 100).findFirst().getAsInt());
			enrollmentInfo.setCost(new java.math.BigDecimal(1500));
			return enrollmentInfo;
		}
		
		Fault fault = new Fault();
		if(payload != null && payload.getPerson() != null && "Kuba".equals(payload.getPerson().getFirstName()))
		{
			fault.setCode(1);
			fault.setText("Kuba cannot be in a club!");
			ClubFaultMsg faultMsg = new ClubFaultMsg("Kuba cannot be in a club!", fault);
			throw faultMsg;
		}
		fault.setCode(2);
		fault.setText("You didn't enter all information!");
		ClubFaultMsg faultMsg = new ClubFaultMsg("You didn't enter all information!", fault);
		throw faultMsg;
	}

}
