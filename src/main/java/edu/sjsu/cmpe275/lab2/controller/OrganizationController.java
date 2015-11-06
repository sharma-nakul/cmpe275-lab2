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
 * Created by Nakul on 05-Nov-15.
 * Controller class to communicate with Organization related APIs.
 */

@RestController
@RequestMapping(value = "api/v1/org",
        produces = {"application/xml", "application/json", "text/html"},
        consumes = MediaType.APPLICATION_JSON_VALUE)
public class OrganizationController extends Throwable {

    private static final Logger logger = LoggerFactory.getLogger(OrganizationController.class);

    @Autowired
    private IOrganizationService organizationService;

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
