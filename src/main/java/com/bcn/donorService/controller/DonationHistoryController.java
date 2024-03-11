package com.bcn.donorService.controller;

import com.bcn.donorService.data.DonationHistory;
import com.bcn.donorService.data.Donor;
import com.bcn.donorService.service.DonationHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DonationHistoryController {

    @Autowired
    private DonationHistoryService donationHistoryService;

    @GetMapping(path = "/donation-history", params = "donorNic")
    public List<DonationHistory> findDonationByNic(@RequestParam String donorNic){
        return donationHistoryService.findDonationByNic(donorNic);
    }
}
