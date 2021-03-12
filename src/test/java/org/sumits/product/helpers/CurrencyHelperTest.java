package org.sumits.product.helpers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;
import org.sumits.product.exception.ProductListException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class CurrencyHelperTest {

    private CurrencyHelper classUnderTest;

    @Mock
    private RestTemplate mockRestTemplate;
    @Mock
    private ResponseEntity mockResponse;

    public static final String TEST_RESPONSE = "{\n" +
            "    \"success\": true,\n" +
            "    \"timestamp\": 1615564567,\n" +
            "    \"base\": \"EUR\",\n" +
            "    \"date\": \"2021-03-12\",\n" +
            "    \"rates\": {\n" +
            "        \"ABC\": 1.01\n" +
            "    }\n" +
            "}";

    @BeforeEach
    public void setup() {
        classUnderTest = new CurrencyHelper(mockRestTemplate);
    }

    @Test
    void convertWithSuccess() {
        when(mockRestTemplate.getForEntity(anyString(), any(Class.class))).thenReturn(mockResponse);
        when(mockResponse.getStatusCode()).thenReturn(HttpStatus.OK);
        when(mockResponse.getBody()).thenReturn(TEST_RESPONSE);
        assertEquals(101, classUnderTest.convert(100.00, "ABC"));
    }

    @Test
    void convert_invalidFixerResponse() {
        when(mockRestTemplate.getForEntity(anyString(), any(Class.class))).thenReturn(mockResponse);
        when(mockResponse.getStatusCode()).thenReturn(HttpStatus.FORBIDDEN);
        try {
            classUnderTest.convert(100.00, "ABC");
        } catch(ProductListException e)  {
            assertEquals("Received unexpected response code 403 from fixer API", e.getMessage());
        }
    }

    @Test
    void buildFixerUrl() {
        ReflectionTestUtils.setField(classUnderTest, "fixerApiUrl", "http://this.is.test.url");
        ReflectionTestUtils.setField(classUnderTest, "fixerApiKey", "abcdefgh12345678");
        assertEquals("http://this.is.test.url?access_key=abcdefgh12345678&symbols=ABC", classUnderTest.buildFixerUrl("ABC"));
    }

    @Test
    void extractRateFromResponse() {
        assertEquals(1.01, classUnderTest.extractRateFromResponse(TEST_RESPONSE, "ABC"));

    }

    @Test
    void testConvert() {
        assertEquals(1.21, Double.parseDouble(String.format("%.2f", classUnderTest.convert(1.1, 1.1))));
    }

    @Test
    void handleDoubleValue() {
        assertEquals(1.21, classUnderTest.handleDoublePrecision(1.2134));
        assertEquals(1.22, classUnderTest.handleDoublePrecision(1.2154));
    }
}