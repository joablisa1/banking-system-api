package com.bankingsystemapplication.model;

import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "account")
public class Account {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String accountName;
    private String accountNumber;
    private double balance=0.0;
    @ManyToOne(cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
    @JoinColumn(name = "cust_id")
    private Customer customer;
    private Boolean isCompleted=false;
}
