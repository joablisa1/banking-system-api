package com.bankingsystemapplication.form;

import com.bankingsystemapplication.model.Customer;

public class CustomerForm {
    private Long id;
    private String firstname;
    private String lastname;
    private Long idnumber;
    private String phone;
    private String email;
    private String password;
    private Long categoryId;

    public CustomerForm() {
    }

    public CustomerForm(Customer customer) {
        this.setId(customer.getId());
        this.setFirstname(customer.getFirstname());
        this.setLastname(customer.getLastname());
        this.setEmail(customer.getEmail());
        this.setIdnumber(customer.getIdnumber());
        this.setPhone(customer.getPhone());
        this.setPassword(customer.getPassword());
        this.setCategoryId(customer.getCategory().getId());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Long getIdnumber() {
        return idnumber;
    }

    public void setIdnumber(Long idnumber) {
        this.idnumber = idnumber;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}
