package com.bcn.donorService.data;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.sql.Date;

public class StockItems {

    private int id;


    private String category;


    private Date stockDate;


    private float aPositive;


    private float aNegative;


    private float bPositive;


    private float bNegative;


    private float abPositive;


    private float abNegative;


    private float oPositive;


    private float oNegative;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getStockDate() {
        return stockDate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setStockDate(Date stockDate) {
        this.stockDate = stockDate;
    }

    public float getaPositive() {
        return aPositive;
    }

    public void setaPositive(float aPositive) {
        this.aPositive = aPositive;
    }

    public float getaNegative() {
        return aNegative;
    }

    public void setaNegative(float aNegative) {
        this.aNegative = aNegative;
    }

    public float getbPositive() {
        return bPositive;
    }

    public void setbPositive(float bPositive) {
        this.bPositive = bPositive;
    }

    public float getbNegative() {
        return bNegative;
    }

    public void setbNegative(float bNegative) {
        this.bNegative = bNegative;
    }

    public float getAbPositive() {
        return abPositive;
    }

    public void setAbPositive(float abPositive) {
        this.abPositive = abPositive;
    }

    public float getAbNegative() {
        return abNegative;
    }

    public void setAbNegative(float abNegative) {
        this.abNegative = abNegative;
    }

    public float getoPositive() {
        return oPositive;
    }

    public void setoPositive(float oPositive) {
        this.oPositive = oPositive;
    }

    public float getoNegative() {
        return oNegative;
    }

    public void setoNegative(float oNegative) {
        this.oNegative = oNegative;
    }
}
