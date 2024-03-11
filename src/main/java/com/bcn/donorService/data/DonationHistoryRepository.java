package com.bcn.donorService.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DonationHistoryRepository extends JpaRepository <DonationHistory, Integer> {

    @Query("select dh from donation_history dh where dh.donor.donorNic=?1")
    List<DonationHistory> findDonationByNic(String donorNic);
}