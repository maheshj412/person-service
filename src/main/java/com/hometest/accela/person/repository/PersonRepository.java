package com.hometest.accela.person.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hometest.accela.person.entity.Person;

/**
 * Person Repository Interface extended to Spring Jpa repository, hence no need to write common methods
 */
@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
}
