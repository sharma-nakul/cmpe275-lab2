package edu.sjsu.cmpe275;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Created by Nakul on 27-Oct-15.
 * POJO class to hold organization information
 */
public class Organization {
    private long id;
    private String name;
    private String description;
    private Address address;

    public Organization(Address address) {
        this.name = "";
        this.description = "";
        this.address = address;
    }

    /**
     * Default constructor to handle request without id any value in id field.
     * This set default value of id field as 999 as id is of long data type which
     * cannot be null (primitive data type)
     */
    public Organization() {

        this.id = 999;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        try {
            this.id = id;
        } catch (NullPointerException e) {
            this.id = 999;
        }
    }

    @JsonIgnore
    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

    @JsonIgnore
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @JsonIgnore
    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
