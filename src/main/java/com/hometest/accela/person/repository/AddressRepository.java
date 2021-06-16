package com.hometest.accela.person.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hometest.accela.person.entity.Address;

/**
 * Address Repository Interface extended to Spring Jpa repository, hence no need to write common methods
 */
@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    @Query(value = "select * from person p join address a on p.id=?2 and a.id=?1", nativeQuery = true)
    Optional<Address> findByIdAndPersonId(final Long id, final Long personId);
}
