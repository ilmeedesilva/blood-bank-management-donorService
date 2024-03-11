package com.bcn.donorService.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DonationHistoryRepository extends JpaRepository <DonationHistory, Integer> {
}