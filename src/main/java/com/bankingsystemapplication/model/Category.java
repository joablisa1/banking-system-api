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
@Table(name = "category")
public class Category {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String categoryName;
    private String description;
    private Date dateCreated=new Date();
    private  boolean isCompleted=false;


}
