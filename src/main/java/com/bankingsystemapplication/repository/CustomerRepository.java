package com.bankingsystemapplication.repository;

import com.bankingsystemapplication.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository  extends JpaRepository<Customer,Long> {
    Customer findCustomerByIdnumber(Long idnumber);

    Customer findCustomerByEmail(String email);

    List<Customer> findByLastnameContaining(String lastname);
}
