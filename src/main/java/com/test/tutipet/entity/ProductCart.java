package com.test.tutipet.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(unique = true, nullable = false)
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;
}
