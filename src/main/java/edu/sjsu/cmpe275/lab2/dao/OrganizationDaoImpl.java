package edu.sjsu.cmpe275.lab2.dao;

import edu.sjsu.cmpe275.lab2.model.Address;
import edu.sjsu.cmpe275.lab2.model.Organization;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * Created by Nakul on 05-Nov-15.
 * Implementation class for Organization DAO interface methods.
 */
@Repository
public class OrganizationDaoImpl extends AbstractDao implements IOrganizationDao{

    private static final Logger logger = LoggerFactory.getLogger(OrganizationDaoImpl.class);
    private Session session;

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

    @Override
    public Organization updateOrganization(Organization organization)
    {
        session =getSession();
        session.update(organization);
        logger.debug(organization.getName() + " updated successfully");
        return organization;
    }

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

    @Override
    public void deleteOrganization(Organization organization)
    {
        session=getSession();
        session.delete(organization);
        logger.debug(organization.getName() + " deleted successfully");
    }
}
