package edu.sjsu.cmpe275.lab2.service;

import edu.sjsu.cmpe275.lab2.model.Address;
import edu.sjsu.cmpe275.lab2.model.Organization;

/**
 * Created by Nakul on 05-Nov-15.
 * Abstract class to provide all the services related to organization.
 */
public interface IOrganizationService {

    Organization addOrganization(String name, String description, Address address);
    Organization updateOrganization (Organization organization);
    Organization getOrganization(String id);
    void deleteOrganization(Organization organization);
}
