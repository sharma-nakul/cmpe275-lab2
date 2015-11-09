package edu.sjsu.cmpe275.lab2.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

/**
 * @author Nakul Sharma
 * POJO class to hold organization information
 * Entity annotation to mark class as persistence entity
 * Table annotation to map table with class
 * XmlType annotation to define order of XML representation
 * XmlSeeAlso annotation to refer classes for XML representation
 * JsonInclude annotation to avoid null value in JSON representation
 */
@Entity
@Table(name = "ORGANIZATION")
@XmlRootElement (name = "Organization")
@XmlSeeAlso(Address.class)
@XmlType(propOrder = { "id", "name","description","address" })
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Organization implements Serializable {

    /**
     * Organization id. This is auto generated value
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="ORG_ID")
    private long id;

    /**
     * Name of an organization
     */
    @Column(name = "ORG_NAME")
    private String name;

    /**
     * Description of an organization
     */
    @Column(name = "ORG_DESC")
    private String description;

    /**
     * Address object of a person
     * Embedded annotation is used to embedded instead of writing all its attributes
     */
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

    /**
     * Getter of an organization id
     * @return auto-generated organization id
     */
    @XmlElement
    public long getId() {
        return id;
    }

    /**
     * Setter for organization id
     * @param id Id of an organization
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Getter of a organization name
     * @return Organization name
     */
    @XmlElement
    public String getName() {
        return name;
    }

    /**
     * Setter for organization name
     * @param name organization name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter of an organization description
     * @return description about organization
     */
    @XmlElement
    public String getDescription() {
        return description;
    }

    /**
     * Setter of an organization description
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Getter of an organization address
     * @return address of an organization
     */
    @XmlElement (name ="address")
    public Address getAddress() {
        return address;
    }

    /**
     * Setter for organization address
     * @param address address of an organization
     */
    public void setAddress(Address address) {
        this.address = address;
    }
}
