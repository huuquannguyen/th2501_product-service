package com.hubt.th2501.product_service.repository;

import com.hubt.th2501.product_service.entity.Size;
import com.hubt.th2501.product_service.subscription.model.OrderLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SizeRepository extends JpaRepository<Size, Long> {

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query(value = "update size s set s.quantity = s.quantity - :#{#orderLine.quantity} " +
            "where s.product_id = :#{#orderLine.productId} " +
            "and s.size = :#{#orderLine.size} " +
            "and s.color = :#{#orderLine.color}",
            nativeQuery = true)
    void updateQuantity(@Param("orderLine") OrderLine orderLine);

}
