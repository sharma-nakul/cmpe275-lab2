package edu.sjsu.cmpe275;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Locale;

/**
 * Created by Nakul on 27-Oct-15.
 * Controller class which calls person related APIs.
 */
@RestController
@RequestMapping(value = "api/v1/person", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class PersonController extends Throwable {

    Person person = new Person();

    HashMap<Long, Person> userProfiles = new HashMap<>();

    /**
     * Controller method to fetch create new user profile. Payload should be of JSON format.
     * Payload may have "id" field but it is not mandatory.
     * Friends details are not accepted as neither payload nor parameter.
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
            throws NumberFormatException, NullPointerException, MissingServletRequestParameterException {

        Address address = new Address();
        address.setAddress(street, city, state, zip);
        this.person.setAddress(address);
        Organization organization = new Organization(address);
        if(orgId!=null)
        organization.setId(Long.parseLong(orgId));
        else
        organization.setId(999);
        person.createProfile(firstname, lastname, email, description, address, organization);
        userProfiles.put(person.getId(), person); //persist user profile.
        return new ResponseEntity<>(person, HttpStatus.OK);
    }



    /* Exception Handling Methods */

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseBody
    public ResponseEntity<BadRequestException> missigParameters(HttpServletRequest request, MissingServletRequestParameterException exception) {
        Locale locale = LocaleContextHolder.getLocale();
        String errorMessage = messageSource.getMessage("error.bad.parameter.id", null, locale);
        errorMessage += " " +exception.getMessage();
        String errorURL = request.getRequestURL().toString();
        return new ResponseEntity<>(new BadRequestException(errorURL, errorMessage), HttpStatus.BAD_REQUEST);
    }


    /**
     * The method handle NumberFormatException.
     * @param request   This is to request the URL of the API.
     * @param exception If NumberFormatException occurs, this object will have exception details.
     * @return It returns error message and url of the request for exception has been generated in JSON format.
     */
    @ExceptionHandler(NumberFormatException.class)
    @ResponseBody
    public ResponseEntity<BadRequestException> numberFormatException(HttpServletRequest request, NumberFormatException exception) {
        Locale locale = LocaleContextHolder.getLocale();
        String errorMessage = messageSource.getMessage("error.bad.organization.id", null, locale);
        errorMessage += " "+exception.getMessage();
        String errorURL = request.getRequestURL().toString();
        return new ResponseEntity<>(new BadRequestException(errorURL, errorMessage), HttpStatus.BAD_REQUEST);
    }

    /**
     * The method handles NullPointerException.
     *
     * @param request   This is to request the URL of the API.
     * @param exception If NullPointerException occurs, this object will have exception details.
     * @return It returns error message and url of the request for exception has been generated in JSON format.
     */
    @ExceptionHandler(NullPointerException.class)
    @ResponseBody
    public ResponseEntity<BadRequestException> nullPointerException(HttpServletRequest request, NullPointerException exception) {
        Locale locale = LocaleContextHolder.getLocale();
        String errorMessage = messageSource.getMessage("error.nullvalue.organization.id", null, locale);
        errorMessage += " "+exception.toString();
        String errorURL = request.getRequestURL().toString();
        return new ResponseEntity<>(new BadRequestException(errorURL, errorMessage), HttpStatus.BAD_REQUEST);
    }

    /**
     * The method handles all other exceptions and returns status as BAD_REQUEST - 400.
     * @param request   This is to request the URL of the API.
     * @param exception If Exception occurs, this object will have exception details.
     * @return It returns error message and url of the request for exception has been generated in JSON format.
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<BadRequestException> genericException(HttpServletRequest request, Exception exception) {
        Locale locale = LocaleContextHolder.getLocale();
        String errorMessage = messageSource.getMessage("error.bad.generic.output", null, locale);
        errorMessage += " "+exception.getMessage();
        String errorURL = request.getRequestURL().toString();
        return new ResponseEntity<>(new BadRequestException(errorURL, errorMessage), HttpStatus.BAD_REQUEST);
    }
}
