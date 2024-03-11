package com.bcn.donorService.service;

import com.bcn.donorService.data.DonationHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DonationHistoryService {

    @Autowired
    private DonationHistoryRepository donationHistoryRepository;
}
