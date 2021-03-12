package org.sumits.product;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ProductControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void listProductsDefaultCurrency() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/products/list"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id", is(100)))
                .andExpect(jsonPath("$[0].name", is("GenX Tablet")))
                .andExpect(jsonPath("$[0].description", is("Next generation tablet")))
                .andExpect(jsonPath("$[0].displayPrice", is("279.05 EUR")));

    }

    @Test
    public void listProductsCurrencyUSD() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/products/list?currency=USD"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id", is(100)))
                .andExpect(jsonPath("$[0].name", is("GenX Tablet")))
                .andExpect(jsonPath("$[0].description", is("Next generation tablet")))
                .andExpect(jsonPath("$[0].displayPrice", containsString(" USD")));

    }

    @Test
    public void listProductsInvalidCurrency() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/products/list?currency=ABC"))
                .andExpect(status().isBadRequest());
    }
}
