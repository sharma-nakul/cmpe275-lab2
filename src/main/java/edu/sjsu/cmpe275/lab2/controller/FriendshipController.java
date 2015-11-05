package edu.sjsu.cmpe275.lab2.controller;

/**
 * Created by Nakul on 03-Nov-15.
 * Controller class to manage friendship APIs.
 */

import edu.sjsu.cmpe275.lab2.model.Person;
import edu.sjsu.cmpe275.lab2.service.IPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "api/v1/friends")
public class FriendshipController {

    @Autowired
    private IPersonService personService;

    @RequestMapping(value = "/{id1}/{id2}", method = RequestMethod.PUT)
    public ResponseEntity makeFriendship(
            @PathVariable("id1") String id1,
            @PathVariable("id2") String id2) {
        try {
            Person p1 = this.personService.getPerson(id1);
            Person p2 = this.personService.getPerson(id2);
            String message = "Done!";
            List<Person> friendList1 = p1.getFriends();
            List<Person> friendList2 = p1.getFriendsWith();
            if (p1 == null || p2 == null)
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            else if (!friendList1.contains(p2)) {
                friendList1.add(p2);
                friendList2.add(p2);
                p1.setFriends(friendList1);
                p1.setFriendsWith(friendList2);
                this.personService.addFriend(p1);
                message = p1.getId() + " and " + p2.getId() + " are now friends";
            } else
                throw new IllegalArgumentException();
            return new ResponseEntity<String>(message, HttpStatus.OK);
        } catch (DataIntegrityViolationException cve) {
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/{id1}/{id2}", method = RequestMethod.DELETE)
    public ResponseEntity deleteFriendship(@PathVariable("id1") String id1,
                                           @PathVariable("id2") String id2) {
        try {
            Person p1 = this.personService.getPerson(id1);
            Person p2 = this.personService.getPerson(id2);
            String message = "Done!";
            List<Person> friendList1 = p1.getFriends();
            List<Person> friendList2 = p1.getFriendsWith();
            if (p1 == null || p2 == null)
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            else if (!friendList1.contains(p2)) {
                friendList1.remove(p2);
                friendList2.remove(p2);
                p1.setFriends(friendList1);
                p1.setFriendsWith(friendList2);
                this.personService.deleteFriend(p1);
                message = p1.getId() + " and " + p2.getId() + "are not friends anymore!";
            } else
                throw new IllegalArgumentException();
            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (Exception e)
        {
          return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
