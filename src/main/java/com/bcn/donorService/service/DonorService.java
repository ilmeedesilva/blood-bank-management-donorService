package com.bcn.donorService.service;

import com.bcn.donorService.data.Donor;
import com.bcn.donorService.data.DonorRepository;
import com.bcn.donorService.data.DonorRespond;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class DonorService {

    @Autowired
    private DonorRepository donorRepository;

    public DonorRespond createDonor(Donor donor) {
        DonorRespond donorRespond = new DonorRespond();
        try {
            donorRepository.save(donor);
            donorRespond.setStatusMsg("Donor created successfully");
            donorRespond.setStatus(200);
            return donorRespond;
        } catch (DataIntegrityViolationException e) {
//            throw new RuntimeException("Duplicate entry: " + e.getMessage());
            donorRespond.setStatusMsg("Duplicate entry: " + e.getMessage());
            donorRespond.setStatus(500);
        } catch (Exception e) {
//            throw new RuntimeException("Failed to create donor: " + e.getMessage());
            donorRespond.setStatusMsg("Failed to create donor: " + e.getMessage());
            donorRespond.setStatus(500);
        }
        return donorRespond;
    }

    public List<Donor> getAllDonors() {
        try {
            return donorRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch all donors: " + e.getMessage());
        }
    }

//    public List<Donor> findDonorByNic(String donorNic){
//        return donorRepository.findDonorByNic(donorNic);
//    }

    public Donor getDonorByNic(String donorNic) {
        try {
            Optional<Donor> donor = donorRepository.findById(donorNic);
            return donor.orElse(null);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get donor by NIC: " + e.getMessage());
        }
    }

    public DonorRespond updateDonor(Donor donor) {
        DonorRespond donorRespond = new DonorRespond();
        try {
            Optional<Donor> donors = donorRepository.findById(donor.getDonorNic());
            if (donors.isPresent()) {
                donorRepository.save(donor);
                donorRespond.setStatusMsg("Donor updated successfully");
                donorRespond.setStatus(200);
            } else {
                donorRespond.setStatusMsg("Donor update unsuccessful");
                donorRespond.setStatus(500);
            }
        } catch (Exception e) {
            donorRespond.setStatusMsg("Failed to update donor: " + e.getMessage());
        }
        return donorRespond;
    }

    public DonorRespond deleteDonorById(String donorNic) {
        DonorRespond donorRespond = new DonorRespond();
        try {
            if (donorRepository.existsById(donorNic)) {
                donorRepository.deleteById(donorNic);
                donorRespond.setStatusMsg("Donor deleted successfully");
                donorRespond.setStatus(200);
            } else {
                donorRespond.setStatusMsg("Donor does not exist");
                donorRespond.setStatus(500);
            }
        } catch (Exception e) {
            donorRespond.setStatusMsg("Failed to delete donor: " + e.getMessage());
            donorRespond.setStatus(500);
        }
        return donorRespond;
    }

    public Donor findDonorByNic(String donorNic){
        System.out.println("in donor nic service");
        try {
            Optional<Donor> donor = donorRepository.findDonorByNic(donorNic);
            System.out.println("donor - " + donor);
            if(donor.isPresent()) {
                return donor.get();
            }
            return null;
        } catch (Exception e) {
            System.out.println("Error finding donor by NIC: " + e.getMessage());
            return null;
        }

    }


}