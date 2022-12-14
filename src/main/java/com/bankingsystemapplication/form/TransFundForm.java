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
public class TransFundForm {
    private Long id;
    private String accountName;
    private double amount=0.0;

    public TransFundForm(Account account){
        this.setId(account.getId());
        this.setAccountName(account.getAccountName());
        this.setAmount(account.getBalance());
    }

}
