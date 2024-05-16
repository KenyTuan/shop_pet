package com.test.tutipet.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.test.tutipet.enums.PetType;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter @Builder
public class ProductType extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Enumerated(EnumType.STRING)
    private PetType petTypes;

    @OneToMany(mappedBy = "type")
    @JsonIgnore
    private Set<Product> products;
}
