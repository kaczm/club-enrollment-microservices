
package org.bp.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Enrollment complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Enrollment"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="for" type="{http://www.bp.org/types}Person"/&gt;
 *         &lt;element name="to" type="{http://www.bp.org/types}Club"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Enrollment", propOrder = {
    "_for",
    "to"
})
public class Enrollment {

    @XmlElement(name = "for", required = true)
    protected Person _for;
    @XmlElement(required = true)
    protected Club to;

    /**
     * Gets the value of the for property.
     * 
     * @return
     *     possible object is
     *     {@link Person }
     *     
     */
    public Person getFor() {
        return _for;
    }

    /**
     * Sets the value of the for property.
     * 
     * @param value
     *     allowed object is
     *     {@link Person }
     *     
     */
    public void setFor(Person value) {
        this._for = value;
    }

    /**
     * Gets the value of the to property.
     * 
     * @return
     *     possible object is
     *     {@link Club }
     *     
     */
    public Club getTo() {
        return to;
    }

    /**
     * Sets the value of the to property.
     * 
     * @param value
     *     allowed object is
     *     {@link Club }
     *     
     */
    public void setTo(Club value) {
        this.to = value;
    }

}
