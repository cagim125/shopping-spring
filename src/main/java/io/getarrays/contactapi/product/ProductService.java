package io.getarrays.contactapi.product;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepo productRepo;

    public Page<Product> getAllProducts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productRepo.findAll(pageable);
    }

    public Page<Product> getSearchValue(String name, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productRepo.fullTextSearch(name, pageable);
    }

    public Product getProduct(Long productId) {
        return productRepo.findById(productId).orElse(null);
    }

    public Product addProduct(Product product) {
        return productRepo.save(product);
    }

    public List<Product> randomProduct() {
        return productRepo.findRandomProducts();
    }
    public List<Product> nikeProduct() {
        return productRepo.nikeProducts();
    }

    public Product updateProduct(Long productId, Product productDetail) {
        Product product = productRepo.findById(productId).orElse(null);
        if (product != null) {
            product.setName(productDetail.getName());
            product.setDescription(productDetail.getDescription());
            product.setPrice(productDetail.getPrice());
            product.setStock(productDetail.getStock());
            return productRepo.save(product);
        }
        return null;
    }
}
