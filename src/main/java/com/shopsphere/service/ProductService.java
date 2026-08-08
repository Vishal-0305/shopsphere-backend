package com.shopsphere.service;

import com.shopsphere.dto.ProductRequest;
import com.shopsphere.entity.Product;

import java.util.List;

public interface ProductService {

    Product addProduct(ProductRequest request);

    List<Product> getAllProducts();

}