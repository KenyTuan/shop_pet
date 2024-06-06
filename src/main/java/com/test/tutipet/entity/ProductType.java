package com.test.tutipet.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.test.tutipet.enums.PetType;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "product_type")
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter @Builder
public class ProductType extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Enumerated(EnumType.STRING)
    private PetType petTypes;

    @OneToMany(mappedBy = "productType")
    @JsonIgnore
    private Set<Product> products;
}
