package com.compasso.uol.productms.controller;

import com.compasso.uol.productms.dto.ProductDto;
import com.compasso.uol.productms.entity.Product;
import com.compasso.uol.productms.service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ProductControllerTest {

    @MockBean
    ProductService productService;

    @Autowired
    MockMvc mvc;

    private static final String ID = "1";
    private static final String NAME = "Livro SpockFramework";
    private static final String DESCRIPTION = "Livro sobre TDD com SpockFramework";
    private static final BigDecimal PRICE = BigDecimal.valueOf(40.75);
    private static final String URL = "/products";

    @Test
    @DisplayName("Should save an product by controller")
    public void ShouldSaveAnProductController() throws Exception {
        BDDMockito.given(productService.insert(Mockito.any(Product.class))).willReturn(this.getMockProduct());

        mvc.perform(MockMvcRequestBuilders.post(URL).content(getJsonPayload(ID, NAME, DESCRIPTION, PRICE))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(ID));

    }

    private Product getMockProduct() {
        return new Product(
                ID,
                NAME,
                DESCRIPTION,
                PRICE
        );
    }

    public String getJsonPayload(String id, String name, String description, BigDecimal price) throws JsonProcessingException {
        ProductDto dto = new ProductDto(
                id,
                name,
                description,
                price
        );

        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(dto);
    }
}
