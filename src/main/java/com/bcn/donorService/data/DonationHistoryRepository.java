package com.bcn.donorService.data;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface DonationHistoryRepository extends JpaRepository<DonationHistory, Integer> {

    @Query("select dh from DonationHistory dh where dh.donorNic =?1 order by date(donationDate) desc limit 1")
    Optional<DonationHistory> findDonationHistoryByNic(@Param("donorNic") String donorNic);

    @Query("select dh from DonationHistory dh where dh.donorNic=?1")
    List<DonationHistory> findDonationByNic(String donorNic);

    @Query("select dh from DonationHistory dh where dh.donorNic=?1 order by date(donationDate) desc limit 1")
    DonationHistory findLatestDonation(String donorNic);

    @Modifying
    @Transactional
    @Query("update DonationHistory dh set dh.donationDate=?1, dh.quantity=?2 where dh.donorNic=?3")
    void updateDonorByNic(Date donationDate, float quantity, String donorNic);

    @Modifying
    @Transactional
    @Query("delete from DonationHistory dh where dh.donorNic=?1 and  dh.donationDate=?2")
    void deleteDonationByNicAndDate(String donorNic, Date donationDate);

    @Query("select dh from DonationHistory dh where dh.donorNic=?1 and dh.donationDate=?2")
    List<DonationHistory> getDonationHistoryByNicAndDate(String donorNic, Date donationDate);

    @Query("select dh from DonationHistory dh where dh.id=?1")
    List<DonationHistory> getDonationHistoryById(int id);

    @Query("select dh from DonationHistory dh where dh.donationDate=?1")
    List<DonationHistory> getDonationHistoryByDate(Date donationDate);

    // @Query("select dh from DonationHistory dh where dh.bloodType=?1 and
    // dh.donationDate=?2")
    // List<DonationHistory> getDonationsByBloodTypeAndDate(String bloodType, Date
    // donationDate);
}