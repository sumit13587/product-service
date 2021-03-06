package org.sumits.product.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.sumits.product.helpers.CurrencyHelper;
import org.sumits.product.model.Product;
import org.sumits.product.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class ProductServiceTest {

    private ProductService classUnderTest;

    @Mock
    private ProductRepository mockProductRepository;
    @Mock
    private CurrencyHelper mockCurrencyHelper;

    @BeforeEach
    public void setup() {
        classUnderTest = new ProductService(mockProductRepository, mockCurrencyHelper);
    }

    @Test
    void listAllProducts() {
        when(mockProductRepository.findAll()).thenReturn(stubList());
        assertEquals(10, classUnderTest.listAllProducts("ABC").size());
        verify(mockProductRepository).findAll();
        verify(mockCurrencyHelper, times(10)).convert(anyDouble(), anyString());
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