package edu.sjsu.cmpe275.lab2.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

/**
 * @author Nakul Sharma
 * POJO class to hold Person's address details.
 */
@Embeddable
@XmlRootElement(name="Address")
@XmlType(propOrder = { "street", "city","state","zip" })
public class Address implements Serializable{

    /**
     * Name of street
     */
    @Column(name ="STREET")
    private String street;

    /**
     * Name of city
     */
    @Column (name = "CITY")
    private String city;

    /**
     * Name of state
     */
    @Column(name = "STATE")
    private String state;

    /**
     * Zipcode of city
     */
    @Column(name ="ZIP")
    private String zip;

    /**
     * Default constructor, initialiaze nothing
     */
    public Address() {}

    /**
     * Initialize Address
     * @param street street name
     * @param city city name
     * @param state state name
     * @param zip zipcode
     */
   public Address(String street, String city, String state, String zip) {
        this.street = street;
        this.city = city;
        this.zip = zip;
        this.state = state;
    }


    /**
     * Getter for street
     * @return street
     */
    @XmlElement
    public String getStreet() {
        return street;
    }

    /**
     * Getter for city
     * @return city
     */
    @XmlElement
    public String getCity() {

        return city;
    }

    /**
     * Getter for state
     * @return state
     */
    @XmlElement
    public String getState() {
        return state;
    }

    /**
     * Getter for zip
     * @return zip
     */
    @XmlElement
    public String getZip() {
        return zip;
    }

    /**
     * Setter for street
     * @param street street name
     */
    public void setStreet(String street) {
        this.street = street;
    }

    /**
     * Setter for city
     * @param city city name
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Setter for state
     * @param state state name
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * Setter for Zipcode
     * @param zip zipcode
     */
    public void setZip(String zip) {
        this.zip = zip;
    }
}
