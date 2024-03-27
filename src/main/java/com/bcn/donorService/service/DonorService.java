package com.bcn.donorService.service;

import com.bcn.donorService.data.Donor;
import com.bcn.donorService.data.DonorRepository;
import com.bcn.donorService.data.DonorRespond;
import com.bcn.donorService.utils.CsvHandler;
import com.bcn.donorService.utils.StockHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class DonorService {

    @Autowired
    private DonorRepository donorRepository;

//    private final RestTemplate restTemplate;
//
//    public DonorService(RestTemplate restTemplate) {
//        this.restTemplate = restTemplate;
//    }
//
//    public ResponseEntity<String> callStockService(String token, Donor donor, String pathParam, HttpMethod method) {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setBearerAuth(token);
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        HttpEntity<Donor> requestEntity = new HttpEntity<>(donor, headers);
//
//        String stockUrl = "http://localhost:8083/bcn/stocks" + pathParam;
//        try {
//            return restTemplate.exchange(stockUrl, method, requestEntity, String.class);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("Error communicating with stock service");
//        }
//    }

    public DonorRespond createDonor(Donor donor, String token) {
        DonorRespond donorRespond = new DonorRespond();
        try {
            donorRepository.save(donor);
            donorRespond.setStatusMsg("Donor created successfully");
            donorRespond.setStatus(200);

//            ResponseEntity<String> stockResponse = callStockService(token, donor, "/" + donor.getDonorNic(),
//                    HttpMethod.PUT);
//            if (stockResponse.getStatusCode() != HttpStatus.OK) {
//                donorRespond.setStatusMsg("Failed to update stock: " + stockResponse.getBody());
//                donorRespond.setStatus(500);
//            }
//            System.out.println("Stock updated with " + stockResponse);
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

    public DonorRespond createDonorFromCsv(MultipartFile file, String token) {
        DonorRespond donorRespond = new DonorRespond();
        try {
            InputStream inputStream = file.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String[] headers = reader.readLine().trim().split(",");
            List<Map<String, String>> data = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] columns = line.split(",");
                Map<String, String> row = new HashMap<>();
                for (int i = 0; i < headers.length && i < columns.length; i++) {
                    row.put(headers[i].replaceAll("^\\W+", "").trim(), columns[i]);
                    System.out.println("headers[i] - " + headers[i]);
                    System.out.println("columns[i] - " + (columns[i].isEmpty() ? "" : columns[i]));
                }
                try {
                    CsvHandler.validateCsvDonor(row);
                    data.add(row);
                } catch (Exception e) {
                    System.out.println("Validation error for row: " + e.getMessage());
                }
            }

            createEachCsvDonor(data);

            donorRespond.setStatusMsg("File uploaded successfully");
            donorRespond.setStatus(200);

        } catch (Exception e) {
            donorRespond.setStatusMsg("Error reading the CSV file: " + e.getMessage());
            donorRespond.setStatus(500);
        }

        return donorRespond;
    }

    private void createEachCsvDonor(List<Map<String, String>> data) {
        try {
            for (Map<String, String> row : data) {
                Donor donor = new Donor();
                System.out.println("Row keys: " + row.keySet());
                String donorNic = row.get("donorNic");
                System.out.println("donorNic value: " + donorNic);
                donor.setDonorNic(donorNic);
                donor.setFirstName(row.get("firstName"));
                donor.setLastName(row.get("lastName"));
                donor.setBloodType(row.get("bloodType"));
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    java.util.Date utilDate = sdf.parse(row.get("birthday"));
                    java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
                    donor.setBirthday(sqlDate);
                } catch (ParseException e) {
                    System.out.println("ERROR IN csv set values : " + e.getMessage());
                }
                donor.setStreetNo(row.get("streetNo"));
                donor.setStreet(row.get("street"));
                donor.setCity(row.get("city"));

                String contactNoStr = row.get("contactNo");
                String emergencyContactNoStr = row.get("emergencyContactNo");
                String weightStr = row.get("weight");
                String unitsStr = row.get("units");

                if (!contactNoStr.isEmpty()) {
                    donor.setContactNo(Long.parseLong(contactNoStr));
                }
                if (!emergencyContactNoStr.isEmpty()) {
                    donor.setEmergencyContactNo(Long.parseLong(emergencyContactNoStr));
                }
                if (!weightStr.isEmpty()) {
                    donor.setWeight(Float.parseFloat(weightStr));
                }
                if (!unitsStr.isEmpty()) {
                    donor.setUnits(Float.parseFloat(unitsStr));
                }

                for (String key : row.keySet()) {
                    System.out.println(key + ": " + row.get(key));
                }

                donorRepository.save(donor);
            }
        } catch (Exception e) {
            System.out.println("ERROR IN CREATE DONOR CSV: " + e.getMessage());
        }
    }

    public List<Donor> getAllDonors() {
        try {
            return donorRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch all donors: " + e.getMessage());
        }
    }

    // public List<Donor> findDonorByNic(String donorNic){
    // return donorRepository.findDonorByNic(donorNic);
    // }

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

    public Donor findDonorByNic(String donorNic) {
        System.out.println("in donor nic service");
        try {
            Optional<Donor> donor = donorRepository.findDonorByNic(donorNic);
            System.out.println("donor - " + donor);
            if (donor.isPresent()) {
                return donor.get();
            }
            return null;
        } catch (Exception e) {
            System.out.println("Error finding donor by NIC: " + e.getMessage());
            return null;
        }

    }

}