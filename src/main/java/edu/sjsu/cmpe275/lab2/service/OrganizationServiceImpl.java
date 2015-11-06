package edu.sjsu.cmpe275.lab2.service;

import edu.sjsu.cmpe275.lab2.dao.IOrganizationDao;
import edu.sjsu.cmpe275.lab2.model.Address;
import edu.sjsu.cmpe275.lab2.model.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Nakul on 05-Nov-15.
 * Class to implement all the services available for the organization.
 */

@Service
@Transactional("tx1")
public class OrganizationServiceImpl implements IOrganizationService{

    @Autowired
    private IOrganizationDao organizationDao;

    public Organization addOrganization(String name, String description, Address address){
        return organizationDao.addOrganization(name,description,address);
    }

    public Organization updateOrganization (Organization organization){
        organizationDao.updateOrganization(organization);
        return organization;
    }

    public Organization getOrganization(String id){
        return organizationDao.getOrganization(id);
    }

    public void deleteOrganization(Organization organization){
        organizationDao.deleteOrganization(organization);
    }
}
