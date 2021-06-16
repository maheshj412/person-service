package com.hometest.accela.person.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hometest.accela.person.entity.Address;
import com.hometest.accela.person.entity.Person;
import com.hometest.accela.person.exception.AddressNotFoundException;
import com.hometest.accela.person.exception.PersonNotFoundException;
import com.hometest.accela.person.repository.AddressRepository;
import com.hometest.accela.person.repository.PersonRepository;

/**
 * Person Service to make calls to database and handle multiple cases
 */
@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Override
    public Person addPerson(final Person person) {
        return personRepository.save(person);
    }

    @Override
    public Person editPerson(final Long personId, final Person person) {

        Person personDB= personRepository.findById(personId).get();
        personDB.setFirstName(person.getFirstName());
        personDB.setLastName(person.getLastName());

        return personRepository.save(personDB);
    }

    @Override
    public void deletePerson(final Long personId) throws PersonNotFoundException{
        Optional<Person> person = personRepository.findById(personId);
        if(!person.isPresent()) {
            throw new PersonNotFoundException("Requested Person details are Not Available");
        }
        personRepository.deleteById(personId);
    }

    @Override
    public Person addAddressToPerson(final Long personId, final Person person) throws PersonNotFoundException {
        Optional<Person> personFromDb = null;
        if(personId >=0 ) {
            personFromDb=personRepository.findById(personId);
            if (!personFromDb.isPresent()) {
                throw new PersonNotFoundException("Requested Person details are Not Available");
            }
        }
        personFromDb.get().setAddresses(person.getAddresses());
        return personRepository.save(personFromDb.get());
    }

    @Override
    public void editAddress(final Person person) throws AddressNotFoundException{
        Optional<Long> addressId = Optional.ofNullable(person.getAddresses().get(0).getId());
        Optional<Long> personId = Optional.ofNullable(person.getId());
        final Optional<Address> addressDb = addressRepository.findByIdAndPersonId(addressId.get(), personId.get());
        if(!addressDb.isPresent()) {
            throw new AddressNotFoundException("Address Not available for the requested information");
        }
        Address address = person.getAddresses().get(0);
        addressDb.get().setStreet(address.getStreet());
        addressDb.get().setCity(address.getCity());
        addressDb.get().setState(address.getState());
        addressDb.get().setPostalCode(address.getPostalCode());
        addressRepository.save(addressDb.get());
    }

    @Override
    public void deleteAddress(final Long personId, final Long addressId) throws AddressNotFoundException {
        final Optional<Address> addressDb = addressRepository.findByIdAndPersonId(addressId, personId);
        if(!addressDb.isPresent()) {
            throw new AddressNotFoundException("Address Not available for the requested information");
        }
        addressRepository.delete(addressDb.get());
    }

    @Override
    public long getCountOfPersons() {
        return personRepository.count();
    }

    @Override
    public List<Person> getPersons() {
        return personRepository.findAll();
    }

    @Override
    public Person findById(final Long personId) {
        return personRepository.findById(personId).get();
    }

    @Override
    public Address editAddress(Long personId, Long addressId, Address address) throws AddressNotFoundException {
        final Optional<Address> addressDb = addressRepository.findByIdAndPersonId(addressId, personId);
        if(!addressDb.isPresent()) {
            throw new AddressNotFoundException("Address Not available for the requested information");
        }
        Address oldAddress = addressDb.get();
        oldAddress.setStreet(address.getStreet());
        oldAddress.setCity(address.getCity());
        oldAddress.setState(address.getState());
        oldAddress.setPostalCode(address.getPostalCode());
        return addressRepository.save(oldAddress);
    }
}
