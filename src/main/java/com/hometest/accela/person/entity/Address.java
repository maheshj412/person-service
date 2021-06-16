package com.hometest.accela.person.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.*;
import org.hibernate.validator.constraints.Length;

/**
 * Address Entity Class.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
@ToString
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Please provide street of address")
    @Length(min =3, message = "Street name must be min 3 letters")
    private String street;

    @NotBlank(message = "Please provide city of address")
    private String city;

    @NotBlank(message = "Please provide state of address")
    private String state;

    @NotBlank(message = "Please provide postal code of address")
    @Size(max = 8, min = 5, message = "Invalid postal code, postal code must have min 6 and max 8 letters")
    private String postalCode;
}
