package org.bp.club;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * This class was generated by Apache CXF 3.3.2
 * 2024-01-14T01:55:34.312+01:00
 * Generated source version: 3.3.2
 *
 */
@WebService(targetNamespace = "http://www.bp.org/club/", name = "ClubEnrollment")
@XmlSeeAlso({org.bp.ObjectFactory.class, org.bp.types.ObjectFactory.class})
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface ClubEnrollment {

    @WebMethod(action = "http://www.bp.org/club/cancelEnroll")
    @WebResult(name = "cancelEnrollResponse", targetNamespace = "http://www.bp.org", partName = "payload")
    public org.bp.types.EnrollmentInfo cancelEnroll(

        @WebParam(partName = "payload", name = "cancelEnrollRequest", targetNamespace = "http://www.bp.org")
        org.bp.CancelEnrollRequest payload
    ) throws ClubFaultMsg;

    @WebMethod(action = "http://www.bp.org/club/enrollClub")
    @WebResult(name = "enrollClubResponse", targetNamespace = "http://www.bp.org", partName = "payload")
    public org.bp.types.EnrollmentInfo enrollClub(

        @WebParam(partName = "payload", name = "enrollClubRequest", targetNamespace = "http://www.bp.org")
        org.bp.EnrollClubRequest payload
    ) throws ClubFaultMsg;
}
