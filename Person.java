/*****************************************************************************
/* Author:      Filip Segota
/* Class:       CSC 321, Spring 2021
/* Assignment:  Donation, Phase 1
/* File:        Person class
/*****************************************************************************/
public class Person implements java.io.Serializable {
    String name, email;
    Boolean forProfit, business;
    
    public Person(String nameIn, String emailIn, Boolean forProfitIn,
                  Boolean businessIn){
        name = nameIn;
        email = emailIn;
        forProfit = forProfitIn;
        business = businessIn;
    }
    public void changeName(String nameIn){
        name = nameIn;
    }
    public void changeEmail(String emailIn){
        email = emailIn;
    }
    public String getEmail() {
        return email;
    }
    public String getName() {
        return name;
    }
    public Boolean isBusiness() {
        return business;
    }
    public Boolean isForProfit() {
        return forProfit;
    }
    public boolean isEqual(Person p) {
        if(this.name.compareTo(p.getName()) == 0) {
            return true;
        }
        else {
            return false;
        }
    }
}
