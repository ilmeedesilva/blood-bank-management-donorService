package com.bcn.donorService.service;

import com.bcn.donorService.data.DonationHistory;
import com.bcn.donorService.data.Donor;
import com.bcn.donorService.data.Stock;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class StockHandleService {
    private final RestTemplate restTemplate;

    public StockHandleService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<String> CallStockService(String token, Stock stockDonorItem, String pathParam, HttpMethod method) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Stock> requestEntity = new HttpEntity<>(stockDonorItem, headers);

        String stockUrl = "http://localhost:8083/bcn/stocks" + pathParam;
        try {
            return restTemplate.exchange(stockUrl, method, requestEntity, String.class);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error communicating with stock service");
        }
    }

    public ResponseEntity<String> PostDataToStockService(String token, Stock stockDonorItem, String pathParam){
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Stock> requestEntity = new HttpEntity<>(stockDonorItem, headers);

        String stockUrl = "http://localhost:8083/bcn/stocks" + pathParam;
        try {
            return restTemplate.exchange(stockUrl, HttpMethod.POST, requestEntity, String.class);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error communicating with stock service");
        }
    }

    public ResponseEntity<String> GetDataFromStockService(String pathParam, String token){
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        String stockUrl = "http://localhost:8083/bcn/stocks" + pathParam;
        try {
            return restTemplate.exchange(stockUrl, HttpMethod.GET, requestEntity, String.class);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error communicating with stock service");
        }
    }


    public ResponseEntity<String> PutDataToStockService(String token, Stock stockDonorItem, String pathParam){
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Stock> requestEntity = new HttpEntity<>(stockDonorItem, headers);

        String stockUrl = "http://localhost:8083/bcn/stocks" + pathParam;
        try {
            return restTemplate.exchange(stockUrl, HttpMethod.PUT, requestEntity, String.class);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error communicating with stock service");
        }
    }


    public ResponseEntity<String> DeleteDataFromStockService(String token, String pathParam){
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        String stockUrl = "http://localhost:8083/bcn/stocks" + pathParam;
        try {
            return restTemplate.exchange(stockUrl, HttpMethod.DELETE, requestEntity, String.class);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error communicating with stock service");
        }
    }

}
