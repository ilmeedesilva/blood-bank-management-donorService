package com.bcn.donorService.service;

import com.bcn.donorService.data.*;
import com.bcn.donorService.utils.DateComparison;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class DonationHistoryService {

    @Autowired
    private DonationHistoryRepository donationHistoryRepository;
    public DonationHistory findLatestDonation(String donorNic){
        return donationHistoryRepository.findLatestDonation(donorNic);

    }
    public DonationHistoryRespond createDonationHistory(DonationHistory donationHistory){
        DonationHistoryRespond donationHistoryRespond = new DonationHistoryRespond();
        try {
            Optional<DonationHistory> donationsHistory = donationHistoryRepository.findDonationHistoryByNic(donationHistory.getDonorNic());

            if(donationsHistory.isPresent()){
                List<DonationHistory> donations = donationHistoryRepository.findDonationByNic(donationHistory.getDonorNic());
                DonationHistory latestDonation = findLatestDonation(donationHistory.getDonorNic());
                System.out.println("latestDonation - " + latestDonation.getDonationDate());

                if(donations == null){
                    System.out.println("NIC not exist");
                }
                else{
                    String donationDate = donationHistory.getDonationDate().toString();
                    String latestDate = latestDonation.getDonationDate().toString();

                    if(DateComparison.isGapGreaterThanOrEqualToMonths(latestDate,donationDate,3 )) {
                        donationHistoryRepository.save(donationHistory);
                        donationHistoryRespond.setStatusMsg("Donation added successfully");
                        return donationHistoryRespond;
                    }
                    else {
                        System.out.println("cannot donate 1");
                        donationHistoryRespond.setStatusMsg("Failed to add donation");
                        return donationHistoryRespond;
                    }
                }
            }
            System.out.println("cannot donate 2");
            donationHistoryRespond.setStatusMsg("Failed to add donation");
        } catch (Exception e) {
            donationHistoryRespond.setStatusMsg("Failed to add donation: " + e.getMessage());
        }
        return donationHistoryRespond;
    }


    public List<DonationHistory> getAllDonationHistory() {
        try {
            return donationHistoryRepository.findAll();
        } catch (Exception e) {
            System.out.println("Failed to fetch all donation history: " + e.getMessage());
            return null;
        }
    }
    public List<DonationHistory> findDonationByNic(String donorNic) {
        try {
            return donationHistoryRepository.findDonationByNic(donorNic);
        } catch (Exception e) {
            System.out.println("Failed to find donations by NIC: " + e.getMessage());
            return null;
        }
    }

    public DonationHistoryRespond updateDonationHistory(DonationHistory donationHistory) {
        DonationHistoryRespond donationHistoryRespond = new DonationHistoryRespond();
        try {
            Optional<DonationHistory> donations = donationHistoryRepository.findDonationHistoryByNic(donationHistory.getDonorNic());
            if (donations.isPresent()) {
                donationHistoryRepository.save(donationHistory);
                donationHistoryRespond.setStatusMsg("Donation updated successfully");
            } else {
                donationHistoryRespond.setStatusMsg("Donation update unsuccessful");
            }
        } catch (Exception e) {
            donationHistoryRespond.setStatusMsg("Failed to update donation: " + e.getMessage());
        }
        return donationHistoryRespond;
    }


    public DonationHistoryRespond deleteDonationById(int id) {
        DonationHistoryRespond donationHistoryRespond = new DonationHistoryRespond();
        try {
            if (donationHistoryRepository.existsById(id)) {
                donationHistoryRepository.deleteById(id);
                donationHistoryRespond.setStatusMsg("Donation deleted successfully");
            } else {
                donationHistoryRespond.setStatusMsg("Donation does not exist");
            }
        } catch (Exception e) {
            donationHistoryRespond.setStatusMsg("Failed to delete donation: " + e.getMessage());
        }
        return donationHistoryRespond;
    }

}