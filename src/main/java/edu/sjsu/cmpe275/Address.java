package edu.sjsu.cmpe275;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Created by Nakul on 27-Oct-15.
 * POJO class to hold Person's address details.
 */

public class Address {

    private String street;
    private String city;
    private String state;
    private String zip;

    public Address() {
        this.street = "";
        this.city = "";
        this.state = "";
        this.zip = "";
    }

    public void setAddress(String street, String city, String state, String zip) {
        this.street = street;
        this.city = city;
        this.zip = zip;
        this.state = state;
    }

    @JsonIgnore
    public String getStreet() {
        return street;
    }

    @JsonIgnore
    public String getCity() {
        return city;
    }

    @JsonIgnore
    public String getState() {
        return state;
    }

    @JsonIgnore
    public String getZip() {
        return zip;
    }
}
