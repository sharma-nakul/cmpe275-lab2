package edu.sjsu.cmpe275.lab2.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

/**
 * Created by Nakul on 27-Oct-15.
 * POJO class to hold organization information
 */

@Entity
@Table(name = "ORGANIZATION")
@XmlRootElement (name = "Organization")
@XmlSeeAlso(Address.class)
@XmlType(propOrder = { "id", "name","description","address" })
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Organization implements Serializable {

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

    public Organization (String name, String description, Address address)
    {
        this.name=name;
        this.description=description;
        this.address=address;
    }
    public Organization(Address address) {
        this.address = address;
    }

    /**
     * Default constructor to handle request without id any value in id field.
     */
    public Organization() {}

    @XmlElement
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @XmlElement
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @XmlElement (name ="address")
    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
