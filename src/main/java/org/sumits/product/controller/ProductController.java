package org.sumits.product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.sumits.product.enums.CurrencyEnum;
import org.sumits.product.model.Product;
import org.sumits.product.service.ProductService;

import java.util.List;

@RestController
@RequestMapping(value = "/products")
public class ProductController {

    @Autowired
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(value = "/list")
    public List<Product> listProducts(@RequestParam(required = false) String currency) {
        try {
            validateCurrency(currency);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
        return productService.listAllProducts(currency);
    }

    void validateCurrency(String currency) {
        if(null != currency) {
            CurrencyEnum identifiedCurrency = CurrencyEnum.retrieveByName(currency);
            if(null == identifiedCurrency) {
                throw new IllegalArgumentException("Unsupported currency requested");
            }
        }
    }

}
