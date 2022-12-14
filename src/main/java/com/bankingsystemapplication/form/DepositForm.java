package com.bankingsystemapplication.form;

import com.bankingsystemapplication.model.Account;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@Getter
@Setter
public class DepositForm {
    private Long id;
    private String accountNumber;
    private double amount=0.0;
    public  DepositForm(Account account){
        this.setId(account.getId());
        this.setAccountNumber(account.getAccountNumber());
        this.setAmount(account.getBalance());
    }




}
