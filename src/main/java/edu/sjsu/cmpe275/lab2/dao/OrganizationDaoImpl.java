package edu.sjsu.cmpe275.lab2.dao;

import edu.sjsu.cmpe275.lab2.model.Address;
import edu.sjsu.cmpe275.lab2.model.Organization;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * @author Nakul Sharma
 * Implementation class for Organization DAO interface methods.
 * Repository annotation to mark the class as repository entity for an organization
 */
@Repository
public class OrganizationDaoImpl extends AbstractDao implements IOrganizationDao{

    /**
     * Variable of type logger to print data on console
     */
    private static final Logger logger = LoggerFactory.getLogger(OrganizationDaoImpl.class);
    /**
     * Object to hold the current session for hibernate connection
     */
    private Session session;

    /**
     * Method to add an organization into database
     * @param name Name of an organization
     * @param description Short description of an organization
     * @param address Address of an organization
     * @return Object of an added organization.
     */
    @Override
    public Organization addOrganization(String name, String description, Address address)
    {
        Organization organization = new Organization(name, description, address);
        session = getSession();
        session.save(organization);
        session.flush();
        logger.debug(organization.getName() + " added successfully");
        return organization;
    }

    /**
     * Method to update an organization into database
     * @param organization Object of an organization
     * @return Object of an updated organization
     */
    @Override
    public Organization updateOrganization(Organization organization)
    {
        session =getSession();
        session.update(organization);
        logger.debug(organization.getName() + " updated successfully");
        return organization;
    }

    /**
     * Method to get an organization details from database
     * @param id Id of an existing organization
     * @return Object of an existing organization
     */
    @Override
    public Organization getOrganization(String id){
        session = getSession();
        long p_id = Long.parseLong(id);
        Organization organization = (Organization) session.get(Organization.class, p_id);
        if (organization == null)
            logger.debug("Returns null while retrieving the organization of id " + p_id);
        else
            logger.debug("Organization of id " + p_id + " exists in database.");
        return organization;
    }

    /**
     * Method to delete an organization from database
     * @param organization Object of an organization that needs to be deleted
     */
    @Override
    public void deleteOrganization(Organization organization)
    {
        session=getSession();
        session.delete(organization);
        logger.debug(organization.getName() + " deleted successfully");
    }
}
