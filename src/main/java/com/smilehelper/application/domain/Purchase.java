package com.smilehelper.application.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


import java.math.BigDecimal;
import java.time.LocalDateTime;



@Entity
@Table(name = "purchase_history")
@Getter
@Setter
public class Purchase {

    @Id
    @GeneratedValue(strategy  = GenerationType.IDENTITY)
    private Long Purchase_id; //구매 내역 식별자

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;  // 구매를 한 사용자

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;   // 구매한 아이템

    @Column(nullable = false)
    private BigDecimal purchasePrice;   // 구매한 아이템의 가격

    @Column(nullable = false)
    private LocalDateTime purchaseDate;   // 구매 일시


    /*
     *
     *  //@Getters and @Setter
     *
     *  Lombok이 제공하는 @Getter @Setter를 사용하여 자동으로 생성
     */

    /*
     * Relationships
     * -------
     * - @ManyToOne 관계를 사용하여 User와 Item 엔티티와의 관계를 설정
     * - User: 하나의 Purchase는 한 명의 User와 연결
     * - item: 하나의 Purchase는 한 개의 Item과 연결
     */
    /*
     * Additional Notes
     * ----------------
     * - Purchase 엔티티는 각 사용자가 어떤 아이템을 언제 어떤 가격에 구매했는지를 나타남
     * - user_id와 item_id는 외래 키로, 각각 User와 Item 엔티티의 primary key에 매핑됩
     * - purchaseDate는 구매가 일어난 시간을 저장
     */
}
