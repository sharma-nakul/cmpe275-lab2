package edu.sjsu.cmpe275.lab2.service;

import edu.sjsu.cmpe275.lab2.model.Address;
import edu.sjsu.cmpe275.lab2.model.Organization;

/**
 * @author Nakul Sharma
 * Abstract handler class to provide all the services related to organization.
 */
public interface IOrganizationService {

    /**
     * Abstract method to add an organization into database
     * @param name Name of an organization
     * @param description Short description of an organization
     * @param address Address of an organization
     * @return Object of an added organization.
     */
    Organization addOrganization(String name, String description, Address address);

    /**
     * Abstract method to update an organization into database
     * @param organization Object of an organization
     * @return Object of an updated organization
     */
    Organization updateOrganization (Organization organization);

    /**
     * Abstract method to get an organization details from database
     * @param id Id of an existing organization
     * @return Object of an existing organization
     */
    Organization getOrganization(String id);

    /**
     * Abstract method to delete an organization from database
     * @param organization Object of an organization that needs to be deleted
     */
    void deleteOrganization(Organization organization);
}
