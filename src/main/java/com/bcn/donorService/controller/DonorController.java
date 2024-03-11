package com.bcn.donorService.controller;

import com.bcn.donorService.data.Donor;
import com.bcn.donorService.service.DonorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DonorController {

    @Autowired
    private DonorService donorService;

    @PostMapping(path = "/donors")
    public Donor createDonor(@RequestBody Donor donor){
        return donorService.createDonor(donor);
    }
    @GetMapping(path = "/donors")
    public List<Donor> getAllDonors(){
        return donorService.getAllDonors();
    }

//    @GetMapping(path = "/donors", params = "donorNic")
//    public List<Donor> findDonorByNic(@RequestParam String donorNic){
//        return donorService.findDonorByNic(donorNic);
//    }

    @GetMapping(path = "/donors/{donorNic}")
    public Donor getDonorByNic(@PathVariable String donorNic){
        return donorService.getDonorByNic(donorNic);
    }

    @PutMapping(path = "/donors/{donorNic}")
    public Donor updateDonorByNic(@PathVariable String donorNic){
        return donorService.updateDonorByNic(donorNic);
    }

    @DeleteMapping(path = "/donors/{donorNic}")
    public boolean deleteDonorById(@PathVariable String donorNic){
        return donorService.deleteDonorById(donorNic);
    }
}
