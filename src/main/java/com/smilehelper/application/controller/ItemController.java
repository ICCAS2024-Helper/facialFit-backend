package com.smilehelper.application.controller;

import com.smilehelper.application.dto.ItemDTO;
import com.smilehelper.application.service.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ItemController 클래스는 Item 관련 HTTP 요청을 처리합니다.
 */
@Tag(name = "Item API", description = "Item API 목록입니다.")
@RestController
@RequestMapping("/api/items")
public class ItemController {

    private final ItemService itemService;

    /**
     * ItemController 생성자
     * @param itemService 아이템 서비스
     */
    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    /**
     * 전체 아이템 조회
     * @return 전체 아이템 목록
     */
    @Operation(summary = "전체 아이템 조회", description = "모든 아이템을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공")
    })
    @GetMapping
    public ResponseEntity<List<ItemDTO>> getAllItems(){
        List<ItemDTO> items = itemService.getAllItems();
        return ResponseEntity.ok(items);
    }

    /**
     * 특정 아이템 조회
     * @param itemId 아이템 식별자
     * @return 특정 아이템 정보
     */
    @Operation(summary = "아이템 상세 조회", description = "특정 아이템을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "404", description = "아이템을 찾을 수 없습니다.")
    })
    @GetMapping("/{itemId}")
    public ResponseEntity<ItemDTO> getItemById(@PathVariable Long itemId) {
        ItemDTO item = itemService.getItemById(itemId);
        return ResponseEntity.ok(item);
    }

    /**
     * 아이템 등록
     * @param itemDTO 등록할 아이템 정보
     * @return 등록된 아이템 정보
     */
    @Operation(summary = "아이템 등록", description = "새로운 아이템을 등록합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "등록 성공")
    })
    @PostMapping
    public ResponseEntity<ItemDTO> createItem(@RequestBody @Valid ItemDTO itemDTO) {
        ItemDTO newItem = itemService.createItem(itemDTO);
        return ResponseEntity.ok(newItem);
    }

    // 사용자별 아이템 수량을 가져오는 엔드포인트 추가
    @Operation(summary = "사용자별 아이템 조회", description = "특정 사용자의 아이템 수량을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없습니다.")
    })
    @GetMapping("/user/{id}")
    public ResponseEntity<List<ItemDTO>> getItemsForUser(@PathVariable String id) {
        List<ItemDTO> items = itemService.getItemsForUser(id);
        return ResponseEntity.ok(items);
    }

    /**
     * 아이템 업데이트
     * @param itemId 아이템 식별자
     * @param itemDTO 업데이트할 아이템 정보
     * @return 업데이트된 아이템 정보
     */
    @Operation(summary = "아이템 업데이트", description = "기존 아이템을 업데이트합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "업데이트 성공"),
            @ApiResponse(responseCode = "404", description = "아이템을 찾을 수 없습니다.")
    })
    @PutMapping("/{itemId}")
    public ResponseEntity<ItemDTO> updateItem(@PathVariable Long itemId, @RequestBody @Valid ItemDTO itemDTO) {
        ItemDTO updatedItem = itemService.updateItem(itemId, itemDTO);
        return ResponseEntity.ok(updatedItem);
    }

    /**
     * 아이템 삭제
     * @param itemId 아이템 식별자
     * @return HTTP 응답 상태
     */
    @Operation(summary = "아이템 삭제", description = "특정 아이템을 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "삭제 성공"),
            @ApiResponse(responseCode = "404", description = "아이템을 찾을 수 없습니다.")
    })
    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long itemId) {
        itemService.deleteItem(itemId);
        return ResponseEntity.noContent().build();
    }
}