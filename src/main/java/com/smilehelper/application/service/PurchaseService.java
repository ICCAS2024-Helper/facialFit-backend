package com.smilehelper.application.service;

import com.smilehelper.application.domain.Item;
import com.smilehelper.application.domain.Purchase;
import com.smilehelper.application.domain.User;
import com.smilehelper.application.dto.PurchaseDTO;
import com.smilehelper.application.repository.ItemRepository;
import com.smilehelper.application.repository.PurchaseRepository;
import com.smilehelper.application.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * PurchaseService 클래스는 사용자 구매 관련 비즈니스 로직을 처리하는 서비스 클래스입니다.
 */
@Service
public class PurchaseService {
    private final PurchaseRepository purchaseRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    @Autowired
    public PurchaseService(PurchaseRepository purchaseRepository, ItemRepository itemRepository, UserRepository userRepository) {
        this.purchaseRepository = purchaseRepository;
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
    }


    /**
     * 모든 구매 내역 조회
     *
     * @return 모든 구매 내역의 DTO 리스트
     */
    @Transactional(readOnly = true)
    public List<PurchaseDTO> getAllPurchases() {
        List<Purchase> purchases = purchaseRepository.findAll();
        return purchases.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * 특정 사용자의 구매 내역 조회
     *
     * @param userId 사용자 ID
     * @return 특정 사용자의 구매 내역 리스트
     */
    public List<PurchaseDTO> getPurchasesByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다. ID: " + userId));

        List<Purchase> userPurchases = purchaseRepository.findByUser(user);
        return userPurchases.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * 아이템 구매 처리
     *
     * @param id 사용자 ID
     * @param itemId 아이템 ID
     * @return 생성된 PurchaseDTO
     * @throws RuntimeException 사용자나 아이템을 찾을 수 없거나, 코인 부족 등의 문제가 발생할 경우 예외 발생
     */
    @Transactional
    public PurchaseDTO purchaseItem(String id, Long itemId) {
        // 사용자 조회
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다. ID: " + id));

        // 아이템 조회
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("아이템을 찾을 수 없습니다. ID: " + itemId));

        // 아이템 가격 조회
        int purchasePrice = item.getItemPrice();

        // 사용자의 코인 잔액 확인 및 차감
        if (user.getCoin() < purchasePrice) {
            throw new RuntimeException("코인 잔액이 부족합니다.");
        }
        user.setCoin(user.getCoin() - purchasePrice);
        userRepository.save(user);

        // 아이템 수량 감소 및 품절 상태 업데이트
        item.setQuantity(item.getQuantity() - 1);
        if (item.getQuantity() == 0) {
            item.setSoldOut(true);
        }
        itemRepository.save(item);

        // 구매 내역 생성 및 저장
        Purchase purchase = new Purchase();
        purchase.setUser(user);
        purchase.setItem(item);
        purchase.setPurchasePrice(purchasePrice);
        purchase.setPurchaseDate(LocalDateTime.now());

        Purchase savedPurchase = purchaseRepository.save(purchase);

        return convertToDTO(savedPurchase);
    }

    /**
     * 구매 내역 삭제
     *
     * @param purchaseId 삭제할 구매 내역 ID
     * @throws RuntimeException 구매 내역을 찾지 못한 경우 예외 발생
     */
    @Transactional
    public void deletePurchase(Long purchaseId) {
        Purchase purchase = purchaseRepository.findById(purchaseId)
                .orElseThrow(() -> new RuntimeException("구매 내역을 찾을 수 없습니다. ID: " + purchaseId));
        purchaseRepository.delete(purchase);
    }

    /**
     * Purchase 엔티티를 PurchaseDTO로 변환
     *
     * @param purchase 변환할 Purchase 엔티티
     * @return 변환된 PurchaseDTO
     */
    private PurchaseDTO convertToDTO(Purchase purchase) {
        PurchaseDTO purchaseDTO = new PurchaseDTO();
        purchaseDTO.setId(purchase.getPurchaseId());
        purchaseDTO.setUser_id(purchase.getUser().getId());
        purchaseDTO.setItemId(purchase.getItem().getItemId());
        purchaseDTO.setPurchasePrice(purchase.getPurchasePrice());
        purchaseDTO.setPurchaseDate(purchase.getPurchaseDate());
        return purchaseDTO;
    }
}

