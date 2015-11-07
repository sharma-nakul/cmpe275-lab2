package edu.sjsu.cmpe275.lab2.dao;

import edu.sjsu.cmpe275.lab2.model.Address;
import edu.sjsu.cmpe275.lab2.model.Organization;
import edu.sjsu.cmpe275.lab2.model.Person;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * Created by Nakul on 03-Nov-15.
 * This class implements the interface IPersonDao
 */

@Repository
public class PersonDaoImpl extends AbstractDao implements IPersonDao {

    private static final Logger logger = LoggerFactory.getLogger(PersonDaoImpl.class);
    private Session session;

    @Override
    public Person addPerson(String firstname, String lastname, String email, String description, Address address, Organization organization) {
        Person person = new Person(firstname, lastname, email, description, address, organization);
        session = getSession();
        session.save(person);
        session.flush();
        logger.info(person.getFirstname() + " " + person.getLastname() + " added successfully");
        return person;
    }

    @Override
    public Person getPerson(String id) {
        session = getSession();
        long p_id = Long.parseLong(id);
        Person person = (Person) session.get(Person.class, p_id);
        if (person == null)
            logger.info("Returns null while retrieving the person of id " + p_id);
        else
            logger.info("Person of id " + p_id + " exists in database.");
        return person;
    }

    @Override
    public Person updatePerson(Person person) {
        session =getSession();
        session.update(person);
        logger.info(person.getFirstname() + " " + person.getLastname() + " profile updated successfully");
        return person;
    }

    @Override
    public void deletePerson (Person person){
        session=getSession();
        session.delete(person);
        logger.info(person.getFirstname() + " " + person.getLastname() + " deleted successfully");
    }

    @Override
    public void addFriend(Person person) {
        session =getSession();
        session.update(person);
        logger.info(person.getFirstname() + " " + person.getLastname() + " updated friendship successfully");
    }

    @Override
    public void deleteFriend(Person person){
        session=getSession();
        session.update(person);
        logger.info(person.getFirstname() + " " + person.getLastname() + " deleted friendship successfully");
    }

}
