package com.compasso.uol.productms.repository;

import com.compasso.uol.productms.entity.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class ProductRepositoryTest {

    @Autowired
    ProductRepository productRepository;

    @AfterEach
    public void tearDown() {
        productRepository.deleteAll();
    }

    @Test
    @DisplayName("Should save an Product into Database")
    public void ShouldSaveProductRepository() {
        Product product = new Product("", "Celular Xioami", "Celular 32gb ram", BigDecimal.valueOf(780.94));

        Product response = productRepository.save(product);

        Assertions.assertNotNull(response);
    }

    @Test
    @DisplayName("Should find product by id in Database")
    public void ShouldFindProductByIdRepository() {
        Product productSaved = productRepository.save(new Product("", "Livro 1", "Clean Coder", BigDecimal.valueOf(30.49)));
        Optional<Product> productOptional = productRepository.findById(productSaved.getId());

        Assertions.assertTrue(productOptional.isPresent());
    }

    @Test
    @DisplayName("Should find only one Product with value >= 40")
    public void ShouldFindOnlyOneProductRepository() {
        Product productSavedOne = productRepository.save(new Product("", "Livro 1", "Clean Coder", BigDecimal.valueOf(30.49)));
        Product productSavedTwo = productRepository.save(new Product("", "Livro Spock Framework", "TDD", BigDecimal.valueOf(50.00)));

        List<Product> response = productRepository.findProductByMinPriceAndMaxPriceAndQuery(BigDecimal.valueOf(40), null, null);

        Assertions.assertEquals(1, response.size());
        Assertions.assertNotNull(response);
    }

    @Test
    @DisplayName("Should find only one Product with value <= 40")
    public void ShouldFindOnlyOneProductRepositoryTwo() {
        Product productSavedOne = productRepository.save(new Product("", "Livro 1", "Clean Coder", BigDecimal.valueOf(30.49)));
        Product productSavedTwo = productRepository.save(new Product("", "Livro Spock Framework", "TDD", BigDecimal.valueOf(50.00)));

        List<Product> response = productRepository.findProductByMinPriceAndMaxPriceAndQuery(null, BigDecimal.valueOf(40), null);

        Assertions.assertEquals(1, response.size());
        Assertions.assertNotNull(response);
    }

    @Test
    @DisplayName("Should return two products with query name,description like livro")
    public void ShouldReturnTwoProductsRepository() {
        Product productSavedOne = productRepository.save(new Product("", "livro 1", "Clean Coder", BigDecimal.valueOf(30.49)));
        Product productSavedTwo = productRepository.save(new Product("", "Spock Framework", "livro xxx", BigDecimal.valueOf(50.00)));

        List<Product> response = productRepository.findProductByMinPriceAndMaxPriceAndQuery(null, null, "livro");

        Assertions.assertEquals(2, response.size());
        Assertions.assertNotNull(response);
    }

}