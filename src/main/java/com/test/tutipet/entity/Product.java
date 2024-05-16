package com.test.tutipet.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.test.tutipet.enums.EnableStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table
@Getter @Setter @Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int price;

    private String description;

    private String info;

    private String image;

    @Enumerated(EnumType.STRING)
    private EnableStatus enableStatus;

    @ManyToOne
    @JoinColumn(name = "type_id")
    @JsonIgnore
    private ProductType productType;

    @OneToMany(mappedBy = "product")
    @JsonIgnore
    private Set<ProductOrder> productOrders;

}
