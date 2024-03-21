package com.bcn.donorService.controller;

import com.bcn.donorService.config.RestTemplateConfig;
import com.bcn.donorService.data.Donor;
import com.bcn.donorService.data.DonorRespond;
import com.bcn.donorService.service.DonorService;
import com.bcn.donorService.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class DonorController {

    @Autowired
    private DonorService donorService;



    private Object authorizeAndGetResult(String token, Supplier<Object> operation) {
        String role = TokenUtil.decryptTokenAndGetRole(token);
        System.out.println("role - " + role);
        if ("Admin".equals(role)) {
            return operation.get();
        } else {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Unauthorized access");
            return response;
        }
    }

    @PostMapping(path = "/donors")
    public Object createDonor(@RequestBody Donor donor, @RequestHeader("Authorization") String token) {
        return authorizeAndGetResult(token, () -> donorService.createDonor(donor, token));
    }

    @GetMapping(path = "/donors")
    public Object getAllDonors(@RequestHeader("Authorization") String token) {
        return authorizeAndGetResult(token, () -> donorService.getAllDonors());
    }

    @GetMapping(path = "/donors", params = "donorNic")
    public Object getDonorByNic(@RequestHeader("Authorization") String token, @RequestParam String donorNic) {
        return authorizeAndGetResult(token, () -> donorService.getDonorByNic(donorNic));
    }

    @PutMapping(path = "/donors")
    public Object updateDonor(@RequestBody Donor donor, @RequestHeader("Authorization") String token) {
        return authorizeAndGetResult(token, () -> donorService.updateDonor(donor));
    }

    @DeleteMapping(path = "/donors/{donorNic}")
    public Object deleteDonorById(@PathVariable String donorNic, @RequestHeader("Authorization") String token) {
        return authorizeAndGetResult(token, () -> donorService.deleteDonorById(donorNic));
    }

    @GetMapping("donors/nic/{donorNic}")
    public Object getDonorsByNic(@PathVariable String donorNic, @RequestHeader("Authorization") String token) {
        return authorizeAndGetResult(token, () -> donorService.findDonorByNic(donorNic));
    }


}
