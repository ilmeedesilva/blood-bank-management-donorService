package com.bcn.donorService.service;

import com.bcn.donorService.data.DonationHistory;
import com.bcn.donorService.data.DonationHistoryRepository;
import com.bcn.donorService.data.Donor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DonationHistoryService {

    @Autowired
    private DonationHistoryRepository donationHistoryRepository;

    public List<DonationHistory> findDonationByNic(String donorNic){
        return donationHistoryRepository.findDonationByNic(donorNic);
    }
}
