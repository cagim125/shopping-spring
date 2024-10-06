package io.getarrays.contactapi.sales;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/sales")
@RequiredArgsConstructor
public class SalesController {
    private final SalesRepository salesRepository;

    @PostMapping("/add")
    public ResponseEntity<?> addSales(@RequestBody SalesRequest request) {
        System.out.println(request.toString());

        Sales sales = Sales.builder()
                .itemName(request.getItemName())
                .price(request.getPrice())
                .count(request.getCount())
                .userId(request.getUserId())
                .build();

        // 주문 내역 존재 여부 확인
        Optional<Sales> existingSales = salesRepository.findByUserIdAndItemName(sales.getUserId(), sales.getItemName());


        if (existingSales.isPresent()) {
            // 이미 존재하는 경우 count만 업데이트
            Sales existingItem = existingSales.get();
            existingItem.setCount(existingItem.getCount() + sales.getCount());
            salesRepository.save(existingItem);  // 업데이트된 아이템 저장

            return ResponseEntity.status(HttpStatus.OK).body("주문 내역에 이미 존재하여 수량이 증가했습니다.");
        } else {
            // 존재하지 않으면 새로운 주문 추가
            salesRepository.save(sales);

            return ResponseEntity.status(HttpStatus.CREATED).body("새로운 아이템이 추가되었습니다.");
        }
    }
}
