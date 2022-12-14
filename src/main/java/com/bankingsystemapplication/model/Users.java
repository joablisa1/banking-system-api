package com.bankingsystemapplication.model;

import lombok.*;

import javax.persistence.*;
import java.util.Collection;

@Data
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "users")
public class Users {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstname;
    private String lastname;
    private String username;
    private String password;
    private boolean isActivated=false;
    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",joinColumns = {@JoinColumn(name = "USER_ID",referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "ROLE_ID",referencedColumnName = "ID")})
    private Collection<Roles> roles;



}
