package com.test.tutipet.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.test.tutipet.enums.DiscountType;
import com.test.tutipet.enums.EnableStatus;
import com.test.tutipet.enums.PromotionTarget;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Set;

@Entity
@Table
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Promotion extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String code;

    @Enumerated(EnumType.STRING)
    private PromotionTarget target;

    private ZonedDateTime fromTime;

    private ZonedDateTime toTime;

    @Enumerated(EnumType.STRING)
    private DiscountType discountType;

    private BigDecimal value;

    @Enumerated(EnumType.STRING)
    private EnableStatus enableStatus;

    @ManyToMany
    @JoinTable(
            name = "order_promotion",
            joinColumns = @JoinColumn(name = "promotion_id"),
            inverseJoinColumns = @JoinColumn(name = "order_id")
    )
    @JsonIgnore
    private Set<Order> orders;

    @ManyToMany
    @JoinTable(
            name = "product_promotion",
            joinColumns = @JoinColumn(name = "promotion_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    @JsonIgnore
    private Set<Product> products;

}
