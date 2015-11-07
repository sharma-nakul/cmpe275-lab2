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

@RestController
@RequestMapping(value = "api/v1/friends")
public class FriendshipController extends Throwable {

    @Autowired
    private IPersonService personService;

    @RequestMapping(value = "/{id1}/{id2}", method = RequestMethod.PUT)
    public ResponseEntity makeFriendship(
            @PathVariable("id1") String id1,
            @PathVariable("id2") String id2) {
        try {
            Person p1 = this.personService.getPerson(id1);
            Person p2 = this.personService.getPerson(id2);
            String message = p1.getFirstname() +" and "+p2.getFirstname()+" are both friends now.";
            String returnCode = this.personService.addFriend(p1,p2);
            if(returnCode.equals("Added"))
                return new ResponseEntity<>(message,HttpStatus.OK);
            else if(returnCode.equals("Friends"))
                return new ResponseEntity<>(HttpStatus.OK);
            else
                throw new Exception("Invalid Request");
        } catch (DataIntegrityViolationException cve) {
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NullPointerException r) {
            return new ResponseEntity<>("One of the id is not present in database.", HttpStatus.NOT_FOUND);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/{id1}/{id2}", method = RequestMethod.DELETE)
    public ResponseEntity deleteFriendship(@PathVariable("id1") String id1,
                                           @PathVariable("id2") String id2) {
        try {
            Person p1 = this.personService.getPerson(id1);
            Person p2 = this.personService.getPerson(id2);
            String message = p1.getFirstname() + " and " + p2.getFirstname() + " are not friends anymore.";
                String returnCode = this.personService.deleteFriend(p1, p2);
                if (returnCode.equals("Deleted"))
                    return new ResponseEntity<>(message, HttpStatus.OK);
                else if (returnCode.equals("Not friends"))
                    return new ResponseEntity<>(p1.getFirstname() + " and " + p2.getFirstname() + " are not friends.", HttpStatus.NOT_FOUND);
                else
                    throw new Exception("Invalid request!");
        }  catch (NullPointerException r) {
            return new ResponseEntity<>("One of the id is not present in database.", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
