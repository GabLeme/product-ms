package com.compasso.uol.productms.service;
import com.compasso.uol.productms.entity.Product;
import com.compasso.uol.productms.entity.ProductNotFoundException;
import com.compasso.uol.productms.repository.ProductRepository;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class ProductServiceTest {

    @MockBean
    ProductRepository repository;

    @Autowired
    ProductService service;

    @Test
    @DisplayName("Should save an product")
    public void ShouldSaveAnProduct() {
        BDDMockito.given(repository.save(Mockito.any(Product.class))).willReturn(this.getMockProduct());

        Product response = service.insert(new Product());

        Assertions.assertNotNull(response);
    }

    @Test
    @DisplayName("Should return an product when find by id")
    public void ShouldReturnProductFindById() throws ProductNotFoundException {
        BDDMockito.given(repository.findById(Mockito.anyString())).willReturn(Optional.of(this.getMockProduct()));

        Product response = service.findById("1");
        Assertions.assertNotNull(response);
    }

    @Test
    @DisplayName("Should update an product")
    public void ShouldUpdateProduct() throws ProductNotFoundException {
        BDDMockito.given(repository.save(Mockito.any(Product.class))).willReturn(this.getMockProduct());
        BDDMockito.given(repository.findById(Mockito.anyString())).willReturn(Optional.of(this.getMockProduct()));

        Product productToBeUpdated = service.findById("1");

        Product response = service.update(productToBeUpdated);

        Assertions.assertNotNull(response);
    }

    @Test
    @DisplayName("Should return a list of products")
    public void ShouldReturnAListOfProducts() {
        BDDMockito.given(repository.findAll()).willReturn(this.getMockList());

        List<Product> response = service.findAll();

        Assertions.assertEquals(3, response.size());
        Assertions.assertNotNull(response);
    }


    private Product getMockProduct() {
        return new Product(
                "1",
                "Livro",
                "Clean Code",
                BigDecimal.valueOf(37.45)
        );
    }

    private List<Product> getMockList() {
        List<Product> fakeList = new ArrayList();
        fakeList.add(new Product("2", "Livro2", "Clean Coder1", BigDecimal.valueOf(20.39)));
        fakeList.add(new Product("3", "Livro3", "Clean Coder2", BigDecimal.valueOf(40.39)));
        fakeList.add(new Product("4", "Livro4", "Clean Coder3", BigDecimal.valueOf(50.39)));

        return fakeList;
    }
}
