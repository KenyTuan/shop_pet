package com.test.tutipet.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.test.tutipet.enums.EnableStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import static com.test.tutipet.entity.BaseEntity.NOT_DELETED;

@Entity
@Table(name = "products")
@Getter @Setter @Builder
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = NOT_DELETED)
public class Product extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private double price;

    private String brand;

    private String origin;

    private String description;

    private String image;

    @Enumerated(EnumType.STRING)
    private EnableStatus status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "type_id")
    private ProductType productType;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<ProductOrder> productOrders = new HashSet<>();

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<ProductCart> productCarts = new HashSet<>();

    @ManyToMany(mappedBy = "products")
    @JsonIgnore
    private Set<Promotion> promotions  = new HashSet<>();

}
