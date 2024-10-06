package io.getarrays.contactapi.sales;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SalesRepository extends JpaRepository<Sales, Long> {

    @Modifying
    @Query(value = "INSERT INTO OrderItem (userid, itemName, count, price) " +
            "VALUES (:userid, :itemName, :count, :price) " +
            "ON DUPLICATE KEY UPDATE count = count + :count",
    nativeQuery = true)
    void upsertSalesItem(@Param("userid") Long userid,
                         @Param("itemName") String itemName,
                         @Param("count") int count,
                         @Param("price") int price);

    @Query("SELECT s FROM Sales s WHERE s.userId = :userId AND s.itemName = :itemName")
    Optional<Sales> findByUserIdAndItemName(@Param("userId") Long userId, @Param("itemName") String itemName);
}
