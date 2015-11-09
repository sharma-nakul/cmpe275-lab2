package edu.sjsu.cmpe275.lab2.controller;

import edu.sjsu.cmpe275.lab2.model.Address;
import edu.sjsu.cmpe275.lab2.model.Organization;
import edu.sjsu.cmpe275.lab2.service.IOrganizationService;
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
 * Controller class to manage organization related APIs.
 * RestController annotation is used to map the class as RestFul web service controller.
 * RequestMapping annotation is used to map the base URI.
 */
@RestController
@RequestMapping(value = "org",
        produces = {"application/xml", "application/json"},
        consumes = MediaType.APPLICATION_JSON_VALUE)
public class OrganizationController extends Throwable {

    /**
     * Variable of type logger to print data on console.
     */
    private static final Logger logger = LoggerFactory.getLogger(OrganizationController.class);

    /**
     * This Autowire the organization service interface to serve HTTP request of Organization URI.
     */
    @Autowired
    private IOrganizationService organizationService;

    /**
     * Method to add an organization using HTTP URI as /org/?
     * HTTP REQUEST - POST
     * @param name Name of an organization. (Required)
     * @param description Short description of an organization. (Optional)
     * @param street Address field "street" of an organization. (Optional)
     * @param city Address field "city" of an organization. (Optional)
     * @param state Address field "state" of an organization. (Optional)
     * @param zip Address field "zip" of an organization. (Optional)
     * @return Https Status as OK (200 - successful), BAD REQUEST (400 - Parameter missing or invalid) and Organization object as JSON.
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity addOrganization(
            @RequestParam(value = "name", required = true) String name,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "street", required = false) String street,
            @RequestParam(value = "city", required = false) String city,
            @RequestParam(value = "state", required = false) String state,
            @RequestParam(value = "zip", required = false) String zip) {

        try {
            Address address = new Address(street, city, state, zip);
            Organization organization = this.organizationService.addOrganization(name, description, address);
            if (organization == null)
                return new ResponseEntity<String>("Could not save the organization at this time, please check your request or try later.", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(organization, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Invalid Request", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Method to get an organization using HTTP URI as /org/{id}
     * format parameter is optional, if present be of value either xml or json
     * HTTP REQUEST - GET
     * @param id Organization id
     * @return Https Status as OK (200 - successful), NOT FOUND (404 - Id doesn't exists) and Organization object of a JSON or XML format.
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity getOrganizationInformation(
            @PathVariable("id") String id) {
        try {
            Organization organization = this.organizationService.getOrganization(id);
            if (organization != null)
                return new ResponseEntity<>(organization, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Method to delete an organization using HTTP URI as /org/{id}
     * HTTP REQUEST - DELETE
     * @param id Organization id
     * @return Https Status as OK (200 - successful), NOT FOUND (404 - Id doesn't exists) and BAD REQUEST (400 - Id references found)
     * and Organization object in JSON format.
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteOrganization (
            @PathVariable("id") String id){
        try{
            Organization organization = this.organizationService.getOrganization(id);
            if (organization != null) {
                this.organizationService.deleteOrganization(organization);
                return new ResponseEntity<>(organization, HttpStatus.OK);
            }
            else
                return new ResponseEntity<>("Organization not found",HttpStatus.NOT_FOUND);
        }
        catch (DataIntegrityViolationException e)
        {
            return new ResponseEntity<>("One or more person belong to organization, hence cannot be deleted.",HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Method to update an organization using HTTP URI org/{id}
     * HTTP REQUEST - POST
     * @param id Id of an existing organization.
     * @param name Name of an organization. (Required)
     * @param description Short description of an organization. (Optional)
     * @param street Address field "street" of an organization. (Optional)
     * @param city Address field "city" of an organization. (Optional)
     * @param state Address field "state" of an organization. (Optional)
     * @param zip Address field "zip" of an organization. (Optional)
     * @return Https Status as OK (200 - successful), NOT FOUND (404 - Id doesn't exists), BAD REQUEST (400 - unsuccessful)
     * and updated Organization object in JSON format.
     */
    @RequestMapping(method = RequestMethod.POST, value = "/{id}")
    @ResponseBody
    public ResponseEntity updateOrganization(
            @PathVariable ("id") String id,
            @RequestParam(value = "name", required = true) String name,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "street", required = false) String street,
            @RequestParam(value = "city", required = false) String city,
            @RequestParam(value = "state", required = false) String state,
            @RequestParam(value = "zip", required = false) String zip) {
        try {
            if(id.isEmpty()||id==null)
                throw new MissingServletRequestParameterException("id", "String");
            Organization organization = this.organizationService.getOrganization(id);
            if (organization==null)
                return new ResponseEntity<>("Organization not found", HttpStatus.NOT_FOUND);
            else
            {   Address address = organization.getAddress();
                if(name!=null)
                    organization.setName(name);
                if(description!=null)
                    organization.setDescription(description);
                if(street!=null)
                    address.setStreet(street);
                if(state!=null)
                    address.setState(state);
                if(city!=null)
                    address.setCity(city);
                if(zip!=null)
                    address.setZip(zip);
                organization.setAddress(address);
                organization=this.organizationService.updateOrganization(organization);
                return new ResponseEntity<>(organization, HttpStatus.OK);
            }
        } catch (MissingServletRequestParameterException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
