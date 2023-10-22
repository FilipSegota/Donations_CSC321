/*****************************************************************************
/* Author:      Micah Focht
/* Class:       CSC 321, Spring 2021
/* Assignment:  Donation, Phase 1
/* File:        Donation class
/*****************************************************************************/
import java.time.*;
import java.io.Serializable;
public class Donation implements DonationInterface, Serializable{
    private String type, description;
    private LocalDate date;
    private int value;
    private DonorInterface donor;
    public Donation(int valueIn, String typeIn, DonorInterface donorIn,
                         LocalDate dateIn, String descriptionIn){
        value = valueIn;
        type = typeIn;
        donor = donorIn;
        date = dateIn;
        description = descriptionIn;
    }
    public String toString(){
        return type +" "+ value +" "+ description +" "+ date;
    }
    public int getValue(){
        return value;
    }
    public String getType(){
        return type;
    }
    public LocalDate getDate(){
        return date;
    }
    public String getDescription(){
        return description;
    }
    public DonorInterface getDonor(){
        return donor;
    }
    public void setType(String type) {
        this.type = type;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setDonor(DonorInterface donor) {
        this.donor = donor;
    }
    public void setValue(int value) {
        this.value = value;
    }
}
