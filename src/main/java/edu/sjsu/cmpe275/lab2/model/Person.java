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
 * Created by Nakul on 27-Oct-15.
 * POJO class to hold Person information
 */

@Entity
@Table(name = "PERSON")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "id")
@XmlRootElement(name = "Person")
@XmlSeeAlso(value = {Organization.class, Address.class, Person.class})
@XmlType(propOrder = {"id", "firstname", "lastname", "email", "description", "organization", "address"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Person implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "P_ID")
    private long id;

    @Column(name = "FIRST_NAME", nullable = false)
    private String firstname;

    @Column(name = "LAST_NAME", nullable = false)
    private String lastname;

    @Column(name = "EMAIL", nullable = false, unique = true)
    private String email;

    @Column(name = "DESCRIPTION")
    private String description;

    @Embedded
    private Address address;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "ORG_ID")
    private Organization organization;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinTable(name = "FRIENDSHIP",
            joinColumns = {@JoinColumn(name = "PERSON_ID", referencedColumnName = "P_ID")},
            inverseJoinColumns = {@JoinColumn(name = "FRIEND_ID", referencedColumnName = "P_ID")})
    private List<Person> friends;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinTable(name = "FRIENDSHIP",
            joinColumns = {@JoinColumn(name = "FRIEND_ID", referencedColumnName = "P_ID")},
            inverseJoinColumns = {@JoinColumn(name = "PERSON_ID", referencedColumnName = "P_ID")})
    private List<Person> friendsWith;

    public Person() {
        this.friends = new ArrayList<>();
        this.friendsWith = new ArrayList<>();
    }

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


    public void setId(long id) {
        this.id = id;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setOrganization(Organization org) {
        this.organization = org;
    }

    @XmlTransient
    public List<Person> getFriends() {
        return friends;
    }

    @XmlElement(name = "organization")
    public Organization getOrganization() {
        return organization;
    }

    @XmlElement(name = "address")
    public Address getAddress() {
        return address;
    }

    @XmlElement
    public String getDescription() {
        return description;
    }

    @XmlElement
    public String getEmail() {
        return email;
    }

    @XmlElement
    public String getLastname() {
        return lastname;
    }

    @XmlElement
    public String getFirstname() {
        return firstname;
    }

    @XmlElement
    public long getId() {
        return id;
    }

    public void setFriends(List<Person> friends) {
        this.friends = friends;
    }

    @JsonIgnore
    @XmlTransient
    public List<Person> getFriendsWith() {
        return friendsWith;
    }

    public void setFriendsWith(List<Person> friendsWith) {
        this.friendsWith = friendsWith;
    }
}
