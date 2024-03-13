package com.bcn.donorService.controller;

import com.bcn.donorService.data.DonationHistory;
import com.bcn.donorService.data.DonationHistoryRespond;
import com.bcn.donorService.data.Donor;
import com.bcn.donorService.data.DonorRespond;
import com.bcn.donorService.service.DonationHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DonationHistoryController {

    @Autowired
    private DonationHistoryService donationHistoryService;

    @PostMapping(path = "/donation-history")
    public DonationHistoryRespond createDonationHistory(@RequestBody DonationHistory donationHistory){
        return donationHistoryService.createDonationHistory(donationHistory);
    }

    @GetMapping(path = "/donation-history")
    public List<DonationHistory> getAllDonationHistory(){
        return donationHistoryService.getAllDonationHistory();
    }

    @GetMapping(path = "/donation-history/{donorNic}")
    public List<DonationHistory> findDonationByNic(@PathVariable String donorNic){
        return donationHistoryService.findDonationByNic(donorNic);
    }

    @GetMapping(path = "/donation-history/{donorNic}/latest")
    public DonationHistory findLatestDonation(@PathVariable String donorNic){
        return donationHistoryService.findLatestDonation(donorNic);
    }

    @PutMapping(path = "/donation-history")
    public DonationHistoryRespond updateDonationHistory(@RequestBody DonationHistory donationHistory){
        return donationHistoryService.updateDonationHistory(donationHistory);
    }

    @DeleteMapping(path = "/donation-history/{id}")
    public DonationHistoryRespond deleteDonationById(@PathVariable int id){
        return donationHistoryService.deleteDonationById(id);
    }
}
