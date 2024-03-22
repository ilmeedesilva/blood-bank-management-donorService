package com.bcn.donorService.service;

import com.bcn.donorService.data.Donor;
import com.bcn.donorService.data.DonorRepository;
import com.bcn.donorService.data.DonorRespond;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class DonorService {

    @Autowired
    private DonorRepository donorRepository;


    private final RestTemplate restTemplate;

    public DonorService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<String> callStockService(String token, String stockData, HttpMethod method) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(stockData, headers);

        String stockUrl = "http://localhost:8083/bcn/stocks";
        try {
            return restTemplate.exchange(stockUrl, method, requestEntity, String.class);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error communicating with stock service");
        }
    }


    public DonorRespond createDonor(Donor donor, String token) {
        DonorRespond donorRespond = new DonorRespond();
        try {
            donorRepository.save(donor);
            donorRespond.setStatusMsg("Donor created successfully");
            donorRespond.setStatus(200);

//            String stockData = "{\"donorId\": \"" + donor.getDonorNic() + "\", \"action\": \"add\"}";
//            ResponseEntity<String> stockResponse = callStockService(token, stockData, HttpMethod.PUT);
//            if (stockResponse.getStatusCode() != HttpStatus.OK) {
//                donorRespond.setStatusMsg("Failed to update stock: " + stockResponse.getBody());
//                donorRespond.setStatus(500);
//            }

            return donorRespond;
        } catch (DataIntegrityViolationException e) {
            donorRespond.setStatusMsg("Duplicate entry: " + e.getMessage());
            donorRespond.setStatus(500);
        } catch (Exception e) {
            donorRespond.setStatusMsg("Failed to create donor: " + e.getMessage());
            donorRespond.setStatus(500);
        }
        return donorRespond;
    }


    public List<Donor> getAllDonors() {
        try {
            return donorRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch all donors: " + e.getMessage());
        }
    }

//    public List<Donor> findDonorByNic(String donorNic){
//        return donorRepository.findDonorByNic(donorNic);
//    }

    public Donor getDonorByNic(String donorNic) {
        try {
            Optional<Donor> donor = donorRepository.findById(donorNic);
            return donor.orElse(null);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get donor by NIC: " + e.getMessage());
        }
    }

    public DonorRespond updateDonor(Donor donor) {
        DonorRespond donorRespond = new DonorRespond();
        try {
            Optional<Donor> donors = donorRepository.findById(donor.getDonorNic());
            if (donors.isPresent()) {
                donorRepository.save(donor);
                donorRespond.setStatusMsg("Donor updated successfully");
                donorRespond.setStatus(200);
            } else {
                donorRespond.setStatusMsg("Donor update unsuccessful");
                donorRespond.setStatus(500);
            }
        } catch (Exception e) {
            donorRespond.setStatusMsg("Failed to update donor: " + e.getMessage());
        }
        return donorRespond;
    }

    public DonorRespond deleteDonorById(String donorNic) {
        DonorRespond donorRespond = new DonorRespond();
        try {
            if (donorRepository.existsById(donorNic)) {
                donorRepository.deleteById(donorNic);
                donorRespond.setStatusMsg("Donor deleted successfully");
                donorRespond.setStatus(200);
            } else {
                donorRespond.setStatusMsg("Donor does not exist");
                donorRespond.setStatus(500);
            }
        } catch (Exception e) {
            donorRespond.setStatusMsg("Failed to delete donor: " + e.getMessage());
            donorRespond.setStatus(500);
        }
        return donorRespond;
    }

    public Donor findDonorByNic(String donorNic){
        System.out.println("in donor nic service");
        try {
            Optional<Donor> donor = donorRepository.findDonorByNic(donorNic);
            System.out.println("donor - " + donor);
            if(donor.isPresent()) {
                return donor.get();
            }
            return null;
        } catch (Exception e) {
            System.out.println("Error finding donor by NIC: " + e.getMessage());
            return null;
        }

    }


}