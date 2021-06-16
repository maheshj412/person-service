package com.hometest.accela.person.entity;

import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import lombok.*;
import org.hibernate.validator.constraints.Length;

/**
 * Person Entity class and used joincolumn to map address of person.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Person{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Please provide person firstname")
    @Length(max = 15,min =3, message = "Person firstname must have min 3 and max 15 letters")
    private String firstName;
    @NotBlank(message = "Please provide person lastname")
    private String lastName;

    @OneToMany(targetEntity = Address.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "pa_fk", referencedColumnName = "id")
    private List<Address> addresses;
}
