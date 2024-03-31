package com.bcn.donorService.data;

import java.sql.Date;

public class Stock {

    private String donorNic;

    private Date donationDate;

    private String bloodType;

    public String getDonorNic() {
        return donorNic;
    }

    public void setDonorNic(String donorNic) {
        this.donorNic = donorNic;
    }

    public Date getDonationDate() {
        return donationDate;
    }

    public void setDonationDate(Date donationDate) {
        this.donationDate = donationDate;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    private float quantity;



}
