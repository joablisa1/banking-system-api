package com.bankingsystemapplication.service;

import com.bankingsystemapplication.form.CustomerForm;
import com.bankingsystemapplication.model.Category;
import com.bankingsystemapplication.model.Customer;
import com.bankingsystemapplication.model.Roles;
import com.bankingsystemapplication.repository.CustomerRepository;
import com.bankingsystemapplication.repository.RolesRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CustomerService {

    private CustomerRepository customerRepository;
    private RolesRepository rolesRepository;
    private PasswordEncoder passwordEncoder;


    public CustomerService(CustomerRepository customerRepository, RolesRepository rolesRepository, PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.rolesRepository = rolesRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public static Customer saveCustomerForm(CustomerForm customerForm, Category category) {
        Customer customer=new Customer();
        customer.setFirstname(customerForm.getFirstname());
        customer.setLastname(customerForm.getLastname());
        customer.setPhone(customerForm.getPhone());
        customer.setEmail(customerForm.getEmail());
        customer.setIdnumber(customerForm.getIdnumber());
        customer.setPassword(customerForm.getPassword());
        customer.setCategory(category);
        return customer;
    }

    public Customer saveCustomer(CustomerForm customerForm, Category category) {
        Roles role = rolesRepository.findRoleByName("ROLE_USER");
        List<Roles> roles = new ArrayList<>();
        roles.add(role);
        Customer customer=saveCustomerForm(customerForm,category);
        customer.setPassword(passwordEncoder.encode(customerForm.getPassword()));
        customer.setRoles(roles);
        customer.setCategory(category);
        customerRepository.save(customer);
        return customer;
    }

    public void updateCustomer(Customer customer, Category category) {
        customer.setCategory(category);
        customer.setCompleted(true);
        customerRepository.save(customer);
    }

    public List<Customer> findCustomerByFirstNameAndLastName(String lastname) {
        return customerRepository.findByLastnameContaining(lastname);
    }
    public List<Customer> findAllCustomer() {
        return customerRepository.findAll();
    }

    public Customer fetchCustomerByIdnumber(Long idnumber) {
        Customer customer = customerRepository.findCustomerByIdnumber(idnumber);
        return customer;
    }

    public Customer findCustomerById(Long id) {
        return customerRepository.findById(id).get();
    }

    public void deleteCustomerById(Long id) {
        customerRepository.deleteById(id);
    }

    public boolean isCustomerPresent(String email) {
        Customer customer = customerRepository.findCustomerByEmail(email);
        if (customer != null)
            return true;
        return false;
    }

    public  Customer loginCustomer(String email,String password){
        Customer customer=customerRepository.findCustomerByEmail(email);
        if(!passwordEncoder.matches(password,customer.getPassword())){
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST,"invalid credentatial");
        }
        return null;
    }

}
