package com.bcn.donorService.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DonationHistoryRepository extends JpaRepository <DonationHistory, Integer> {

    @Query("select dh from DonationHistory dh where dh.donorNic =?1 order by date(donationDate) desc limit 1")
    Optional<DonationHistory> findDonationHistoryByNic(@Param("donorNic") String donorNic);

    @Query("select dh from DonationHistory dh where dh.donorNic=?1")
    List<DonationHistory> findDonationByNic(String donorNic);

    @Query("select dh from DonationHistory dh where dh.donorNic=?1 order by date(donationDate) desc limit 1")
    DonationHistory findLatestDonation(String donorNic);
}