package edu.sjsu.cmpe275.lab2.service;

import edu.sjsu.cmpe275.lab2.dao.IOrganizationDao;
import edu.sjsu.cmpe275.lab2.model.Address;
import edu.sjsu.cmpe275.lab2.model.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Nakul Sharma
 * Implementation class to provide services available for the organization.
 * Service annotation to mark the class as service class in application context
 * Transactional annotation to make the class transactional entity i.e. it will be
 * counted a single transaction
 */
@Service
@Transactional("tx1")
public class OrganizationServiceImpl implements IOrganizationService{

    /**
     * Autowire the Organization DAO interface object
     */
    @Autowired
    private IOrganizationDao organizationDao;

    /**
     * Method to add an organization into database
     * @param name Name of an organization
     * @param description Short description of an organization
     * @param address Address of an organization
     * @return Object of an added organization.
     */
    public Organization addOrganization(String name, String description, Address address){
        return organizationDao.addOrganization(name,description,address);
    }

    /**
     * Method to update an organization into database
     * @param organization Object of an organization
     * @return Object of an updated organization
     */
    public Organization updateOrganization (Organization organization){
        organizationDao.updateOrganization(organization);
        return organization;
    }

    /**
     * Method to get an organization details from database
     * @param id Id of an existing organization
     * @return Object of an existing organization
     */
    public Organization getOrganization(String id){
        return organizationDao.getOrganization(id);
    }

    /**
     * Method to delete an organization from database
     * @param organization Object of an organization that needs to be deleted
     */
    public void deleteOrganization(Organization organization){
        organizationDao.deleteOrganization(organization);
    }
}
