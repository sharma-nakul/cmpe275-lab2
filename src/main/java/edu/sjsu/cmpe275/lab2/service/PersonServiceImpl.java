package edu.sjsu.cmpe275.lab2.service;

import edu.sjsu.cmpe275.lab2.dao.IPersonDao;
import edu.sjsu.cmpe275.lab2.model.Address;
import edu.sjsu.cmpe275.lab2.model.Organization;
import edu.sjsu.cmpe275.lab2.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Nakul on 03-Nov-15.
 * Handler class for Person. The class intercept REST call to persist or retrieve data.
 */

@Service
@Transactional("txManager")
public class PersonServiceImpl implements PersonService {

    private static final Logger logger = LoggerFactory.getLogger(PersonServiceImpl.class);

    @Autowired
    private IPersonDao personDao;

    public Person addPerson(String firstname, String lastname, String email, String description, Address address, Organization organization) {
        return personDao.addPerson(firstname,lastname,email,description,address,organization);
    }

    public Person getPerson(String id) {
        return personDao.getPerson(id);
    }

    public void updatePerson(Person person) {

    }
}
