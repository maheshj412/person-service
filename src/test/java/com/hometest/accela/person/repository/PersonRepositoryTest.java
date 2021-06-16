package com.hometest.accela.person.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.hometest.accela.person.entity.Person;
import com.hometest.accela.person.entity.Address;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PersonRepositoryTest {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private AddressRepository addressRepository;

    Person newPerson = Person.builder().firstName("Joe").lastName("Dow").build();

    @BeforeAll
    void setup() {
        Person person = Person.builder().firstName("John").lastName("Mee").build();
        personRepository.save(person);
    }

    @Test
    public void whenFindByPersonId_ThenReturnPerson() {
        Person person = personRepository.findById(1L).get();
        assertEquals(person.getFirstName(), "John");
        assertEquals(person.getLastName(), "Mee");
        assertTrue(person.getAddresses().isEmpty());
    }

    @Test
    public void savePersonWithoutAddress() {
        Person savedPerson = personRepository.save(newPerson);
        assertEquals(newPerson, savedPerson);
    }

    @Test
    public void whenSaveMultipleEntryCheckTheSizeOfEntries() {
        personRepository.save(Person.builder().firstName("firstname1").lastName("lastname1").build());
        personRepository.save(Person.builder().firstName("firstname2").lastName("lastname2").build());
        personRepository.save(Person.builder().firstName("firstname3").lastName("lastname3").build());
        assertEquals(4, personRepository.count());
    }

    @Test
    public void editFirstAndLastNameOfASavedPerson() {
        Person person = personRepository.getById(1L);
        person.setFirstName("NewJohn");
        person.setLastName("NewMee");
        personRepository.save(person);
        Person updatedPerson = personRepository.getById(1L);
        assertEquals("NewJohn", updatedPerson.getFirstName());
        assertEquals("NewMee", updatedPerson.getLastName());
    }

    @Test
    public void deleteSavedPerson() {
        Person dummyPerson = Person.builder().firstName("firstname1").lastName("lastname1").build();
        personRepository.save(dummyPerson);
        assertEquals(2, personRepository.count());
        personRepository.delete(dummyPerson);
        assertEquals(1, personRepository.count());
    }

    @Test
    public void saveAndDeleteAndCheckPersonsCount() {
        personRepository.save(Person.builder().firstName("firstname1").lastName("lastname1").build());
        personRepository.save(Person.builder().firstName("firstname2").lastName("lastname2").build());
        personRepository.save(Person.builder().firstName("firstname3").lastName("lastname3").build());
        List<Person> personList = personRepository.findAll();
        personList.stream().forEach(personRepository::delete);
        assertTrue(personRepository.count() == 0);
    }

    @Test
    public void addAddressToANewPerson(){
        List<Address> address = Arrays.asList(Address.builder().street("Wall Street").city("New York").state("NY").postalCode("10005").build());
        Person dummyPerson = Person.builder().firstName("firstname1").lastName("lastname1").addresses(address).build();
        Person setAndSavedPerson = personRepository.save(dummyPerson);
        assertEquals(dummyPerson.getAddresses().get(0), setAndSavedPerson.getAddresses().get(0));
    }


    @Test
    public void checkPersonAndAddressCount(){
        List<Address> address = Arrays.asList(Address.builder().street("Wall Street").city("New York").state("NY").postalCode("10005").build());
        Person dummyPerson = Person.builder().firstName("firstname1").lastName("lastname1").addresses(address).build();
        personRepository.save(dummyPerson);
        assertEquals(2,personRepository.count());
        assertEquals(1, addressRepository.count());
    }

    @Test
    public void editFirstNameOfASavedPerson() {
        Person person = personRepository.getById(1L);
        person.setFirstName("NewJohn");
        personRepository.save(person);
        assertEquals("NewJohn", personRepository.getById(1L).getFirstName());
    }

    @Test
    public void editLastNameOfASavedPerson() {
        Person person = personRepository.getById(1L);
        person.setLastName("NewMee");
        personRepository.save(person);
        assertEquals("NewMee", personRepository.getById(1L).getLastName());
    }

}
