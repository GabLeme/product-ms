package com.compasso.uol.productms.repository;

import com.compasso.uol.productms.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, String>, ProductSearchRepository {
}
