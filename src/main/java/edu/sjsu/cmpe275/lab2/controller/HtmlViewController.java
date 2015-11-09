package edu.sjsu.cmpe275.lab2.controller;

import edu.sjsu.cmpe275.lab2.model.Organization;
import edu.sjsu.cmpe275.lab2.model.Person;
import edu.sjsu.cmpe275.lab2.service.IOrganizationService;
import edu.sjsu.cmpe275.lab2.service.IPersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HtmlViewController {

    private static final Logger logger = LoggerFactory.getLogger(HtmlViewController.class);

    @Autowired
    IOrganizationService organizationService;

    @Autowired
    IPersonService personService;

    /**
     * Simply selects the home view to render by returning its name.
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home() {
        return "home";
    }

    @RequestMapping(value = "org/{id}", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public String getOrgInHtml(
            @PathVariable("id") String id, Model model) {
        try {
            Organization organization = this.organizationService.getOrganization(id);
            if (organization != null) {
                model.addAttribute("organization", organization);
                return "org";
            } else
                return "error";
        } catch (Exception e) {
            return "error";
        }
    }

    @RequestMapping(value = "person/{id}", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public String getPersonInHtml(
            @PathVariable("id") String id, Model model) {
        try {
            Person person = this.personService.getPerson(id);
            if (person != null) {
                List<Person> friends = person.getFriends();
                model.addAttribute("friends",friends);
                model.addAttribute("person", person);
                return "person";
            } else
                return "error";
        } catch (Exception e) {
            return "error";
        }
    }
}
