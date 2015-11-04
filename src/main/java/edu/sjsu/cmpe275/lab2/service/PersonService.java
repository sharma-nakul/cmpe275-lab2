package edu.sjsu.cmpe275.lab2.service;

import edu.sjsu.cmpe275.lab2.model.Address;
import edu.sjsu.cmpe275.lab2.model.Organization;
import edu.sjsu.cmpe275.lab2.model.Person;


/**
 * Created by Nakul on 03-Nov-15.
 * Interface to deliver Person related operations.
 */

public interface PersonService {

    Person addPerson(String firstname, String lastname, String email,String description, Address address, Organization organization);
    void updatePerson (Person person);
    Person getPerson(String id);
}
