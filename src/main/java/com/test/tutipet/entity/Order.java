package com.test.tutipet.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.test.tutipet.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.Set;

@Entity
@Table(name = "`order`")
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter @Builder
public class Order extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    private BigDecimal total;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private String phone;

    private String address;

    private ZonedDateTime orderDate;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "order")
    @JsonIgnore
    private Set<ProductOrder> productOrders;
}
