package com.smilehelper.application.dto;


import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemDTO {
    private String itemCode; //재고번호
    private String itemName; // 아이템 이름
    private int itemPrice; // 아이템 가격
    private int quantity; //수량
}
