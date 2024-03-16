package com.bcn.donorService.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DonorRepository extends JpaRepository<Donor, String> {

//    @Query("select d from Donor d where d.donorNic=?1")
//    List<Donor> findDonorByNic(String donorNic);
}
