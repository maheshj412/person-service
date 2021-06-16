package com.hometest.accela.person.service;

import java.util.List;

import com.hometest.accela.person.entity.Address;
import com.hometest.accela.person.entity.Person;
import com.hometest.accela.person.exception.AddressNotFoundException;
import com.hometest.accela.person.exception.PersonNotFoundException;

public interface PersonService {
    public Person addPerson(final Person person);

    public Person editPerson(final Long personId, final Person person);

    public void deletePerson(final Long personId) throws PersonNotFoundException;

    public Person addAddressToPerson(final Long personId, final Person person) throws PersonNotFoundException;

    public void editAddress(final Person person) throws AddressNotFoundException;

    public void deleteAddress(final Long personId, final Long addressId) throws AddressNotFoundException;

    public long getCountOfPersons();

    public List<Person> getPersons();

    public Person findById(final Long personId);

    Address editAddress(final Long personId, final Long addressId, final Address address) throws AddressNotFoundException;
}
