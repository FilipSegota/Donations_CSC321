/*****************************************************************************
/* Author:      Filip Segota
/* Class:       CSC 321, Spring 2021
/* Assignment:  Donation, Phase 1
/* File:        Donor classz
/*****************************************************************************/
import java.time.*;
import java.io.Serializable;
public class Donor extends Person implements DonorInterface, Serializable{
    String type;
    Boolean active;
    int id, index, indexFam;
    Donation[] donations;
    Person[] family;
    
    public Donor(String typeIn, Boolean activeIn, String nameIn,
                 String emailIn, Boolean businessIn,
                 Boolean forProfitIn) {
        super(nameIn, emailIn, forProfitIn, businessIn);
        donations = new Donation[10000];
        family = new Person[100];
        index = 0;
        indexFam = 0;
        type = typeIn;
        active = activeIn;
    }

    public void changeActive(Boolean in) {
        active = in;
    }

    public String getType() {
        return type;
    }

    public int getDonorID() {
        return id;
    }

    public Boolean getActiveState() {
        return active;
    }

    public LocalDate getStartDate() {
        if (donations[0] == null){
            return null;
        }
        return donations[0].getDate();
    }

    public LocalDate getLastDate() {
        if (donations[0] == null){
            return null;
        }
        return donations[index-1].getDate();
    }

    public void addFamilyMember(Person in) {
        if (type.equals("Family")){
            family[indexFam] = in;
            indexFam += 1;
        }
    }

    public Donation[] getDonations() {
        return donations;
    }

    public void addDonation(Donation in) {
        donations[index] = in;
        index++;
    }
    public int getIndex(){
        return index;
    }
    public int getIndexFam() {
        return indexFam;
    }
}
