package com.bcn.donorService.controller;

import com.bcn.donorService.data.DonationHistory;
import com.bcn.donorService.data.DonationHistoryRespond;
import com.bcn.donorService.service.DonationHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class DonationHistoryController {

    @Autowired
    private DonationHistoryService donationHistoryService;

    @PostMapping(path = "/donation-history")
    public DonationHistoryRespond createDonationHistory(@RequestBody DonationHistory donationHistory){
        System.out.println("*********************************");
        System.out.println("NIC - "+ donationHistory.getDonorNic());
        System.out.println("Date - "+ donationHistory.getDonationDate());
        System.out.println("Qty - "+ donationHistory.getQuantity());
        return donationHistoryService.createDonationHistory(donationHistory);
    }

    @GetMapping(path = "/donation-history")
    public List<DonationHistory> getAllDonationHistory(){
        return donationHistoryService.getAllDonationHistory();
    }

    @GetMapping(path = "/donation-history/{donorNic}")
    public List<DonationHistory> findDonationByNic(@PathVariable String donorNic){
        System.out.println("donorNic - "+ donorNic);
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

    @DeleteMapping(path = "/donation-history")
    public DonationHistoryRespond deleteDonationByNicAndDate(
            @RequestParam String donorNic,
            @RequestParam String donationDate) {
        return donationHistoryService.deleteDonationByNicAndDate(donorNic, donationDate);
    }

}
