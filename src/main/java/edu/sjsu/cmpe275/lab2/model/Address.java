package edu.sjsu.cmpe275.lab2.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Nakul on 27-Oct-15.
 * POJO class to hold Person's address details.
 */

@Embeddable
@XmlRootElement(name="Address")
public class Address {

    @Column(name ="STREET")
    private String street;

    @Column (name = "CITY")
    private String city;

    @Column(name = "STATE")
    private String state;

    @Column(name ="ZIP")
    private String zip;

    public Address() {}

   public Address(String street, String city, String state, String zip) {
        this.street = street;
        this.city = city;
        this.zip = zip;
        this.state = state;
    }


    public String getStreet() {
        return street;
    }

    public String getCity() {

        return city;
    }

    public String getState() {
        return state;
    }

    public String getZip() {
        return zip;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }
}
