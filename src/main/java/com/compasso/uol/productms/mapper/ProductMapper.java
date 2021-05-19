package com.compasso.uol.productms.mapper;

import com.compasso.uol.productms.dto.ProductDto;
import com.compasso.uol.productms.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public Product toEntity(ProductDto dto) {
        return new Product(
                dto.getId(),
                dto.getName().toLowerCase(),
                dto.getDescription().toLowerCase(),
                dto.getPrice()
        );
    }

    public ProductDto toDto(Product ent) {
        return new ProductDto(
                ent.getId(),
                ent.getName().toLowerCase(),
                ent.getDescription().toLowerCase(),
                ent.getPrice()
        );
    }

}
