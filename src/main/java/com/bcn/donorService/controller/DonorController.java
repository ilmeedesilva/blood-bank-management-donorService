package com.bcn.donorService.controller;

import com.bcn.donorService.data.Donor;
import com.bcn.donorService.data.DonorRespond;
import com.bcn.donorService.service.DonorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class DonorController {

    @Autowired
    private DonorService donorService;

    @PostMapping(path = "/donors")
    public DonorRespond createDonor(@RequestBody Donor donor){
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

    @GetMapping(path = "/donors", params = "donorNic")
    public Donor getDonorByNic(@RequestParam String donorNic){
        return donorService.getDonorByNic(donorNic);
    }

    @PutMapping(path = "/donors")
    public DonorRespond updateDonor(@RequestBody Donor donor){
        return donorService.updateDonor(donor);
    }

    @DeleteMapping(path = "/donors/{donorNic}")
    public DonorRespond deleteDonorById(@PathVariable String donorNic){
        return donorService.deleteDonorById(donorNic);
    }

    @GetMapping("donors/nic/{donorNic}")
    public Donor getDonorsByNic(@PathVariable String donorNic){
        System.out.println("in donor nic controller");
        return donorService.findDonorByNic(donorNic);
    }
}
