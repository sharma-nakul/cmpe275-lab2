package edu.sjsu.cmpe275.lab2.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Nakul on 27-Oct-15.
 * POJO class to hold Person information
 */

@Entity
@Table(name = "PERSON")
@XmlRootElement(name = "Person")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Person {

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

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "ORG_ID")
    private Organization organization;

    // private List<Person> friends;
    public Person() {
    }

    public Person(String firstname, String lastname, String email, String description, Address address, Organization organization) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.description = description;
        this.organization = organization;
        this.address = address;
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

    public Organization getOrganization() {
        return organization;
    }

    public Address getAddress() {
        return address;
    }

    public String getDescription() {
        return description;
    }

    public String getEmail() {
        return email;
    }

    public String getLastname() {
        return lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public long getId() {
        return id;
    }

  /*  public List<Person> getFriends() {
        return friends;
    }

    public void setFriends(List<Person> friends) {
        this.friends = friends;
    }*/
}
