package com.hometest.accela.person.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.hometest.accela.person.entity.Address;
import com.hometest.accela.person.entity.Person;
import com.hometest.accela.person.exception.AddressNotFoundException;
import com.hometest.accela.person.exception.PersonNotFoundException;
import com.hometest.accela.person.repository.AddressRepository;
import com.hometest.accela.person.repository.PersonRepository;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PersonServiceTest {

    @MockBean
    PersonRepository personRepository;

    @MockBean
    AddressRepository addressRepository;

    @Autowired
    PersonService personService;

    Person newPerson;
    Person updatedPerson;
    Address address1;
    Address address2;

    @BeforeAll
    void setUp() {
        newPerson = Person.builder().id(1L).firstName("John").lastName("Mee").build();
        updatedPerson = Person.builder().firstName("Joe").lastName("Dingle").build();
        address1 = Address.builder().id(1L).street("Wall Street").city("New York").state("NY").postalCode("10005").build();
        address2 = Address.builder().id(2L).street("Broadway").city("North Carolina").state("Carolina").postalCode("27505").build();
    }

    @Test
    void testAddPersonWithMockito() {
        Mockito.when(personRepository.save(newPerson)).thenReturn(newPerson);
        Person savedPerson = personService.addPerson(newPerson);
        assertEquals(newPerson, savedPerson);
    }

    @Test
    void testEditPersonAndAssertWithUpdatedPerson() {
        Mockito.when(personRepository.save(updatedPerson)).thenReturn(updatedPerson);
        Mockito.when(personRepository.findById(2L)).thenReturn(Optional.of(updatedPerson));
        Person savedPerson = personService.editPerson(2L, updatedPerson);
        assertEquals(updatedPerson, savedPerson);
    }

    @Test
    void testDeletePerson() throws PersonNotFoundException {
        Mockito.when(personRepository.save(newPerson)).thenReturn(newPerson);
        Mockito.when(personRepository.findById(1L)).thenReturn(Optional.of(newPerson));
        personService.deletePerson(1L);
        assertTrue(personRepository.count() == 0);
    }

    @Test
    void testAddAddressToPerson() {
        Mockito.when(personRepository.save(newPerson)).thenReturn(newPerson);
        Mockito.when(personRepository.findById(1L)).thenReturn(Optional.of(newPerson));
        newPerson.setAddresses(Arrays.asList(address1, address2));
        Mockito.when(personRepository.save(newPerson)).thenReturn(newPerson);
        Mockito.when(personRepository.findById(1L)).thenReturn(Optional.of(newPerson));
        assertEquals(newPerson, personRepository.findById(1L).get());
    }

    @Test
    void testEditAddress() {
        Mockito.when(personRepository.save(newPerson)).thenReturn(newPerson);
        Mockito.when(personRepository.findById(1L)).thenReturn(Optional.of(newPerson));
        newPerson.setAddresses(Arrays.asList(address1, address2));
        Mockito.when(personRepository.save(newPerson)).thenReturn(newPerson);
        Mockito.when(personRepository.findById(1L)).thenReturn(Optional.of(newPerson));
        assertEquals(personRepository.findById(1L).get() , newPerson);
        newPerson.getAddresses().get(0).setStreet("Famous Street");
        Mockito.when(personRepository.save(newPerson)).thenReturn(newPerson);
        Mockito.when(personRepository.findById(1L)).thenReturn(Optional.of(newPerson));
        assertEquals("Famous Street" , personRepository.findById(1L).get().getAddresses().get(0).getStreet());
    }

    @Test
    void deleteAddress() throws AddressNotFoundException {
        newPerson.setAddresses(Arrays.asList(address1, address2));
        Mockito.when(personRepository.save(newPerson)).thenReturn(newPerson);
        Mockito.when(personRepository.findById(1L)).thenReturn(Optional.of(newPerson));
        assertEquals(2, personService.findById(1L).getAddresses().stream().count());
        newPerson.setAddresses(Arrays.asList(address1));
        Mockito.when(personRepository.save(newPerson)).thenReturn(newPerson);
        Mockito.when(addressRepository.findByIdAndPersonId(1L, 1L)).thenReturn(Optional.of(address1));
        personService.deleteAddress(1L, 1L);
        assertEquals(1, personService.findById(1L).getAddresses().stream().count());
    }

    @Test
    void getCountOfPersons() {
        Mockito.when(personRepository.save(newPerson)).thenReturn(newPerson);
        Mockito.when(personRepository.count()).thenReturn(1L);
        assertEquals(1L, personService.getCountOfPersons());
    }

    @Test
    void getPersons() {
        Person dummyPerson = Person.builder().id(2L).firstName("Catherine").lastName("Berl").addresses(Arrays.asList(address1, address2)).build();
        Mockito.when(personRepository.save(newPerson)).thenReturn(newPerson);
        Mockito.when(personRepository.save(dummyPerson)).thenReturn(dummyPerson);
        Mockito.when(personRepository.findAll()).thenReturn(Arrays.asList(newPerson, dummyPerson));
        List<Person> personList = personService.getPersons();
        assertEquals(newPerson, personList.get(0));
        assertEquals(dummyPerson, personList.get(1));
    }

}