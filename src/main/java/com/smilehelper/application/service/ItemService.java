package com.smilehelper.application.service;

import com.smilehelper.application.domain.Item;
import com.smilehelper.application.domain.Purchase;
import com.smilehelper.application.domain.User;
import com.smilehelper.application.dto.ItemDTO;
import com.smilehelper.application.repository.ItemRepository;
import com.smilehelper.application.repository.PurchaseRepository;
import com.smilehelper.application.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * ItemService 클래스는 아이템 관련 비즈니스 로직을 처리하는 서비스 클래스입니다.
 */
@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final PurchaseRepository purchaseRepository;

    @Autowired
    public ItemService(
            ItemRepository itemRepository,
            UserRepository userRepository,
            PurchaseRepository purchaseRepository
    ) {
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
        this.purchaseRepository = purchaseRepository;
    }

    /**
     * 모든 아이템 조회
     * @return 모든 아이템 목록
     */
    public List<ItemDTO> getAllItems() {
        List<Item> items = itemRepository.findAll();
        return items.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * 특정 아이템 조회
     * @param itemId 아이템 식별자
     * @return 특정 아이템 DTO
     * @throws RuntimeException 아이템을 찾을 수 없는 경우 예외 발생
     */
    public ItemDTO getItemById(Long itemId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("아이템을 찾을 수 없습니다."));
        return convertToDTO(item);
    }

    /**
     * 아이템 등록
     * @param itemDTO 등록할 아이템 정보 DTO
     * @return 등록된 아이템 DTO
     */
    @Transactional
    public ItemDTO createItem(ItemDTO itemDTO) {
        Item newItem = new Item();
        BeanUtils.copyProperties(itemDTO, newItem);
        if (newItem.getQuantity() == 0) {
            newItem.setSoldOut(true);
        }
        newItem = itemRepository.save(newItem);
        return convertToDTO(newItem);
    }

    // 사용자별 아이템 수량을 가져오는 메서드 수정
    public List<ItemDTO> getItemsForUser(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다. ID: " + id));

        List<Item> items = itemRepository.findAll();
        List<Purchase> userPurchases = purchaseRepository.findByUser(user);

        return items.stream().map(item -> {
            long purchasedQuantity = userPurchases.stream()
                    .filter(purchase -> purchase.getItem().getItemId().equals(item.getItemId()))
                    .count(); // 구매한 아이템 수량을 세기
            int remainingQuantity = item.getQuantity() - (int) purchasedQuantity;
            return new ItemDTO(item.getItemCode(), item.getItemName(), item.getItemPrice(), remainingQuantity);
        }).collect(Collectors.toList());
    }

    /**
     * 아이템 업데이트
     * @param itemId 업데이트할 아이템의 식별자
     * @param updatedItemDTO 업데이트할 아이템 정보 DTO
     * @return 업데이트 된 아이템 DTO
     * @throws RuntimeException 아이템을 찾을 수 없거나 업데이트 중 문제가 발생한 경우 예외 발생
     */
    @Transactional
    public ItemDTO updateItem(Long itemId, ItemDTO updatedItemDTO) {
        Item itemToUpdate = itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("아이템을 찾을 수 없습니다. ID: " + itemId));
        itemToUpdate.setItemName(updatedItemDTO.getItemName());
        itemToUpdate.setItemPrice(updatedItemDTO.getItemPrice());
        itemToUpdate.setQuantity(updatedItemDTO.getQuantity());
        if (itemToUpdate.getQuantity() == 0) {
            itemToUpdate.setSoldOut(true);
        } else {
            itemToUpdate.setSoldOut(false);
        }
        Item updatedItem = itemRepository.save(itemToUpdate);
        return convertToDTO(updatedItem);
    }

    /**
     * 아이템 삭제
     * @param itemId 삭제할 아이템 식별자
     * @throws RuntimeException 아이템을 찾지 못한 경우 발생
     */
    @Transactional
    public void deleteItem(Long itemId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("아이템을 찾을 수 없습니다 ID: " + itemId));
        itemRepository.delete(item);
    }

    /**
     * Item 엔티티를 Item DTO 변환
     * @param item 변환활 Item 엔티티
     * @return 변환된 ItemDTO
     */
    private ItemDTO convertToDTO(Item item) {
        ItemDTO itemDTO = new ItemDTO();
        BeanUtils.copyProperties(item, itemDTO);
        return itemDTO;
    }
}