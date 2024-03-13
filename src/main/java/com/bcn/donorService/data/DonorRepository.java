package com.bcn.donorService.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DonorRepository extends JpaRepository<Donor, String> {

//    @Query("select d from Donor d where d.donorNic=?1")
//    List<Donor> findDonorByNic(String donorNic);
}
