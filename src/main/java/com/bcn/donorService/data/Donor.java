package com.bcn.donorService.data;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.sql.Date;

@Entity
@Table(name = "donor")
public class Donor {

    @Id
    @Column(name = "donor_nic")
    private String donorNic;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "blood_type")
    private String bloodType;

    @Column(name = "birthday")
    private Date birthday;

    @Column(name = "street_no")
    private String streetNo;

    @Column(name = "street")
    private String street;

    @Column(name = "city")
    private String city;

    @Column(name = "contact_no")
    private Long contactNo;

    @Column(name = "emergency_contact_no")
    private Long emergencyContactNo;

    @Column(name = "weight")
    private float weight;

    @Column(name = "gender")
    private String gender;

    @Column(name = "units")
    private float units;

    public String getDonorNic() {
        return donorNic;
    }

    public void setDonorNic(String donorNic) {
        this.donorNic = donorNic;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getStreetNo() {
        return streetNo;
    }

    public void setStreetNo(String streetNo) {
        this.streetNo = streetNo;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Long getContactNo() {
        return contactNo;
    }

    public void setContactNo(Long contactNo) {
        this.contactNo = contactNo;
    }

    public Long getEmergencyContactNo() {
        return emergencyContactNo;
    }

    public void setEmergencyContactNo(Long emergencyContactNo) {
        this.emergencyContactNo = emergencyContactNo;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public float getUnits() {
        return units;
    }

    public void setUnits(float units) {
        this.units = units;
    }
}
