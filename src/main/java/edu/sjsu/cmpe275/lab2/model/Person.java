package edu.sjsu.cmpe275.lab2.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Nakul Sharma
 * POJO class to hold Person information
 * Entity annotation to mark class as persistence entity
 * Table annotation to map table with class
 * XmlType annotation to define order of XML representation
 * XmlSeeAlso annotation to refer classes for XML representation
 * JsonInclude annotation to avoid null value in JSON representation
 */
@Entity
@Table(name = "PERSON")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "id")
@XmlRootElement(name = "Person")
@XmlSeeAlso(value = {Organization.class, Address.class, Person.class})
@XmlType(propOrder = {"id", "firstname", "lastname", "email", "description", "organization", "address"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Person implements Serializable {

    /**
     * Id of a person. It is an auto generated id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "P_ID")
    private long id;

    /**
     * First name of a person
     */
    @Column(name = "FIRST_NAME", nullable = false)
    private String firstname;

    /**
     * Last name of a person
     */
    @Column(name = "LAST_NAME", nullable = false)
    private String lastname;

    /**
     * Email id of a person. It should be unique
     */
    @Column(name = "EMAIL", nullable = false, unique = true)
    private String email;

    /**
     * Short description about a person
     */
    @Column(name = "DESCRIPTION")
    private String description;

    /**
     * Address object of a person
     * Embedded annotation is used to embedded instead of writing all its attributes.
     */
    @Embedded
    private Address address;

    /**
     * Organization object.
     * ManyToOne annotation - Person can belong up to 1 organization
     * JoinColumn annotation to join person with organization
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ORG_ID")
    private Organization organization;

    /**
     * List of friends of a person
     * The list has many to many bi-directional relation
     */
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinTable(name = "FRIENDSHIP",
            joinColumns = {@JoinColumn(name = "PERSON_ID", referencedColumnName = "P_ID")},
            inverseJoinColumns = {@JoinColumn(name = "FRIEND_ID", referencedColumnName = "P_ID")})
    private List<Person> friends;

    /**
     * List the people whose friend is this person
     * The list has many to many bi-directional relation
     */
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinTable(name = "FRIENDSHIP",
            joinColumns = {@JoinColumn(name = "FRIEND_ID", referencedColumnName = "P_ID")},
            inverseJoinColumns = {@JoinColumn(name = "PERSON_ID", referencedColumnName = "P_ID")})
    private List<Person> friendsWith;

    /**
     * Initiliazed friend list
     */
    public Person() {
        this.friends = new ArrayList<>();
        this.friendsWith = new ArrayList<>();
    }

    /**
     * Set person's personal details
     * @param firstname First name of a person
     * @param lastname Last name of a person
     * @param email Email id of a person
     * @param description Description of a person
     * @param address Address of a person
     * @param organization Organization (if any) person belongs
     */
    public Person(String firstname, String lastname, String email, String description, Address address, Organization organization) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.description = description;
        this.organization = organization;
        this.address = address;
        this.friends = new ArrayList<>();
        this.friendsWith = new ArrayList<>();
    }


    /**
     * Setter for person id
     * @param id id of a person
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Setter for first name
     * @param firstname first name of person
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    /**
     * Setter for lastname
     * @param lastname last name of a person
     */
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    /**
     * Setter for email id
     * @param email email id of a person
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Setter for description
     * @param description short description of a person
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Setter for address
     * @param address address of a person
     */
    public void setAddress(Address address) {
        this.address = address;
    }

    /**
     * Setter for Organization
     * @param org organization person belongs to
     */
    public void setOrganization(Organization org) {
        this.organization = org;
    }

    /**
     * Getter of friends
     * @return list of friends
     */
    @XmlTransient
    public List<Person> getFriends() {
        return friends;
    }

    /**
     * Getter of organization
     * @return organization person belongs to
     */
    @XmlElement(name = "organization")
    public Organization getOrganization() {
        return organization;
    }

    /**
     * Getter of address
     * @return address of a person
     */
    @XmlElement(name = "address")
    public Address getAddress() {
        return address;
    }

    /**
     * Getter of description
     * @return description of a person
     */
    @XmlElement
    public String getDescription() {
        return description;
    }

    @XmlElement
    public String getEmail() {
        return email;
    }

    /**
     * Getter of last name
     * @return last name of a person
     */
    @XmlElement
    public String getLastname() {
        return lastname;
    }

    /**
     * Getter of first name
     * @return first name of a person
     */
    @XmlElement
    public String getFirstname() {
        return firstname;
    }

    /**
     * Getter of id
     * @return id of a person
     */
    @XmlElement
    public long getId() {
        return id;
    }

    /**
     * Setter for a friend list
     * @param friends friend list of a person
     */
    public void setFriends(List<Person> friends) {
        this.friends = friends;
    }

    /**
     * Getter of list of people this person is friends with
     * @return list of people
     */
    @JsonIgnore
    @XmlTransient
    public List<Person> getFriendsWith() {
        return friendsWith;
    }

    /**
     * Setter for list of people this person is friends with
     * @param friendsWith list of people
     */
    public void setFriendsWith(List<Person> friendsWith) {
        this.friendsWith = friendsWith;
    }
}
