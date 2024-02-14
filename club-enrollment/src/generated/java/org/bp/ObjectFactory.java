
package org.bp;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;
import org.bp.types.EnrollmentInfo;
import org.bp.types.Fault;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.bp package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _EnrollClubResponse_QNAME = new QName("http://www.bp.org", "enrollClubResponse");
    private final static QName _CancelEnrollResponse_QNAME = new QName("http://www.bp.org", "cancelEnrollResponse");
    private final static QName _ClubFault_QNAME = new QName("http://www.bp.org", "clubFault");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.bp
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link EnrollClubRequest }
     * 
     */
    public EnrollClubRequest createEnrollClubRequest() {
        return new EnrollClubRequest();
    }

    /**
     * Create an instance of {@link CancelEnrollRequest }
     * 
     */
    public CancelEnrollRequest createCancelEnrollRequest() {
        return new CancelEnrollRequest();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EnrollmentInfo }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link EnrollmentInfo }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.bp.org", name = "enrollClubResponse")
    public JAXBElement<EnrollmentInfo> createEnrollClubResponse(EnrollmentInfo value) {
        return new JAXBElement<EnrollmentInfo>(_EnrollClubResponse_QNAME, EnrollmentInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EnrollmentInfo }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link EnrollmentInfo }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.bp.org", name = "cancelEnrollResponse")
    public JAXBElement<EnrollmentInfo> createCancelEnrollResponse(EnrollmentInfo value) {
        return new JAXBElement<EnrollmentInfo>(_CancelEnrollResponse_QNAME, EnrollmentInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Fault }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Fault }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.bp.org", name = "clubFault")
    public JAXBElement<Fault> createClubFault(Fault value) {
        return new JAXBElement<Fault>(_ClubFault_QNAME, Fault.class, null, value);
    }

}
