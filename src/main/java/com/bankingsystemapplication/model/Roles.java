package com.bankingsystemapplication.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "roles")
public class Roles {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private boolean isActivated=false;
    @Temporal(TemporalType.DATE)
    Date  createdOn=new Date();
}
