package com.smilehelper.application.domain;

import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Items")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemId; // 아이템 식별자

    @Column(nullable = false, unique = true)
    private String itemCode; // 재고 번호

    @Column(nullable = false)
    @Size(min = 2, max = 20)
    private String itemName; // 아이템 이름

    @Column(nullable = false)
    private int itemPrice; // 아이템 가격

    @Column(nullable = false)
    private boolean soldOut; // 품절 여부

    @Column(nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt; // 생성 일시

    @Column(nullable = false, columnDefinition = "int default 1")
    private int quantity; // 수량

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}