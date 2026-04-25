package com.example.orm_exercise.repositories;

import com.example.orm_exercise.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Integer> {
    List<Address> findByContactId(int contactId);
}
