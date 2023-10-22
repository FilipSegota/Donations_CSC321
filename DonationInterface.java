/*****************************************************************************
/* Author:      Micah Focht
/* Class:       CSC 321, Spring 2021
/* Assignment:  Donation, Phase 1
/* File:        Donation interface
/*****************************************************************************/
public interface DonationInterface {
    public String toString();
    public int getValue();
    public String getType();
    public java.time.LocalDate getDate();
    public String getDescription();
    public DonorInterface getDonor();
}
