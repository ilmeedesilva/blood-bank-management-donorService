package com.bcn.donorService.service;

import com.bcn.donorService.data.*;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
public class DonationHistoryService {

    @Autowired
    private DonationHistoryRepository donationHistoryRepository;

    @Autowired
    private StockHandleService stockHandleService;

    @Autowired
    private DonorService donorService;

    public DonationHistory findLatestDonation(String donorNic) {
        return donationHistoryRepository.findLatestDonation(donorNic);
    }

    public DonationHistoryRespond createDonationHistory(DonationHistory donationHistory, String token) {
        DonationHistoryRespond donationHistoryRespond = new DonationHistoryRespond();
        DonationHistory donHis = new DonationHistory();
        donHis.setDonorNic(donationHistory.getDonorNic());
        donHis.setDonationDate(donationHistory.getDonationDate());
        donHis.setQuantity(donationHistory.getQuantity());
        donHis.setRecipientId(11);
        donHis.setRecipientType("BLOOD BANK C.");

        List<DonationHistory> donationHistoriesList = getDonationsByNicAndDate(donationHistory.getDonorNic(),
                donationHistory.getDonationDate());

        System.out.println();
        if (donationHistoriesList.isEmpty()) {

            System.out.println("no donations from donor");
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
                donationHistoryRepository.save(donHis);
                donationHistoryRespond.setStatusMsg("Donation added successfully");
                donationHistoryRespond.setStatus(200);

                System.out.println( "is stock exist ? " + isStockExist(donHis.getDonationDate(), token));
                if (isStockExist(donHis.getDonationDate(), token)) {
                    System.out.println("stock exist, sending put request");
                    // put request
//                    SendDonationToStock(donHis, token, HttpMethod.PUT);
                    String bloodType = donorService.getDonorByNic(donHis.getDonorNic()).getBloodType();

                    HandleUpdateDonationToStock(donHis.getDonorNic(), token, donHis.getDonationDate(), bloodType);
                } else {
                    // post request
                    System.out.println("stock does not exist, sending post request");
                    SendDonationToStock(donHis, token, HttpMethod.POST);
                }

                // }
                return donationHistoryRespond;
            } catch (Exception e) {
                donationHistoryRespond.setStatusMsg("Failed to add donation: " + e.getMessage());
                donationHistoryRespond.setStatus(500);
            }
        } else {
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


    public List<DonationHistory> getDonationsByDate(Date date) {
        try {
            return donationHistoryRepository.getDonationHistoryByDate(date);
        } catch (Exception e) {
            System.out.println("Failed to fetch all donation history by specific date: " + e.getMessage());
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

    public DonationHistoryRespond updateDonationHistory(DonationHistory donationHistory, String token) {
        DonationHistoryRespond donationHistoryRespond = new DonationHistoryRespond();
        List<DonationHistory> donationHistoriesListByID = getDonationsById(donationHistory.getId());
        List<DonationHistory> donationHistoriesList = getDonationsByNicAndDate(donationHistory.getDonorNic(),
                donationHistory.getDonationDate());

        // System.out.println("donor date :" + donationHistory.getDonationDate());
        // System.out.println("donor id :" + donationHistory.getId());
        // System.out.println("List id: " + donationHistoriesList.get(0).getId());
        // System.out.println("donor history size :" + donationHistoriesList.size());

        System.out.println("01 update donation");
        if (donationHistoriesList.isEmpty() || donationHistoriesList.size() == 1
                && donationHistoriesList.get(0).getId() == donationHistory.getId()) {
            try {

                System.out.println("02 update donation verified");

                // DonationHistory donationHistory1 = new DonationHistory();
                // donationHistory1.setDonationDate(donationHistory.getDonationDate());
                // donationHistory1.setQuantity(donationHistory.getQuantity());
                // donationHistory1.setDonorNic(donationHistory.getDonorNic());

                Date dateBeforeUpdate = donationHistoriesListByID.get(0).getDonationDate();
                Date updatedDate = donationHistory.getDonationDate();
                String bloodType = donorService.getDonorByNic(donationHistory.getDonorNic()).getBloodType();

                System.out.println("03 put donation pre data - " + dateBeforeUpdate);
                System.out.println("04 put donation now data - " + updatedDate);
                System.out.println("05 put donation bloodType - " + bloodType);

                boolean areDatesAreSame = dateBeforeUpdate.toLocalDate().equals(updatedDate.toLocalDate());

                System.out.println("06 are dates are same ? " + areDatesAreSame);
                if (donationHistoryRepository.existsById(donationHistory.getId())) {
                    donationHistoryRepository.save(donationHistory);
                    donationHistoryRespond.setStatusMsg("Donation updated successfully");
                    donationHistoryRespond.setStatus(200);


//                    DonationHistory updatedDonations = new DonationHistory();
                    if (areDatesAreSame) {
//                        List<Donor> donorsWithSameBloodType = donorService.getDonorsByBloodType(bloodType);
//                        List<DonationHistory> donorHistoryForBloodType = new ArrayList<>();
//                        System.out.println(
//                                "total donor count for bloodtype " + bloodType + ": " + donorsWithSameBloodType.size());
//                        float totalQty = 0.0f;
//                        for (Donor eachDonor : donorsWithSameBloodType) {
//                            List<DonationHistory> donations = getDonationsByNicAndDate(eachDonor.getDonorNic(),
//                                    updatedDate);
//                            donorHistoryForBloodType.addAll(donations);
//                        }
//                        for (DonationHistory eachHistory : donorHistoryForBloodType) {
//                            System.out.println("each qty = " + eachHistory.getQuantity());
//                            totalQty += eachHistory.getQuantity();
//                        }
//                        System.out.println("total qty of the blood type " + bloodType + ": " + totalQty);
//                        updatedDonations.setDonationDate(updatedDate);
//                        updatedDonations.setDonorNic(donationHistory.getDonorNic());
//                        updatedDonations.setQuantity(totalQty);
//                        SendDonationToStock(updatedDonations, token, HttpMethod.PUT);

                        HandleUpdateDonationToStock(donationHistory.getDonorNic(), token, updatedDate, bloodType);
                    } else {
                        // donation history with updated date
                        HandleUpdateDonationToStock(donationHistory.getDonorNic(), token, dateBeforeUpdate, bloodType);
                        HandleUpdateDonationToStock(donationHistory.getDonorNic(), token, updatedDate, bloodType);
                    }
                } else {
                    donationHistoryRespond.setStatusMsg("Donation update unsuccessful");
                    donationHistoryRespond.setStatus(500);
                }

            } catch (Exception e) {
                donationHistoryRespond.setStatusMsg("Failed to update donation: " + e.getMessage());
                donationHistoryRespond.setStatus(500);
            }
        } else {
            donationHistoryRespond.setStatusMsg("Donor has a donation on this date");
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

    public DonationHistoryRespond deleteDonationByNicAndDate(String donorNic, String donationDate) {
        DonationHistoryRespond donationHistoryRespond = new DonationHistoryRespond();
        try {
            Date sqlDate = Date.valueOf(donationDate);
            donationHistoryRepository.deleteDonationByNicAndDate(donorNic, sqlDate);
            donationHistoryRespond.setStatusMsg("Donation delete successful");
            donationHistoryRespond.setStatus(200);

        } catch (Exception e) {
            donationHistoryRespond.setStatusMsg("Donation delete unsuccessful: " + e.getMessage());
            donationHistoryRespond.setStatus(200);
        }
        return donationHistoryRespond;
    }

    public boolean isStockExist(Date date, String token) {
        try {
            ResponseEntity<String> stockResponse = stockHandleService.GetDataFromStockService("/stock-items/" + date,
                    token);
            System.out.println("stockResponse - " + stockResponse);

            if (stockResponse.getStatusCode() == HttpStatus.OK) {
                JSONArray jsonArray = new JSONArray(stockResponse.getBody());

                if (!jsonArray.isEmpty()) {
                    return true;
                }
            }
        } catch (Exception e) {
            System.out.println("an error in is stock exist check method: " + e.getMessage());
        }

        return false;
    }

    public void HandleUpdateDonationToStock(String nic, String token, Date targetDate, String bloodType){
        DonationHistory updatedDonations = new DonationHistory();
        List<Donor> donorsWithSameBloodType = donorService.getDonorsByBloodType(bloodType);
        List<DonationHistory> donorHistoryForBloodType = new ArrayList<>();
        System.out.println(
                "08 total donor count for same bloodtype = " + bloodType + ": " + donorsWithSameBloodType.size());
        float totalQty = 0.0f;
        for (Donor eachDonor : donorsWithSameBloodType) {

            List<DonationHistory> donations = getDonationsByNicAndDate(eachDonor.getDonorNic(),
                    targetDate);
            donorHistoryForBloodType.addAll(donations);
        }
        System.out.println(
                "09 total donors history for same blood type and same date = " + bloodType + ": " + donorHistoryForBloodType.size());
        for (DonationHistory eachHistory : donorHistoryForBloodType) {
            System.out.println("each qty = " + eachHistory.getQuantity());
            totalQty += eachHistory.getQuantity();
        }
        System.out.println("10 total qty of the blood type " + bloodType + ": " + totalQty);
        updatedDonations.setDonationDate(targetDate);
        updatedDonations.setDonorNic(nic);
        updatedDonations.setQuantity(totalQty);
        SendDonationToStock(updatedDonations, token, HttpMethod.PUT);
    }

    public void SendDonationToStock(DonationHistory historydata, String token, HttpMethod method) {
        Donor selectedDonor = new Donor();
        selectedDonor = donorService.getDonorByNic(historydata.getDonorNic());

        if (selectedDonor != null) {

            System.out.println("SendDonationToStock donor blood type - " + selectedDonor.getBloodType());
            Stock stockItem = new Stock();
            stockItem.setDonorNic(historydata.getDonorNic());
            stockItem.setDonationDate(historydata.getDonationDate());
            stockItem.setQuantity(historydata.getQuantity());
            stockItem.setBloodType(selectedDonor.getBloodType());

            System.out.println("stock item nic = " + stockItem.getDonorNic());
            System.out.println("stock item blood type  = " + stockItem.getBloodType());
            System.out.println("stock item date = " + stockItem.getDonationDate());
            System.out.println("stock item qty = " + stockItem.getQuantity());

            if (historydata.getQuantity() > 0) {
                ResponseEntity<String> stockResponse = null;
                if (method == HttpMethod.PUT) {
                    System.out.println("sending put request to stock");
                    stockResponse = stockHandleService.PutDataToStockService(token, stockItem, "/stock-items/");
                } else if (method == HttpMethod.POST) {
                    System.out.println("sending post request to stock");
                    stockResponse = stockHandleService.PostDataToStockService(token, stockItem, "/stock-items/");
                }

                System.out.println("stock res :" + stockResponse);
            }
        }
    }

    public List<DonationHistory> getDonationsByNicAndDate(String nic, Date DonationDate) {
        try {
            return donationHistoryRepository.getDonationHistoryByNicAndDate(nic, DonationDate);
        } catch (Exception e) {
            return null;
        }
    }

    public List<DonationHistory> getDonationsById(int id) {
        try {
            return donationHistoryRepository.getDonationHistoryById(id);
        } catch (Exception e) {
            return null;
        }
    }

    // public List<DonationHistory> getDonationsByDate(Date donationDate) {
    // try {
    // return donationHistoryRepository.getDonationHistoryByDate(donationDate);
    // } catch (Exception e) {
    // return null;
    // }
    // }

}