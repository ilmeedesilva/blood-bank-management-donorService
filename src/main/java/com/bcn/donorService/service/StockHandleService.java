package com.bcn.donorService.service;

import com.bcn.donorService.data.DonationHistory;
import com.bcn.donorService.data.Donor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class StockHandleService {
    private final RestTemplate restTemplate;

    public StockHandleService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<String> CallStockService(String token, DonationHistory donationHistory, String pathParam, HttpMethod method) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<DonationHistory> requestEntity = new HttpEntity<>(donationHistory, headers);

        String stockUrl = "http://localhost:8083/bcn/stocks" + pathParam;
        try {
            return restTemplate.exchange(stockUrl, method, requestEntity, String.class);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error communicating with stock service");
        }
    }
}
