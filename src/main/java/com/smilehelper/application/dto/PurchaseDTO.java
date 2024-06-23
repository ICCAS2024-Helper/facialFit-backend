package com.smilehelper.application.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseDTO {
    private Long id; // 구매 내역 식별자
    private Long userId; // 구매한 사용자 식별자
    private Long itemId; // 구매한 아이템 식별자
    private BigDecimal purchasePrice; // 구매한 아이템 가격
    private LocalDateTime purchaseDate; // 구매 일시

}
