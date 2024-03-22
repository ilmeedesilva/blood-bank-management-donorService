package com.bcn.donorService.utils;

import com.bcn.donorService.data.Donor;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class StockHandler {

    private static RestTemplate restTemplate;
    public StockHandler(RestTemplate restTemplate) {
        StockHandler.restTemplate = restTemplate;
    }
    public static ResponseEntity<String> callStockService(String token, Donor donor, String pathParam, HttpMethod method) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Donor> requestEntity = new HttpEntity<>(donor, headers);

        String stockUrl = "http://localhost:8083/bcn/stocks" + pathParam;
        try {
            return restTemplate.exchange(stockUrl, method, requestEntity, String.class);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error communicating with stock service");
        }
    }
}
