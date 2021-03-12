package org.sumits.product.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sumits.product.enums.CurrencyEnum;
import org.sumits.product.helpers.CurrencyHelper;
import org.sumits.product.model.Product;
import org.sumits.product.repository.ProductRepository;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CurrencyHelper currencyHelper;

    public ProductService(ProductRepository productRepository, CurrencyHelper currencyHelper) {
        this.productRepository = productRepository;
        this.currencyHelper = currencyHelper;
    }

    public List<Product> listAllProducts(String currency) {
        List<Product> products = (List<Product>)productRepository.findAll();
        if(null != currency) { // Convert price if a specific currency is requested
            products.forEach(
                    product -> product.setDisplayPrice(
                            String.format("%.2f %s", currencyHelper.convert(product.getPrice(), currency), currency)));
        } else {
            products.forEach(
                    product -> product.setDisplayPrice(
                            String.format("%.2f %s", product.getPrice(), CurrencyEnum.BASE_CURRENCY.getName())));
        }
        return products;
    }
}
