package edu.sjsu.cmpe275.lab2.controller;

import edu.sjsu.cmpe275.lab2.model.Address;
import edu.sjsu.cmpe275.lab2.model.Organization;
import edu.sjsu.cmpe275.lab2.model.Person;
import edu.sjsu.cmpe275.lab2.service.IOrganizationService;
import edu.sjsu.cmpe275.lab2.service.IPersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;

/**
 * @author Nakul Sharma
 * Controller class to manage Person related APIs.
 * RestController annotation is used to map the class as RestFul web service controller.
 * RequestMapping annotation is used to map the base URI.
 */
@RestController
@RequestMapping(value = "person",
        produces = {"application/xml", "application/json"},
        consumes = MediaType.APPLICATION_JSON_VALUE)
public class PersonController extends Throwable {

    /**
     * Variable of type logger to print data on console.
     */
    private static final Logger logger = LoggerFactory.getLogger(PersonController.class);

    /**
     * This Autowire the person service interface to serve HTTP request of Person URI.
     */
    @Autowired
    private IPersonService personService;

    /**
     * This Autowire the organization service interface to serve HTTP request of Organization URI.
     */
    @Autowired
    private IOrganizationService orgService;

    /**
     * Method to add a person using HTTP URI as /person?
     * HTTP REQUEST - POST
     * @param firstname   It is a first name of a person. (Required)
     * @param lastname    It is a last name of a person. (Required)
     * @param email       It is an email id of a person. (Required)
     * @param street      Name of street.(Optional)
     * @param city        Name of city. (Optional)
     * @param state       Name of state. (Optional)
     * @param zip         Zip Code of a location. (Optional)
     * @param description Description of a person. (Optional)
     * @param orgId Id of an existing organization. Request parameter should "organization". (Optional)
     * @return Https Status as OK (200 - successful), BAD REQUEST (400 - Parameter missing or invalid)
     * and Person object in JSON format.
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

        try {
            Address address = new Address(street, city, state, zip);
            Organization organization = new Organization();
            Person person;
            if (orgId != null) {
                Organization o1 = this.orgService.getOrganization(orgId);
                if (o1 == null)
                        throw new DataIntegrityViolationException("Organization id is incorrect! Please provide correct id or leave it blank.");
                organization.setId(o1.getId());
                logger.info("org " + o1.getId());
                person = this.personService.addPerson(firstname, lastname, email, description, address, organization);
            }
            else
            person=this.personService.addPerson(firstname,lastname,email,description,address,null);
            if (person == null)
                return new ResponseEntity<>("Could not save the user at this time, please check your request or try later.", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(person, HttpStatus.OK);
        }
        catch(DataIntegrityViolationException e){
            return new ResponseEntity<>("Either email or organization id is not correct.", HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            return new ResponseEntity<>("Invalid Request", HttpStatus.BAD_REQUEST);
        }
    }


    /**
     * Method to get a person details using HTTP URI /person/{id}
     * HTTP REQUEST - GET
     * format parameter is optional, if present must be of value either json or xml.
     * @param id Id of an existing person.
     * @return Https Status as OK (200 - successful), NOT FOUND (404 - Id doesn't exist)
     * and Person object in JSON or XML format.
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity getPerson(
            @PathVariable("id") String id) {
        try {
            Person person = this.personService.getPerson(id);
            if (person != null)
                return new ResponseEntity<Person>(person, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    /**
     * Method to update a person using HTTP URI as /person/{id}?
     * HTTP REQUEST - POST
     * @param id Id of an existing person who needs to be updated.
     * @param firstname   It is a first name of a person. (Required)
     * @param lastname    It is a last name of a person. (Required)
     * @param email       It is an email id of a person. (Required)
     * @param street      Name of street.(Optional)
     * @param city        Name of city. (Optional)
     * @param state       Name of state. (Optional)
     * @param zip         Zip Code of a location. (Optional)
     * @param description Description of a person. (Optional)
     * @param orgId Id of an existing organization.
     * @return Https Status as OK (200 - successful), NOT FOUND (404 - Id doesn't exist),
     * BAD REQUEST (400 - Parameter missing or Invalid request) and Person object in JSON format.
     */
    @RequestMapping(method = RequestMethod.POST, value = "/{id}")
    @ResponseBody
    public ResponseEntity updatePerson(
            @PathVariable("id") String id,
            @RequestParam(value = "firstname", required = true) String firstname,
            @RequestParam(value = "lastname", required = true) String lastname,
            @RequestParam(value = "email", required = true) String email,
            @RequestParam(value = "street", required = false) String street,
            @RequestParam(value = "city", required = false) String city,
            @RequestParam(value = "state", required = false) String state,
            @RequestParam(value = "zip", required = false) String zip,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "organization", required = false) String orgId) {
        try {
            if (id.isEmpty() || id == null)
                throw new MissingServletRequestParameterException("id", "String");
            Person person = this.personService.getPerson(id);
            if (person == null)
                return new ResponseEntity<>("Person not found.", HttpStatus.NOT_FOUND);
            else {
                Address address = person.getAddress();
                if (firstname!=null)
                    person.setFirstname(firstname);
                if (description!=null)
                    person.setDescription(description);
                if (lastname!=null)
                    person.setLastname(lastname);
                if (email!=null)
                    person.setEmail(email);
                if (street!=null)
                    address.setStreet(street);
                if (state!=null)
                    address.setState(state);
                if (city!=null)
                    address.setCity(city);
                if (zip!=null)
                    address.setZip(zip);
                Organization organization = person.getOrganization();
                if (orgId != null)
                    organization.setId(Long.parseLong(orgId));
                person.setAddress(address);
                person.setOrganization(organization);
                person = this.personService.updatePerson(person);
                return new ResponseEntity<>(person, HttpStatus.OK);
            }
        } catch (MissingServletRequestParameterException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Invalid Request", HttpStatus.BAD_REQUEST);
        }
    }


    /**
     * Method to delete a person using HTTP URI as /person/{id}
     * HTTP REQUEST - DELETE
     * @param id Id of an existing person who needs to be delete.
     * @return Https Status as OK (200 - successful), NOT FOUND (404 - Id doesn't exist)
     * and Person object in JSON format.
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deletePerson(
            @PathVariable("id") String id) {

        try {
            Person person = this.personService.getPerson(id);
            if (person != null) {
                this.personService.deletePerson(person);
                return new ResponseEntity<>(person, HttpStatus.OK);
            } else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>("Person's references are present in other tables, cannot delete.", HttpStatus.BAD_REQUEST);
        }
    }
}

