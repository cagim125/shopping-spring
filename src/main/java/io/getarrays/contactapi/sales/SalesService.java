package io.getarrays.contactapi.sales;

import io.getarrays.contactapi.user.CustomUser;
import io.getarrays.contactapi.user.User;
import io.getarrays.contactapi.user.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class SalesService {
    private final SalesRepository salesRepository;
    private final UserRepo userRepo;

    public ResponseEntity<?> addSales(SalesRequest request) {

        // 사용자가 존재하는지 확인
        Optional<User> existingUser = userRepo.findById(request.getUserId());

        if (existingUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("존재하지 않은 유저 입니다.");
        }
        // 주문 내역 존재 여부 확인
        Optional<Sales> existingSales = salesRepository.findByUserIdAndItemName(request.getUserId(), request.getItemName());

        Sales sales = Sales.builder()
                .itemName(request.getItemName())
                .price(request.getPrice())
                .count(request.getCount())
                .user(existingUser.get())
                .build();

        if (existingSales.isPresent()) {
            // 이미 아이템이 존재하는 경우 수량만 증가
            Sales existingItem = existingSales.get();
            existingItem.setCount(existingItem.getCount() + sales.getCount());
            salesRepository.save(existingItem);

            return ResponseEntity.status(HttpStatus.OK).body("주문 내역에 이미 존재하여 수량이 증가했습니다.");
        } else {
            // 존재하지 않으면 새로운 주문 추가
            salesRepository.save(sales);

            return ResponseEntity.status(HttpStatus.CREATED).body("새로운 아이템이 추가되었습니다.");
        }

    }

    public ResponseEntity<?> getOrder(CustomUser user) {
        var result = salesRepository.findSalesByUserNameNative(user.getDisplayName());

        return ResponseEntity.ok().body(result);


    }
}
