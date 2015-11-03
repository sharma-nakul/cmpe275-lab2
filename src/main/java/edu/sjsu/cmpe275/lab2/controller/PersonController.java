package edu.sjsu.cmpe275.lab2.controller;

import edu.sjsu.cmpe275.lab2.Utility.HUtility;
import edu.sjsu.cmpe275.lab2.model.Address;
import edu.sjsu.cmpe275.lab2.model.Organization;
import edu.sjsu.cmpe275.lab2.model.Person;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

/**
 * Created by Nakul on 27-Oct-15.
 * Controller class which calls person related APIs.
 */
@RestController
@RequestMapping(value = "api/v1/person", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class PersonController extends Throwable {

    private static Session session;
    Person person;

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
    public ResponseEntity<Person> addPerson(
            @RequestParam(value = "firstname", required = true) String firstname,
            @RequestParam(value = "lastname", required = true) String lastname,
            @RequestParam(value = "email", required = true) String email,
            @RequestParam(value = "street", required = false) String street,
            @RequestParam(value = "city", required = false) String city,
            @RequestParam(value = "state", required = false) String state,
            @RequestParam(value = "zip", required = false) String zip,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "organization", required = false) String orgId)
            throws HibernateException {

        Address address = new Address(street, city, state, zip);
        Organization organization = new Organization();
        // Org id has be retrieved from table
        if (orgId != null)
            organization.setId(Long.parseLong(orgId));
        //else
        //organization.setId(999);
        person = new Person(firstname, lastname, email, description, address, organization);
        try {
            session = HUtility.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(person);
            session.flush();
            session.getTransaction().commit();
            return new ResponseEntity<>(person, HttpStatus.OK);
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw new HibernateException("Hibernate Exception: " + e.getMessage());
        } finally {
            session.flush();
            session.close();
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Person> getPersonInformation(
            @PathVariable("id") String id,
            @RequestParam(value = "format", required = false) String format)
    {
        try{
            session = HUtility.getSessionFactory().openSession();
            long p_id= Long.parseLong(id);
            Person person = (Person) session.get(Person.class,p_id);
            if(person==null)
                throw new HibernateException("Person doesn't exist.");
            session.flush();
            return new ResponseEntity<Person>(person, HttpStatus.OK);
        }
        catch (HibernateException e) {
            session.getTransaction().rollback();
            throw new HibernateException("Hibernate Exception: " + e.getMessage());
        } finally {
            session.flush();
            session.close();
        }
    }
    /* Exception Handling Methods */

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(HibernateException.class)
    @ResponseBody
    public ResponseEntity<BadRequestException> connectionError(HttpServletRequest request, HibernateException exception) {
        String errorMessage = exception.getMessage();
        String errorURL = request.getRequestURL().toString();
        return new ResponseEntity<>(new BadRequestException(errorURL, errorMessage), HttpStatus.BAD_REQUEST);
    }

    /**
     * The method handles all other exceptions and returns status as BAD_REQUEST - 400.
     *
     * @param request   This is to request the URL of the API.
     * @param exception If Exception occurs, this object will have exception details.
     * @return It returns error message and url of the request for exception has been generated in JSON format.
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<BadRequestException> genericException(HttpServletRequest request, Exception exception) {
        Locale locale = LocaleContextHolder.getLocale();
        String errorMessage = messageSource.getMessage("error.bad.generic.output", null, locale);
        errorMessage += " " + exception.getMessage();
        String errorURL = request.getRequestURL().toString();
        return new ResponseEntity<>(new BadRequestException(errorURL, errorMessage), HttpStatus.BAD_REQUEST);
    }
}
