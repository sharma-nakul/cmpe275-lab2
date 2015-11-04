package edu.sjsu.cmpe275.lab2.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Nakul on 27-Oct-15.
 * POJO class to hold organization information
 */

@Entity
@Table(name = "ORGANIZATION")
@XmlRootElement (name = "Organization")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Organization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="ORG_ID")
    private long id;

    @Column(name = "ORG_NAME")
    private String name;

    @Column(name = "ORG_DESC")
    private String description;

    @Embedded
    private Address address;

    public Organization(Address address) {
        this.address = address;
    }

    /**
     * Default constructor to handle request without id any value in id field.
     */
    public Organization() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
