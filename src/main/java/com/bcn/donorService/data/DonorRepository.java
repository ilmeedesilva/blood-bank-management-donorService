package com.bcn.donorService.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DonorRepository extends JpaRepository<Donor, String> {

    // @Query("select d from Donor d where d.donorNic=?1")
    // List<Donor> findDonorByNic(String donorNic);

    @Query("select d from Donor d where d.donorNic =?1")
    Optional<Donor> findDonorByNic(@Param("donorNic") String donorNic);

    @Query("select d from Donor d where d.bloodType =?1")
    List<Donor> getDonorsByBloodType(String bloodType);
}
