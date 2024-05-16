package com.test.tutipet.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table
@Getter @Setter @Builder
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true, nullable = false)
    private String email;

    private String password;

    private String fullName;

    private boolean gender;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private Set<Order> orders;


}
