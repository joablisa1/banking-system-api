package com.bankingsystemapplication.service;

import com.bankingsystemapplication.form.DepositForm;
import com.bankingsystemapplication.model.Account;
import com.bankingsystemapplication.model.Customer;
import com.bankingsystemapplication.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final CustomerService customerService;

    public AccountService(AccountRepository accountRepository, CustomerService customerService) {
        this.accountRepository = accountRepository;
        this.customerService = customerService;
    }

    public Account createAccount(Account account, Customer customer){
        account.setIsCompleted(true);
        account.setCustomer(customer);
        return accountRepository.save(account);
    }
    public  List<Account> fetchAccount(){
        return accountRepository.findAll();
    }

    public Account depositFunds(DepositForm depositForm) {
        Account account=accountRepository.findAccountByAccountNumber(depositForm.getAccountNumber());
        account.setIsCompleted(false);
        account.setBalance(account.getBalance() + depositForm.getAmount());
        accountRepository.save(account);
        return account;
    }
    public  Account withdrawFunds(DepositForm depositForm){
        Account account=accountRepository.findAccountByAccountNumber(depositForm.getAccountNumber());
        if(account.getBalance()<depositForm.getAmount()){
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST,"Sorry you don't have sufficient funds in you account");
        }else if(account.getBalance()<200.00){
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST,"Sorry Your account is below the minimum amount Please Deposit some funds to withraw");
        }
        account.setIsCompleted(true);

        account.setBalance(account.getBalance() - depositForm.getAmount());
        accountRepository.save(account);
        return account;
    }
    public Account fetchAccountByAccountNo(String accountNo){
        Account account=accountRepository.findAccountByAccountNumber(accountNo);
        return account;
    }



}
