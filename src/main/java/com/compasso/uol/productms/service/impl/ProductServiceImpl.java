package com.compasso.uol.productms.service.impl;

import com.compasso.uol.productms.entity.Product;
import com.compasso.uol.productms.entity.ProductNotFoundException;
import com.compasso.uol.productms.repository.ProductRepository;
import com.compasso.uol.productms.service.ProductService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository repo) {
        this.productRepository = repo;
    }

    @Override
    public List<Product> findProductByMinPriceAndMaxPrice(BigDecimal minPrice, BigDecimal maxPrice, String query) {
        return this.productRepository.findProductByMinPriceAndMaxPriceAndQuery(minPrice, maxPrice, query)  ;
    }

    @Override
    public Product insert(Product p) {
        return this.productRepository.save(p);
    }

    @Override
    public Product update(Product p) throws ProductNotFoundException {
        Optional<Product> optionalProduct = this.productRepository.findById(p.getId());
        if(!optionalProduct.isPresent()) throw new ProductNotFoundException("Product not found");

        return this.productRepository.save(p);
    }

    @Override
    public Product findById(String id) throws ProductNotFoundException {
        Optional<Product> optionalProduct = this.productRepository.findById(id);
        if(optionalProduct.isPresent()) return optionalProduct.get();
        else throw new ProductNotFoundException("Product not found!");
    }

    @Override
    public List<Product> findAll() {
        return this.productRepository.findAll();
    }

    @Override
    public void delete(String id) throws ProductNotFoundException {
        Optional<Product> optionalProduct = this.productRepository.findById(id);

        if(optionalProduct.isPresent()) this.productRepository.deleteById(id);
        else throw new ProductNotFoundException("Product not found!");
    }
}
