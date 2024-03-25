package com.bcn.donorService.service;

import com.bcn.donorService.data.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class DonationHistoryService {

    @Autowired
    private DonationHistoryRepository donationHistoryRepository;

    public DonationHistory findLatestDonation(String donorNic) {
        return donationHistoryRepository.findLatestDonation(donorNic);
    }

    public DonationHistoryRespond createDonationHistory(DonationHistory donationHistory) {
        DonationHistoryRespond donationHistoryRespond = new DonationHistoryRespond();
        DonationHistory donHis = new DonationHistory();
        donHis.setDonorNic(donationHistory.getDonorNic());
        donHis.setDonationDate(donationHistory.getDonationDate());
        donHis.setQuantity(donationHistory.getQuantity());

        List<DonationHistory> donationHistoriesList = getDonationsByDate(donationHistory.getDonorNic(), donationHistory.getDonationDate());
        System.out.println("DONOR HIS");
        System.out.println("donationHistory.getDonorNic() - " + donHis.getDonorNic());
        System.out.println();
        if(donationHistoriesList.isEmpty()){


        try {
            // System.out.println("DONOR RES - " +
            // donationHistoryRepository.findDonationHistoryByNic(donHis.getDonorNic()));
            // Optional<DonationHistory> donationsHistory =
            // donationHistoryRepository.findDonationHistoryByNic(donHis.getDonorNic());
            // System.out.println("TRY TRY TRY");
            // System.out.println("donationsHistory.isPresent() - "+
            // donationsHistory.isPresent());
            // if(donationsHistory.isPresent()){
            //// List<DonationHistory> donations =
            // donationHistoryRepository.findDonationByNic(donationHistory.getDonorNic());
            // DonationHistory latestDonation =
            // findLatestDonation(donationHistory.getDonorNic());
            // System.out.println("latestDonation - " + latestDonation.getDonationDate());
            ////
            //// if(donations == null){
            //// System.out.println("NIC not exist");
            //// }
            //
            // String donationDate = donationHistory.getDonationDate().toString();
            // String latestDate = latestDonation.getDonationDate().toString();
            //
            // if(DateComparison.isGapGreaterThanOrEqualToMonths(latestDate,donationDate,3
            // )) {
            // donationHistoryRepository.save(donHis);
            // donationHistoryRespond.setStatusMsg("Donation added successfully");
            // donationHistoryRespond.setStatus(200);
            // }
            // else {
            // System.out.println("cannot donate 1");
            // donationHistoryRespond.setStatusMsg("Failed to add donation"); // period
            // below than 3 months from last donation
            // donationHistoryRespond.setStatus(500);
            // }
            //
            // } else {
            System.out.println("ELSE");
            donationHistoryRepository.save(donHis);
            donationHistoryRespond.setStatusMsg("Donation added successfully");
            donationHistoryRespond.setStatus(200);
            // }
            return donationHistoryRespond;
        } catch (Exception e) {
            donationHistoryRespond.setStatusMsg("Failed to add donation: " + e.getMessage());
            donationHistoryRespond.setStatus(500);
        }
        }
        else{
            donationHistoryRespond.setStatusMsg("Failed to add donation. donor has donation on this date");
            donationHistoryRespond.setStatus(500);
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

            // DonationHistory donationHistory1 = new DonationHistory();
            // donationHistory1.setDonationDate(donationHistory.getDonationDate());
            // donationHistory1.setQuantity(donationHistory.getQuantity());
            // donationHistory1.setDonorNic(donationHistory.getDonorNic());
            if (donationHistoryRepository.existsById(donationHistory.getId())) {
                donationHistoryRepository.save(donationHistory);
                donationHistoryRespond.setStatusMsg("Donation updated successfully");
                donationHistoryRespond.setStatus(200);
            } else {
                donationHistoryRespond.setStatusMsg("Donation update unsuccessful");
                donationHistoryRespond.setStatus(500);
            }

        } catch (Exception e) {
            donationHistoryRespond.setStatusMsg("Failed to update donation: " + e.getMessage());
            donationHistoryRespond.setStatus(500);
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

    public  DonationHistoryRespond deleteDonationByNicAndDate(String donorNic, String donationDate){
        DonationHistoryRespond donationHistoryRespond = new DonationHistoryRespond();
        try{
            Date sqlDate = Date.valueOf(donationDate);
            donationHistoryRepository.deleteDonationByNicAndDate(donorNic, sqlDate);
            donationHistoryRespond.setStatusMsg("Donation delete successful");
            donationHistoryRespond.setStatus(200);

        }catch (Exception e){
            donationHistoryRespond.setStatusMsg("Donation delete unsuccessful: " + e.getMessage());
            donationHistoryRespond.setStatus(200);
        }
            return donationHistoryRespond;
    }

    public List<DonationHistory> getDonationsByDate(String nic, Date DonationDate){
        try{
            return donationHistoryRepository.getDonationHistoryByDate(nic, DonationDate);
        }catch (Exception e){
            return null;
        }
    }

}