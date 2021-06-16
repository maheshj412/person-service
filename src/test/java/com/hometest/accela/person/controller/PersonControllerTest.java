package com.hometest.accela.person.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.hometest.accela.person.dto.PersonAddress;
import com.hometest.accela.person.entity.Address;
import com.hometest.accela.person.entity.Person;
import com.hometest.accela.person.service.PersonService;

@ExtendWith(SpringExtension.class)
@WebMvcTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PersonControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private PersonService personService;

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
    void testAddPersonRestEndpoint() throws Exception {
        Mockito.when(personService.addPerson(newPerson)).thenReturn(newPerson);
        ObjectMapper mapper = new ObjectMapper();
        String personAsJson = mapper.writeValueAsString(newPerson);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/personservice/addperson")
                .contentType(MediaType.APPLICATION_JSON)
                .content(personAsJson)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    void testAddAddressToASavedPersonRestEndpoint() throws Exception {
        newPerson.setAddresses(Arrays.asList(address1, address2));
        Mockito.when(personService.addAddressToPerson(1L, newPerson)).thenReturn(newPerson);
        ObjectMapper mapper = new ObjectMapper();
        String personAsJson = mapper.writeValueAsString(newPerson);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/personservice/addpersonaddress/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(personAsJson)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    void testAddAddressToNewPersonRestEndpoint() throws Exception {
        newPerson.setAddresses(Arrays.asList(address1, address2));
        PersonAddress personWithAddress = PersonAddress.builder().person(newPerson).build();
        Mockito.when(personService.addPerson(newPerson)).thenReturn(newPerson);
        ObjectMapper mapper = new ObjectMapper();
        String personAsJson = mapper.writeValueAsString(personWithAddress);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/personservice/addaddress")
                .contentType(MediaType.APPLICATION_JSON)
                .content(personAsJson)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    void testEditPersonFirstNameEndpoint() throws Exception {
        newPerson.setFirstName("New John");
        Mockito.when(personService.editPerson(1L, newPerson)).thenReturn(newPerson);
        ObjectMapper mapper = new ObjectMapper();
        String personAsJson = mapper.writeValueAsString(newPerson);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/personservice/editperson/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(personAsJson)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", Matchers.is(newPerson.getFirstName())))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void testEditPersonLastNameEndpoint() throws Exception {
        newPerson.setLastName("New Mee");
        Mockito.when(personService.editPerson(1L, newPerson)).thenReturn(newPerson);
        ObjectMapper mapper = new ObjectMapper();
        String personAsJson = mapper.writeValueAsString(newPerson);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/personservice/editperson/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(personAsJson)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", Matchers.is(newPerson.getLastName())))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void testEditAddressEndpoint() throws Exception {
        newPerson.setAddresses(Arrays.asList(address1, address2));
        address1.setStreet("Famous Street");
        Mockito.when(personService.editAddress(1L, 1L, address1)).thenReturn(address1);
        ObjectMapper mapper = new ObjectMapper();
        String personAsJson = mapper.writeValueAsString(address1);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/personservice/editaddress/1/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(personAsJson)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.street", Matchers.is("Famous Street")))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void testDeleteAddressEndPoint() throws Exception {
        newPerson.setAddresses(Arrays.asList(address1, address2));
        Mockito.when(personService.findById(1L)).thenReturn(newPerson);
        newPerson.setAddresses(Arrays.asList(address1));
        Mockito.doNothing().when(personService).deleteAddress(1L, 1L);
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/personservice/deleteaddress/1/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testDeletePersonEndPoint() throws Exception {
        Mockito.when(personService.findById(1L)).thenReturn(newPerson);
        Mockito.doNothing().when(personService).deletePerson(1L);
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/personservice/deleteperson/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testGetPersonsEndpoint() throws Exception {
        Mockito.when(personService.getPersons()).thenReturn(Arrays.asList(newPerson, updatedPerson));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/personservice/getallpersons")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void testCountOfPersonEndpoint() throws Exception {
        Mockito.when(personService.getCountOfPersons()).thenReturn(5L);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/personservice/count")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.is(5)))
                .andDo(MockMvcResultHandlers.print());
    }

}
