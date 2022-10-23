package com.hubt.th2501.product_service.repository;

import com.hubt.th2501.product_service.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findById(Long id);

    @Query("from Product p inner join Size s on p.id = s.id where s.inStore > 0")
    Page<Product> findInStoreProducts(Pageable pageable);

}
