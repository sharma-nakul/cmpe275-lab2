package edu.sjsu.cmpe275.lab2.dao;

import edu.sjsu.cmpe275.lab2.model.Address;
import edu.sjsu.cmpe275.lab2.model.Organization;
import edu.sjsu.cmpe275.lab2.model.Person;

/**
 * Created by Nakul on 03-Nov-15.
 * Person DAO to perform database operation.
 */
public interface IPersonDao {

    Person addPerson(String firstname, String lastname, String email,String description, Address address, Organization organization);
    void updatePerson (Person person);
    Person getPerson(String id);
    void addFriend(Person person);
    void deleteFriend (Person person);
}
