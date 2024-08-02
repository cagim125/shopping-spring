package io.getarrays.contactapi.product;

import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Array;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ProductRepo productRepo;
    private final S3Service s3Service;

    @GetMapping
    public ResponseEntity<Page<Product>> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size ) {
        Page<Product> products = productService.getAllProducts(page, size);

        return ResponseEntity.ok(products);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable Long productId) {
        Product product = productService.getProduct(productId);
        System.out.println(product);
        if (product != null) {
            return ResponseEntity.ok(product);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{productId}")
    public ResponseEntity<Product> updateProduct(
            @PathVariable Long productId, @RequestBody Product productDetail) {
        System.out.println("ID : " + productId);
        System.out.println("product : " + productDetail);
        Product result = productService.updateProduct(productId, productDetail);
        if( result != null) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/search")
    public ResponseEntity<Page<Product>> productSearch(
            @RequestParam String searchValue,
            @RequestParam int page,
            @RequestParam int size
    ) {

        Page<Product> result = productService.getSearchValue(searchValue, page, size);
        System.out.println(result);
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        Product addedProduct = productService.addProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedProduct);
//        System.out.println(product);
//        return ResponseEntity.status(200).body(product);
    }

    @GetMapping("/sale")
    public ResponseEntity<List<Product>> saleProduct() {
        List<Product> randomProducts = productService.randomProduct();

        return ResponseEntity.ok(randomProducts);
    }
    @GetMapping("/nike")
    public ResponseEntity<List<Product>> nikeProduct() {
        List<Product> nikeProducts = productService.nikeProduct();

        return ResponseEntity.ok(nikeProducts);
     }

    @GetMapping("/presigned-url")
    @ResponseBody
    String getURL(@RequestParam String filename){
        System.out.println(filename);
        var result = s3Service.createPresignedUrl("test/" + filename);
        return result;
    }

}



