package com.bankingsystemapplication.controller;

import com.bankingsystemapplication.form.DepositForm;
import com.bankingsystemapplication.model.Account;
import com.bankingsystemapplication.model.Customer;
import com.bankingsystemapplication.service.AccountService;
import com.bankingsystemapplication.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class AccountController {
    private AccountService accountService;
    private CustomerService customerService;

    public AccountController(AccountService accountService, CustomerService customerService) {
        this.accountService = accountService;
        this.customerService = customerService;
    }

    @GetMapping("/account")
    public ResponseEntity<List<Account>> fetchAccountResponseEntity(){
        return new ResponseEntity<>(accountService.fetchAccount(), HttpStatus.OK);
    }
    @PostMapping("/account/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Account> createAccountResponseEntity(@RequestBody Account account ){
        Customer customer=customerService.fetchCustomerByIdnumber(account.getCustomer().getIdnumber());
        return new ResponseEntity<>(accountService.createAccount(account,customer),HttpStatus.CREATED);
    }
    @PutMapping("/account/deposit")
    public ResponseEntity<Account> depositAmountResponseEntity(@RequestBody DepositForm depositForm){
        Account account=accountService.depositFunds(depositForm);
        if(account!=null){
            throw  new ResponseStatusException(HttpStatus.OK,"Account credited  successfully");
        }
        return new ResponseEntity<>(account,HttpStatus.CREATED);
    }
    @PutMapping("/account/withdraw")
    public ResponseEntity<Account> withdrawAmountResponseEntity(@RequestBody DepositForm depositForm){
        Account account=accountService.withdrawFunds(depositForm);
        if(account!=null){
            throw  new ResponseStatusException(HttpStatus.OK,"Funds Successfully withdrew" + depositForm.getAmount());
        }
        return new ResponseEntity<>(account,HttpStatus.CREATED);
    }
    @GetMapping("/account/find/{accountNo}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Account> fetchAccountResponseEntity(@PathVariable("accountNo") String accountNo){
        Account account=accountService.fetchAccountByAccountNo(accountNo);
        if (account==null){
            throw new  ResponseStatusException(HttpStatus.BAD_REQUEST,"Sorry account not found");
        }
        return new ResponseEntity<>(account,HttpStatus.OK);
    }
}
