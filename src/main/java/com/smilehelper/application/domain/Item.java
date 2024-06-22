package com.smilehelper.application.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Entity
@Table(name="Items")
public class Item {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemId; // 아이템 식별자



    @Column(nullable = false, unique = true)
    private String itemCode; // 재고 번호

    @Setter
    @Column(nullable = false)
    @NotEmpty
    @Size(min = 2, max = 100)
    private String itemName; //아이템 이름

    @Setter
    @Column(nullable = false)
    private BigDecimal itemPrice; //아이템 가격

    @Setter
    @Column(nullable = false)
    private boolean soldOut; // 품절 여부

    @Column(nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt; // 생성 일시

    @Setter
    @Column(nullable = false, columnDefinition = "int default 1")
    private int quantity; // 수량
}
