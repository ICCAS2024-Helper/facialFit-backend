package com.smilehelper.application.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemDTO {
    private Long itemId; // 아이템 식별자

    @NotEmpty
    @Size(min = 2, max = 100)
    private String itemName; // 아이템 이름
    @NotNull
    private BigDecimal itemPrice; // 아이템 가격
    private boolean soldOut; //품절 여부
    private LocalDateTime createdAt; // 생성 일시
    private int quantity; //수량
}
