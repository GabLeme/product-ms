package com.compasso.uol.productms.service;

import com.compasso.uol.productms.entity.Product;
import com.compasso.uol.productms.entity.ProductNotFoundException;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {
    List<Product> findProductByMinPriceAndMaxPrice(BigDecimal minPrice, BigDecimal maxPrice, String query);
    Product insert(Product p);
    Product update(Product p) throws ProductNotFoundException;
    Product findById(String id) throws ProductNotFoundException;
    List<Product> findAll();
    void delete(String id) throws ProductNotFoundException;
}
