package com.compasso.uol.productms.repository;

import com.compasso.uol.productms.entity.Product;

import java.math.BigDecimal;
import java.util.List;

public interface ProductSearchRepository {
    List<Product> findProductByMinPriceAndMaxPriceAndQuery(BigDecimal minPrice, BigDecimal maxPrice, String query);
}
