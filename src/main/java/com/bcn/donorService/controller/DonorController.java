package com.bcn.donorService.controller;

import com.bcn.donorService.service.DonorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DonorController {

    @Autowired
    private DonorService donorService;
}
