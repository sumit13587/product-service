package org.sumits.product.helpers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.sumits.product.exception.ProductListException;

@Component
public class CurrencyHelper {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${fixer.api.url}")
    private String fixerApiUrl;
    @Value("${fixer.api.key}")
    private String fixerApiKey;

    public CurrencyHelper(RestTemplate defaultRestTemplate) {
        this.restTemplate = defaultRestTemplate;
    }

    public double convert(double amount, String targetCurrency) {
        ResponseEntity<String> fixerApiResponse;

        String urlWithParams = buildFixerUrl(targetCurrency);
        fixerApiResponse = restTemplate.getForEntity(urlWithParams, String.class);

        if(fixerApiResponse.getStatusCode() != HttpStatus.OK) {
            throw new ProductListException(
                    String.format("Received unexpected response code %d from fixer API", fixerApiResponse.getStatusCode().value()));
        } else {
            double conversionRate = extractRateFromResponse(fixerApiResponse.getBody(), targetCurrency);
            return convert(amount, conversionRate);
        }

    }

    String buildFixerUrl(String targetCurrency) {
        return String.format("%s?access_key=%s&symbols=%s", fixerApiUrl, fixerApiKey, targetCurrency);
    }

    double extractRateFromResponse(String response, String targetCurrency) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response);
            return handleDoublePrecision(root.path("rates").get(targetCurrency).doubleValue());
        } catch (JsonProcessingException e) {
            throw new ProductListException("Failed whilst parsing response from fixer API", e);
        }
    }

    double convert(double from, double rate) {
        return handleDoublePrecision(from * rate);
    }

    double handleDoublePrecision(double value) {
        return Double.parseDouble(String.format("%.2f", value));
    }

}
