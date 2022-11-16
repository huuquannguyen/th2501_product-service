package com.hubt.th2501.product_service.repository;

import com.hubt.th2501.product_service.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    Optional<Product> findById(Long id);

    @Query("select new com.hubt.th2501.product_service.controller.response.GetProductInStoreResponse(" +
            " p.id," +
            " p.name," +
            " p.category," +
            " p.subject," +
            " p.price," +
            " p.sold," +
            " p.description," +
            " p.imageUrl," +
            " s.size," +
            " s.color," +
            " s.quantity," +
            " s.inStore) " +
            "from Product p inner join Size s " +
            "on p.id = s.product.id where s.inStore > 0")
    Page<Product> findInStoreProducts(Pageable pageable);

}
