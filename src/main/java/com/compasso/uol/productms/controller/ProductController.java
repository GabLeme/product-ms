package com.compasso.uol.productms.controller;

import com.compasso.uol.productms.dto.CustomErrorDto;
import com.compasso.uol.productms.dto.ProductDto;
import com.compasso.uol.productms.entity.ProductNotFoundException;
import com.compasso.uol.productms.mapper.ProductMapper;
import com.compasso.uol.productms.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
public class ProductController {

    private ProductService productService;
    private ProductMapper mapper;

    public ProductController(ProductService service, ProductMapper mapper) {
        this.productService = service;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity save(@Valid @RequestBody ProductDto product, BindingResult validationResult) {

        if (validationResult.hasErrors()) {
            return ResponseEntity.status(400).body(new CustomErrorDto("400", "Campos requeridos não podem ser nulos"));
        }

        product.setId("");

        return ResponseEntity.status(201)
                .body(this.mapper.toDto(this.productService.insert(this.mapper.toEntity(product))));
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductDto>> findWithCriteria(
            @RequestParam(value = "min_price", required = false) BigDecimal minPrice,
            @RequestParam(value = "max_price", required = false) BigDecimal maxPrice,
            @RequestParam(value = "q", required = false) String query
    ) {
        return ResponseEntity.ok(
                this.productService.findProductByMinPriceAndMaxPrice(minPrice, maxPrice, query)
                        .stream()
                        .map(p -> this.mapper.toDto(p))
                        .collect(Collectors.toList())
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity updateProductById(@PathVariable("id") String id, @RequestBody ProductDto product, BindingResult validationResult) throws ProductNotFoundException {

        if (validationResult.hasErrors()) {
            return ResponseEntity.status(400).body(new CustomErrorDto("400", "Campos requeridos não podem ser nulos"));
        }

        try {
            product.setId(id);
            return ResponseEntity.ok(this.mapper.toDto(
                    this.productService.update(this.mapper.toEntity(product))));
        } catch(ProductNotFoundException ex) {
            return ResponseEntity.status(404).build();
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity findProductById(@PathVariable("id") String id) throws ProductNotFoundException {
        try {
            return ResponseEntity.ok(this.mapper.toDto(this.productService.findById(id)));
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(404).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> findAllProducts() {
        return ResponseEntity.ok(
                this.productService.findAll()
                        .stream().map(p -> this.mapper.toDto(p))
                        .collect(Collectors.toList())
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteProduct(@PathVariable("id") String id) throws ProductNotFoundException {
        try {
            this.productService.delete(id);
            return ResponseEntity.status(200).build();
        } catch (ProductNotFoundException ex) {
            return ResponseEntity.status(404).build();
        }
    }

}
