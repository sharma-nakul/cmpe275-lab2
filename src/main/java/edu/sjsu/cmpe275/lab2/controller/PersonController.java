package edu.sjsu.cmpe275.lab2.controller;

import edu.sjsu.cmpe275.lab2.model.Address;
import edu.sjsu.cmpe275.lab2.model.Organization;
import edu.sjsu.cmpe275.lab2.model.Person;
import edu.sjsu.cmpe275.lab2.service.IPersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Nakul on 27-Oct-15.
 * Controller class for Person Entity. The defines all person related APIs.
 */
@RestController
@RequestMapping(value = "api/v1/person",
        produces = {"application/xml", "application/json", "text/html"},
        consumes = MediaType.APPLICATION_JSON_VALUE)
public class PersonController extends Throwable {

    private static final Logger logger = LoggerFactory.getLogger(PersonController.class);

    @Autowired
    private IPersonService IPersonService;


    /**
     * Controller method to fetch create new user profile. Payload should be of JSON format.
     * Payload may have "id" field but it is not mandatory.
     * All the person fields (firstname, lastname, email, street, city, organization, etc), except ID and friends, are passed in as query parameters.
     * Only the firstname, lastname, and email are required. Anything else is optional.
     * --------Friends is not allowed to be passed in as a parameter.
     * The organization parameter, if present, must be the ID of an existing organization.
     * The request returns the newly created person object in JSON in its HTTP payload, including all attributes. (Please note this differs from generally recommended practice of only returning the ID.)
     * If the request is invalid, e.g., missing required parameters, the HTTP status code should be 400; otherwise 200.
     *
     * @param firstname   It is a first name of a person. (Required)
     * @param lastname    It is a last name of a person. (Required)
     * @param email       It is an email id of a person. (Required)
     * @param street      Name of street.(Optional)
     * @param city        Name of city. (Optional)
     * @param state       Name of state. (Optional)
     * @param zip         Zip Code of a location. (Optional)
     * @param description Description of a person. (Optional)
     * @return Object of person in JSON format.
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity addPerson(
            @RequestParam(value = "firstname", required = true) String firstname,
            @RequestParam(value = "lastname", required = true) String lastname,
            @RequestParam(value = "email", required = true) String email,
            @RequestParam(value = "street", required = false) String street,
            @RequestParam(value = "city", required = false) String city,
            @RequestParam(value = "state", required = false) String state,
            @RequestParam(value = "zip", required = false) String zip,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "organization", required = false) String orgId) {

        Address address = new Address(street, city, state, zip);
        Organization organization = new Organization();
        // Org id has be retrieved from table
        if (orgId != null)
            organization.setId(Long.parseLong(orgId));
        Person person = this.IPersonService.addPerson(firstname, lastname, email, description, address, organization);
        if (person == null)
            return new ResponseEntity<String>("Could not save the user at this time, please check your request or try later.", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<Person>(person, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity getPersonInformation(
            @PathVariable("id") String id) {
        try {
            Person person = this.IPersonService.getPerson(id);
            if (person != null)
                return new ResponseEntity<Person>(person, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (NullPointerException e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
