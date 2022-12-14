package com.bankingsystemapplication.form;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Setter
@Getter
@NoArgsConstructor
public class LogoinRequest {
    private String email;
    private String password;
}
