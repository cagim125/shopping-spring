package io.getarrays.contactapi.commnet;


import io.getarrays.contactapi.product.Product;
import io.getarrays.contactapi.product.ProductRepo;
import io.getarrays.contactapi.user.User;
import io.getarrays.contactapi.user.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/comment")
public class CommnetController {

    private final CommnetRepository commnetRepository;
    private final UserRepo userRepo;
    private final ProductRepo productRepo;

    @GetMapping
    public ResponseEntity<List<Comment>> getReview(@RequestParam Long productId) {
        List<Comment> result = commnetRepository.findByProductId(productId);

        System.out.println(result.toString());

        return ResponseEntity.ok().body(result);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addReview(@RequestBody RequestDto data) {
        var existingReview = commnetRepository.findByUserIdAndProductId(data.getUserId(), data.getProductId());

        if (existingReview.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("alreadyReview");
        } else {
            Optional<User> user = userRepo.findById(data.getUserId());
            Optional<Product> product = productRepo.findById(data.getProductId());

            if (user.isPresent() && product.isPresent()) {
                Comment comment = Comment.builder()
                        .content(data.getReview())
                        .user(user.get())
                        .product(product.get())
                        .build();

                return ResponseEntity.status(HttpStatus.CREATED).body(comment);
            }
        }

        return ResponseEntity.status(HttpStatus.CREATED).body("notSavedReview");
    }
}
