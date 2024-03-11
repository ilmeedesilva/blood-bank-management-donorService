package com.bcn.donorService.controller;

import com.bcn.donorService.service.DonationHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DonationHistoryController {

    @Autowired
    private DonationHistoryService donationHistoryService;
}
