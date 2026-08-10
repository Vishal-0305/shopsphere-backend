package com.shopsphere.service;

import com.shopsphere.dto.ProductRequest;
import com.shopsphere.dto.ProductResponse;
import com.shopsphere.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public interface ProductService {

    ProductResponse addProduct(ProductRequest request);

    Page<ProductResponse> getAllProducts(int page, int size, String sortBy, String direction);

    ProductResponse getProductById(Long id);

    ProductResponse updateProduct(Long id, ProductRequest request);

    void deleteProduct(Long id);

    List<ProductResponse> searchProducts(String keyword);
    List<ProductResponse> getProductsByPriceRange(
            BigDecimal minPrice,
            BigDecimal maxPrice);

    List<ProductResponse> getProductsByCategoryAndPriceRange(
            String categoryName,
            BigDecimal minPrice,
            BigDecimal maxPrice);

    List<ProductResponse> getProductsCostlierThan(BigDecimal price);

    ProductResponse uploadProductImage(Long productId, MultipartFile file)
            throws IOException;


}