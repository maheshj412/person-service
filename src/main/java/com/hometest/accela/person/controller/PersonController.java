package com.hometest.accela.person.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.hometest.accela.person.dto.PersonAddress;
import com.hometest.accela.person.entity.Address;
import com.hometest.accela.person.entity.Person;
import com.hometest.accela.person.exception.AddressNotFoundException;
import com.hometest.accela.person.exception.PersonNotFoundException;
import com.hometest.accela.person.service.PersonService;

/**
 * PersonController handle adding/editing/deleting person along with adding/updating/deleting address of person.
 * Also, endpoints available to get all persons and count of persons.
 */
@RestController
@RequestMapping(value = {"/api/v1/personservice"})
public class PersonController {

    @Autowired
    private PersonService personService;

    private final Logger LOGGER =
            LoggerFactory.getLogger(PersonController.class);

    @PostMapping(value = "/addperson", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Person addPerson(@Valid @RequestBody Person person) {
        LOGGER.info("Adding person: {}", person.toString());
        return personService.addPerson(person);
    }

    @PutMapping(value = "/addpersonaddress/{personid}", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Person addAddressToPerson(@PathVariable(name = "personid") final Long personId, @Valid @RequestBody PersonAddress personAddress) throws PersonNotFoundException {
        LOGGER.info("Add address(s) to person: {}", personId);
        return personService.addAddressToPerson(personId, personAddress.getPerson());
    }

    @PostMapping(value = "/addaddress", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Person addAddress(@Valid @RequestBody PersonAddress personAddress) {
        LOGGER.info("Add person along with address: {}", personAddress.getPerson());
        return personService.addPerson(personAddress.getPerson());
    }

    @PutMapping(value = "/editaddress/{personid}/{addressid}", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Address editAddress(@PathVariable(name = "personid") final Long personId, @PathVariable(name = "addressid") final Long addressId, @RequestBody Address address) throws AddressNotFoundException {
        LOGGER.info("Edit address {} from person record: {} with new address {}", addressId, personId, address.toString());
        return personService.editAddress(personId, addressId, address);
    }

    @PutMapping("/editperson/{personid}")
    public Person editPerson(@PathVariable(name = "personid") final Long personId, @Valid @RequestBody Person person) {
        LOGGER.info("Editing/updating person {} data: ", personId, person.toString());
        return personService.editPerson(personId, person);
    }

    @DeleteMapping("/deleteperson/{personid}")
    public void deletePerson(@PathVariable(name = "personid") final Long personId) throws PersonNotFoundException {
        LOGGER.info("delete person: {}", personId);
        personService.deletePerson(personId);
    }

    @DeleteMapping("/deleteaddress/{personid}/{addressid}")
    public void deleteAddress(@PathVariable(name = "personid") final Long personId, @PathVariable(name = "addressid") final Long addressId) throws AddressNotFoundException {
        LOGGER.info("Delete address {} from person record: {}", addressId, personId);
        personService.deleteAddress(personId, addressId);
    }

    @GetMapping("/count")
    public Long getCountOfPersons() {
        final Long count = personService.getCountOfPersons();
        LOGGER.info("Count of persons in the system: {}", count);
        return count;
    }

    @GetMapping("/getallpersons")
    public List<Person> getAllPersons() {
        List<Person> personList = personService.getPersons();
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("All Persons in the system: {}", personList);
        }
        return personList;
    }


}