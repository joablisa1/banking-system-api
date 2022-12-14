package com.bankingsystemapplication.controller;

import com.bankingsystemapplication.form.CustomerForm;
import com.bankingsystemapplication.model.Category;
import com.bankingsystemapplication.model.Customer;
import com.bankingsystemapplication.service.CategoryService;
import com.bankingsystemapplication.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class CustomerController {

   private CustomerService customerService;
   private CategoryService categoryService;

    public CustomerController(CustomerService customerService, CategoryService categoryService) {
        this.customerService = customerService;
        this.categoryService = categoryService;
    }
    @GetMapping("/customer")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Customer>> findAllCustomerEntity(@RequestParam(required = false)String lastname){
        List<Customer> customers=new ArrayList<>();
        if(lastname==null){
            customerService.findAllCustomer().forEach(customers::add);
        }else{
           customerService.findCustomerByFirstNameAndLastName(lastname).forEach(customers::add);
        }
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }
    @PostMapping("/customer/save")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Customer> saveCustomerResponseEntity(@RequestBody CustomerForm customerForm){
        Category category=categoryService.findCategoryById(customerForm.getCategoryId());
        if(customerService.isCustomerPresent(customerForm.getEmail())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Sorry customer  with this email Address exist." + customerForm.getEmail());
        }
       return  new ResponseEntity<>(customerService.saveCustomer(customerForm,category),HttpStatus.ACCEPTED);
    }

    @GetMapping("/customer/{id}")
    public ResponseEntity<Customer> findCustomerByIdResponseEntity(@PathVariable("id") Long id){
        Customer customer=customerService.findCustomerById(id);
        return new ResponseEntity<>(customer,HttpStatus.OK);
    }
    @PutMapping("/customer/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Customer> updateCustomerResponseEntity(@PathVariable("id")Long id,@RequestBody Customer customer){
        Category category=categoryService.findCategoryByName(customer.getCategory().getCategoryName());
        customerService.updateCustomer(customer,category);
        customer.setId(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
    @DeleteMapping("/customer/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteCustomerResponseEntity(@PathVariable("id") Long id){
        customerService.deleteCustomerById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
