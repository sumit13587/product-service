package org.sumits.product.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.sumits.product.model.Product;
import org.sumits.product.service.ProductService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    private ProductController classUnderTest;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService mockProductService;

    @BeforeEach
    public void setup() {
        classUnderTest = new ProductController(mockProductService);
    }

    @Test
    void listProducts() {
        when(mockProductService.listAllProducts(anyString())).thenReturn(stubList());
        assertEquals(10, classUnderTest.listProducts("USD").size());
        verify(mockProductService).listAllProducts("USD");
    }

    private List<Product> stubList() {
        List<Product> stubList = new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            Product p = new Product();
            p.setId(i + 100);
            p.setName("ProductName" + i);
            p.setDescription("ProductDescription" + i);
            p.setPrice(200 + i);
            stubList.add(p);
        }
        return stubList;
    }
}