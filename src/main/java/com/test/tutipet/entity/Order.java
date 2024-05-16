package com.test.tutipet.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.test.tutipet.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter @Builder
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String note;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private String phone;

    private String address;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "order")
    @JsonIgnore
    private Set<ProductOrder> productOrders;
}
