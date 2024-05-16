package com.test.tutipet.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "product_order")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ProductOrder extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonIgnore
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonIgnore
    private Product product;

}
