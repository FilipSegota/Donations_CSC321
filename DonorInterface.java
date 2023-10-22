/*****************************************************************************
/* Author:      Filip Segota
/* Class:       CSC 321, Spring 2021
/* Assignment:  Donation, Phase 1
/* File:        Donor interface
/*****************************************************************************/
public interface DonorInterface {
    public void changeActive(Boolean in);
    public String getType();
    public int getDonorID();
    public Boolean getActiveState();
    public String getName();
    public String getEmail();
    public java.time.LocalDate getStartDate();
    public java.time.LocalDate getLastDate();
    public void addFamilyMember(Person in);
    public Donation[] getDonations();
    public void addDonation(Donation in);
    public String toString();
}
