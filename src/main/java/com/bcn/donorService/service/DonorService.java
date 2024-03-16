package com.bcn.donorService.service;

import com.bcn.donorService.data.Donor;
import com.bcn.donorService.data.DonorRepository;
import com.bcn.donorService.data.DonorRespond;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class DonorService {

    @Autowired
    private DonorRepository donorRepository;

    public DonorRespond createDonor(Donor donor) {
        DonorRespond donorRespond = new DonorRespond();
        Donor savedDonor = donorRepository.save(donor);
        if (savedDonor != null) {
            donorRespond.setStatusMsg("Donor created successfully");
            donorRespond.setStatus(200);
        } else {
            donorRespond.setStatusMsg("Failed to create donor");
            donorRespond.setStatus(500);
        }
        return donorRespond;
    }

    public List<Donor> getAllDonors(){
        return donorRepository.findAll();
    }

//    public List<Donor> findDonorByNic(String donorNic){
//        return donorRepository.findDonorByNic(donorNic);
//    }

    public Donor getDonorByNic(String donorNic){
        Optional<Donor> donor = donorRepository.findById(donorNic);
        if(donor.isPresent()) {
            return donor.get();
        }
        return null;
    }

    public DonorRespond updateDonor(Donor donor) {
        DonorRespond donorRespond = new DonorRespond();
        Optional<Donor> donors = donorRepository.findById(donor.getDonorNic());
        if (donors.isPresent()) {
            donorRepository.save(donor);
            donorRespond.setStatusMsg("Donor updated successfully");
            donorRespond.setStatus(200);
        } else {
            donorRespond.setStatusMsg("Donor update unsuccessful");
            donorRespond.setStatus(500);
        }
        return donorRespond;
    }

    public DonorRespond deleteDonorById(String donorNic) {
        DonorRespond donorRespond = new DonorRespond();
        if (donorRepository.existsById(donorNic)) {
            donorRepository.deleteById(donorNic);
            donorRespond.setStatusMsg("Donor deleted successfully");
            donorRespond.setStatus(200);
            return donorRespond;
        }
        donorRespond.setStatusMsg("Donor delete unsuccessful");
        donorRespond.setStatus(500);
        return donorRespond;
    }
}
