package edu.sjsu.cmpe275.lab2.service;

import edu.sjsu.cmpe275.lab2.controller.BadRequestException;
import edu.sjsu.cmpe275.lab2.dao.IPersonDao;
import edu.sjsu.cmpe275.lab2.model.Address;
import edu.sjsu.cmpe275.lab2.model.Organization;
import edu.sjsu.cmpe275.lab2.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nakul on 03-Nov-15.
 * Handler class for Person. The class intercept REST call to persist or retrieve data.
 */

@Service
@Transactional("tx1")
public class PersonServiceImpl implements IPersonService {

    private static final Logger logger = LoggerFactory.getLogger(PersonServiceImpl.class);

    @Autowired
    private IPersonDao personDao;

    public Person addPerson(String firstname, String lastname, String email, String description, Address address, Organization organization) {
        return personDao.addPerson(firstname, lastname, email, description, address, organization);
    }

    public Person getPerson(String id) {
        return personDao.getPerson(id);
    }

    public Person updatePerson(Person person) {
        personDao.updatePerson(person);
        return person;
    }

    public void deletePerson(Person person) {
        //personDao.deletePerson(person);
        List<Person> l1 = person.getFriends();
        List<Person> l2 = person.getFriendsWith();
        person.getFriends().removeAll(l1);
        person.getFriendsWith().removeAll(l2);
        person.setFriends(person.getFriends());
        person.setFriendsWith(person.getFriends());
        personDao.deletePerson(person);
    }

    public String addFriend(Person p1, Person p2) {
        try {
            int flag = 0;
            List<Person> l1;
            List<Person> l2;
            for (Person person : p1.getFriends()) {
                if(person.getFirstname().equals(p2.getFirstname())) {
                    flag = 1;
                    break;
                }
            }
            if(flag==0)
            {
                l1=p1.getFriends();
                l1.add(p2);
                l2=p1.getFriendsWith();
                l2.add(p2);
                p1.setFriends(l1);
                p1.setFriendsWith(l2);
                personDao.deleteFriend(p1);
                return "Added";
            }
            else
                throw new BadRequestException("Friends");
        }
        catch (BadRequestException e) {
            return e.getMessage();
        }
    }

    public String deleteFriend(Person p1, Person p2) {
        try {
            int flag=0;
            List<Person> l1=new ArrayList<>();
            List<Person> l2=new ArrayList<>();
            for (Person person : p1.getFriends()) {
                if(person.getFirstname().equals(p2.getFirstname())){
                    l1=p1.getFriends();
                    l1.remove(person);
                    l2=p1.getFriendsWith();
                    l2.remove(person);
                    flag=1;
                    break;
                }
            }
            if (flag==1) {
                p1.setFriends(l1);
                p1.setFriendsWith(l2);
                personDao.deleteFriend(p1);
                return "Deleted";
            } else
                throw new BadRequestException("Not friends");
        } catch (BadRequestException e) {
            return e.getMessage();
        }
    }
}
