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
        Optional<DonationHistory> donationsHistory = donationHistoryRepository.findDonationHistoryByNic(donationHistory.getDonorNic());

        if(donationsHistory.isPresent()){
        List<DonationHistory> donations = donationHistoryRepository.findDonationByNic(donationHistory.getDonorNic());
        DonationHistory latestDonation = findLatestDonation(donationHistory.getDonorNic());
        System.out.println("latestDonation - " + latestDonation.getDonationDate());

        if(donations == null){
            System.out.println("NIC not exist");
        }
        else{
//            if (latestDonation == null || latestDonation.getDonationDate() == null) {
//                System.out.println("No donation");
//                return null;
//            }
            String donationDate = donationHistory.getDonationDate().toString();
            String latestDate = latestDonation.getDonationDate().toString();
//            LocalDate threeMonthsAgo = today.minusMonths(3);

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
        return donationHistoryRespond;
    }

    public List<DonationHistory> getAllDonationHistory(){
        return donationHistoryRepository.findAll();
    }
    public List<DonationHistory> findDonationByNic(String donorNic){
        return donationHistoryRepository.findDonationByNic(donorNic);
    }

    public DonationHistoryRespond updateDonationHistory(DonationHistory donationHistory) {
        DonationHistoryRespond donationHistoryRespond = new DonationHistoryRespond();
        Optional<DonationHistory> donations = donationHistoryRepository.findDonationHistoryByNic(donationHistory.getDonorNic());
        if (donations.isPresent()) {
            donationHistoryRepository.save(donationHistory);
            donationHistoryRespond.setStatusMsg("Donation updated successfully");

        } else {
            donationHistoryRespond.setStatusMsg("Donation update unsuccessful");
        }
        return donationHistoryRespond;
    }

    public DonationHistoryRespond deleteDonationById(int id) {
        DonationHistoryRespond donationHistoryRespond = new DonationHistoryRespond();
        if (donationHistoryRepository.existsById(id)) {
            donationHistoryRepository.deleteById(id);
            donationHistoryRespond.setStatusMsg("Donor deleted successfully");
            return donationHistoryRespond;
        }
        donationHistoryRespond.setStatusMsg("Donor delete unsuccessful");
        return donationHistoryRespond;
    }
}
