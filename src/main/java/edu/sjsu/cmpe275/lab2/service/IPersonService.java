package edu.sjsu.cmpe275.lab2.service;

import edu.sjsu.cmpe275.lab2.model.Address;
import edu.sjsu.cmpe275.lab2.model.Organization;
import edu.sjsu.cmpe275.lab2.model.Person;


/**
 * Created by Nakul on 03-Nov-15.
 * Interface to deliver Person related operations.
 */

public interface IPersonService {

    Person addPerson(String firstname, String lastname, String email,String description, Address address, Organization organization);
    Person updatePerson (Person person);
    Person getPerson(String id);
    void deletePerson (Person person);

    String addFriend (Person person, Person personToAdd);
    String deleteFriend(Person person, Person personToDelete);
}
