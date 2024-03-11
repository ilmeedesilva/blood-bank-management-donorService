package com.bcn.donorService.data;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name = "donation_history")
public class DonationHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "donor_nic")
    private String donorNic;

//    @ManyToOne
//    @JoinColumn(name = "donor_nic", referencedColumnName = "donor_nic", insertable = false, updatable = false)
//    private Donor donor;

    @Column(name = "donation_date")
    private Date donationDate;

    @Column(name = "quantity")
    private float quantity;

    @Column(name = "recipient_type")
    private String recipientType;

    @Column(name = "recipient_id")
    private int recipientId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDonorNic() {
        return donorNic;
    }

    public void setDonorNic(String donorNic) {
        this.donorNic = donorNic;
    }
//    public Donor getDonor() {
//        return donor;
//    }
//
//    public void setDonor(Donor donor) {
//        this.donor = donor;
//    }

    public Date getDonationDate() {
        return donationDate;
    }

    public void setDonationDate(Date donationDate) {
        this.donationDate = donationDate;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public String getRecipientType() {
        return recipientType;
    }

    public void setRecipientType(String recipientType) {
        this.recipientType = recipientType;
    }

    public int getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(int recipientId) {
        this.recipientId = recipientId;
    }
}
