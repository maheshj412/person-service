package com.hometest.accela.person.dto;

import lombok.*;

import com.hometest.accela.person.entity.Person;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class PersonAddress {

    private Person person;
}
