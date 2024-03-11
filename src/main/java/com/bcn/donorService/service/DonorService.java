package com.bcn.donorService.service;

import com.bcn.donorService.data.Donor;
import com.bcn.donorService.data.DonorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DonorService {

    @Autowired
    private DonorRepository donorRepository;

    public Donor createDonor(Donor donor){
        return donorRepository.save(donor);
    }

    public List<Donor> getAllDonors(){
        return donorRepository.findAll();
    }

    public List<Donor> findDonorByNic(String donorNic){
        return donorRepository.findDonorByNic(donorNic);
    }

    public Donor getDonorByNic(String donorNic){
        Optional<Donor> donor = donorRepository.findById(donorNic);
        if(donor.isPresent()) {
            return donor.get();
        }
        return null;
    }

    public Donor updateDonorByNic(String donorNic) {
        Optional<Donor> donors = donorRepository.findById(donorNic);
        if (donors.isPresent()) {
            return donorRepository.save(donors.get());
        } else {
            return null;
        }
    }

    public boolean deleteDonorById(String donorNic) {
        if (donorRepository.existsById(donorNic)) {
            donorRepository.deleteById(donorNic);
            return true;
        }
        return false;
    }
}
