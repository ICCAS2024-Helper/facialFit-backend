package com.smilehelper.application.controller;

import com.smilehelper.application.dto.PurchaseDTO;
import com.smilehelper.application.service.PurchaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * PurchaseController 클래스는 Item 구매 관련 HTTP 요청을 처리합니다.
 */
@Tag(name = "Purchase API", description = "Purchase API 목록입니다.")
@RestController
@RequestMapping("api/purchase")
public class PurchaseController {

    private final PurchaseService purchaseService;

    @Autowired
    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }
    /**
     * 모든 구매 내역 조회
     *
     * @return 모든 구매 내역 리스트
     */
    @Operation(summary = "모든 구매 내역 조회", description = "모든 사용자의 구매 내역을 조회합니다.")
    @GetMapping
    public ResponseEntity<List<PurchaseDTO>> getAllPurchases() {
        List<PurchaseDTO> purchases = purchaseService.getAllPurchases();
        return ResponseEntity.ok(purchases);
    }

    /**
     * 특정 사용자의 구매 내역 조회
     *
     * @param userId 사용자 ID
     * @return 특정 사용자의 구매 내역 리스트
     */
    @Operation(summary = "특정 사용자의 구매 내역 조회", description = "특정 사용자의 구매 내역을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없음")
    })
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PurchaseDTO>> getPurchasesByUser(@PathVariable Long userId) {
        List<PurchaseDTO> userPurchases = purchaseService.getPurchasesByUser(userId);
        return ResponseEntity.ok(userPurchases);
    }
    /**
     * 아이템 구매
     *
     * @param userId 사용자 ID
     * @param itemId 아이템 ID
     * @return 생성된 PurchaseDTO
     * @throws RuntimeException 사용자나 아이템을 찾을 수 없거나, 코인 부족 등의 문제가 발생할 경우 예외 발생
     */
    @Operation(summary = "아이템 구매", description = "사용자가 아이템을 구매합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "구매 성공"),
            @ApiResponse(responseCode = "400", description = "구매 실패")
    })
    @PostMapping("/purchase")
    public ResponseEntity<PurchaseDTO> purchaseItem(
            @RequestParam Long userId,
            @RequestParam Long itemId
    ) {
        PurchaseDTO purchaseDTO = purchaseService.purchaseItem(userId, itemId);
        return ResponseEntity.ok(purchaseDTO);
    }
    /**
     * 구매 내역 삭제
     *
     * @param purchaseId 삭제할 구매 내역 ID
     * @return 삭제 성공 여부
     */
    @Operation(summary = "구매 내역 삭제", description = "구매 내역을 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "삭제 성공"),
            @ApiResponse(responseCode = "404", description = "구매 내역을 찾을 수 없음")
    })
    @DeleteMapping("/{purchaseId}")
    public ResponseEntity<Void> deletePurchase(@PathVariable Long purchaseId) {
        purchaseService.deletePurchase(purchaseId);
        return ResponseEntity.ok().build();
    }
}
