package com.shopsphere.repository;

import com.shopsphere.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByNameContainingIgnoreCase(String keyword);
    List<Product> findByPriceBetween(BigDecimal minPrice,
                                     BigDecimal maxPrice);
    List<Product> findByCategoryNameAndPriceBetween(
            String categoryName,
            BigDecimal minPrice,
            BigDecimal maxPrice);

    @Query("""
       SELECT p
       FROM Product p
       WHERE p.price > :price
       """)
    List<Product> findProductsCostlierThan(@Param("price") BigDecimal price);

}