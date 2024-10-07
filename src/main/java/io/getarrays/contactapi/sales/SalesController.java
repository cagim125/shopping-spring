package io.getarrays.contactapi.sales;


import io.getarrays.contactapi.user.CustomUser;
import io.getarrays.contactapi.user.User;
import io.getarrays.contactapi.user.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/sales")
@RequiredArgsConstructor
public class SalesController {
    private final SalesService salesService;

    @PostMapping("/add")
    public ResponseEntity<?> addSales(@RequestBody SalesRequest request) throws Exception {
        System.out.println(request.toString());

        try {
            return salesService.addSales(request);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류 발생");
        }

    }

    @GetMapping("/order")
    public ResponseEntity<?> getOrder(Authentication auth) {

        if (auth == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인해주세요.");
        } else {
            CustomUser user = (CustomUser) auth.getPrincipal();
            return salesService.getOrder(user);
        }
    }
}
