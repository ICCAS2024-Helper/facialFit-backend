package com.smilehelper.application.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "purchase_history")
public class Purchase {

    @Id
    @GeneratedValue(strategy  = GenerationType.IDENTITY)
    private Long purchaseId; //구매 내역 식별자

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;  // 구매를 한 사용자

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;   // 구매한 아이템

    @Column(nullable = false)
    private int purchasePrice;   // 구매한 아이템의 가격

    @Column(nullable = false)
    private LocalDateTime purchaseDate;   // 구매 일시
}
