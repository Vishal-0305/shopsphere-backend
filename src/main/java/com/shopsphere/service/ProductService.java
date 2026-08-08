package com.shopsphere.service;

import com.shopsphere.dto.ProductRequest;
import com.shopsphere.entity.Product;

public interface ProductService {

    Product addProduct(ProductRequest request);

}