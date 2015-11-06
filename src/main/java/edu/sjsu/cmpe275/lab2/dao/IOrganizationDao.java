package edu.sjsu.cmpe275.lab2.dao;

import edu.sjsu.cmpe275.lab2.model.Address;
import edu.sjsu.cmpe275.lab2.model.Organization;

/**
 * Created by Nakul on 05-Nov-15.
 * Interface to define abstract methods of organization's data access objects.
 */
public interface IOrganizationDao {
    Organization addOrganization(String name, String description, Address address);
    Organization updateOrganization (Organization organization);
    Organization getOrganization(String id);
    void deleteOrganization(Organization organization);
}
