package io.getarrays.contactapi.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {
    List<Product> findAllByName(String name);


    @Query(value = "select * from shop.product where id = ?1", nativeQuery = true)
    Optional<Product> rawQuery1(int num);

    @Query(value = "SELECT * FROM shop.product WHERE MATCH(name) AGAINST(:name)",
            countQuery = "SELECT count(*) FROM shop.product WHERE MATCH(name) AGAINST(:name)",
            nativeQuery = true)
    Page<Product> fullTextSearch(@Param("name") String name, Pageable pageable);

    @Query(value = "SELECT * FROM shop.product ORDER BY RAND() LIMIT 12", nativeQuery = true)
    List<Product> findRandomProducts();

    @Query(value = "select * from shop.product p where p.description = '나이키' limit 12;", nativeQuery = true)
    List<Product> nikeProducts();





}
