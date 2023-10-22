/*****************************************************************************
/* Author:      Micah Focht
/* Class:       CSC 321, Spring 2021
/* Assignment:  Donation, Phase 1
/* File:        Driver
/*****************************************************************************/
import java.time.*;
import java.util.*;
import java.io.*;
public class Driver implements Serializable{
    private static Donor[] donors;
    private static int location;
    
    public static void main(String[] args){
        Scanner in;
        Boolean running; //State for main loop
        int selection;//temporary variable for loop
        int count; //temporary variable for calculating total
        Donor temp;   //temporary variable for holding donor
        String input; //temporary variable getting active correction
        in = new Scanner(System.in);
        running = true;
        try{
            load();
        }catch(IOException e){
            System.out.println("IOException");
            System.out.println(e.getMessage());
            donors = new Donor[10000];
        }catch(ClassNotFoundException c){
            System.out.println("ClassNotFoundException");
            donors = new Donor[10000];
        }
        while (running){ //Main Loop
            clear();
            System.out.println("Welcome!\nWhat would you like to do?");
            System.out.println("1) Add a new Donor\n2) Add a new Donation");
            System.out.println("3) Get Total for Donor");
            System.out.print("4) Get Active status donor\n5) Set donor ");
            System.out.println("active status\n6) List a donor's donations");
            System.out.println("7) List Donations from a start date\n8) Exit");
            selection = 0;
            try{
                selection = in.nextInt();
                in.nextLine();
                clear();
            }
            catch (Exception e) {
                clear();
                System.out.println("Please enter a numerical selection.");
                System.out.println("Press Enter to continue.");
                in.nextLine();
                in.nextLine();
                clear();
            }
            if(selection == 1){
                newDonor(in);
            }else if (selection == 2){
                newDonation(in);
            }else if (selection == 3){
                count = 0;
                temp = getDonor(in);
                for (int i = 0; i < temp.getIndex(); i++){
                    count += temp.getDonations()[i].getValue();
                }
                System.out.println("The donor has donated " + count);
                in.nextLine();
            }else if (selection == 4){
                temp = getDonor(in);
                System.out.println(temp.getActiveState());
                in.nextLine();
            }else if (selection == 5){
                temp = getDonor(in);
                System.out.println("Set donor active or inactive");
                input = in.nextLine();
                if (input.equals("active")){
                    temp.changeActive(true);
                }else {
                temp.changeActive(false);
                }
            }else if (selection == 6){
                System.out.println("Donations from donor:");
                temp = getDonor(in);
                for (int i = 0; i < temp.getIndex(); i++){
                    System.out.println(temp.getDonations()[i].toString());
                }
                in.nextLine();
            }else if (selection == 7){
                LocalDate min;
                temp = getDonor(in);
                System.out.println("What is the start date? 2020-12-01");
                min = LocalDate.parse(in.nextLine());
                System.out.println("Donations from donor starting " + min);
                for (int i = 0; i < temp.getIndex(); i++){
                    if(temp.getDonations()[i].getDate().compareTo(min) > 1){
                        System.out.println(temp.getDonations()[i].toString());
                    }
                }
                in.nextLine();
            }else if (selection == 8){
                running = false;
                try{
                    save();
                }catch(IOException e){
                    return;
                }
            }else{
                clear();
            }
        }
    }
    private static void newDonation(Scanner in){
        String type, description,temp;
        int value;
        int donor;
        LocalDate date;
        Donation donate;
        System.out.println("How would you like to select donor?\n1) By name");
        System.out.println("2) By ID");
        if (in.nextInt() == 2){
            System.out.println("What is the donor's ID?");
            donor = in.nextInt();
            in.nextLine();
        }else{
            System.out.println("What is the donor's name? ");
            in.nextLine();
            donor = getByName(in.nextLine());
        }
        System.out.println("What is the value of the donation?");
        value = in.nextInt();
        in.nextLine();
        System.out.println("What is the type of donation?");
        type = in.nextLine();
        System.out.println("When was the donation made?");
        System.out.println("Either 2020-12-01 or TODAY.");
        temp = in.nextLine();
        if (temp.equals("TODAY")){
            date = LocalDate.now();
        }else{
            date = LocalDate.parse(temp);
        }
        System.out.println("Please enter a brief description.");
        description = in.nextLine();
        donate = new Donation(value,type,getByID(donor),date,description);
        getByID(donor).addDonation(donate);
        System.out.println();
    }
    private static void newDonor(Scanner in){
        String type,nameIn,emailIn;
        Boolean business, forProfit;
        Donor donor;
        System.out.println("What is the Donor's name?");
        nameIn = in.nextLine();
        System.out.println("What is the donor's email?");
        emailIn = in.nextLine();
        System.out.println("Is the donor a business, individual, or family?");
        type = in.nextLine();
        if (type.equals("Business")){
            business = true;
            System.out.println("Is the Business for profit?\n Yes or No");
            if (in.nextLine().equals("Yes")) forProfit = true;
            else forProfit = false;
        }else{
            business = false;
            forProfit = false;
        }
        donor = new Donor(type, true, nameIn, emailIn, business, forProfit);
        donors[location] = donor;
        System.out.println("The Donor's ID is " + location);
        System.out.println("Press enter to continue");
        in.nextLine();
        location++;
    }
    private static Donor getByID(int id){
        //Find donor by ID
        return donors[id];
    }
    private static Donor getDonor(Scanner in){
        int id;
        System.out.println("How would you like to select donor?\n1) By name");
        System.out.println("2) By ID");
            if (in.nextInt() == 2){
                System.out.println("What is the donor's ID?");
                id = in.nextInt();
                in.nextLine();
            }else{
                in.nextLine();
                System.out.println("What is the donor's name? ");
                id = getByName(in.nextLine());
            }
        return getByID(id);
    }
    private static int getByName(String name){
        //Find donor by Name
        for (int i=0; i< location; i++){
            if (donors[i].getName().equals(name)){
                return i;
            }
        }
        return -1;
    }
    private static void clear(){
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
    private static void save() throws IOException{
        try{
            FileOutputStream outFile;
            ObjectOutputStream out;
            outFile = new FileOutputStream("data.drd");
            out = new ObjectOutputStream(outFile);
            out.writeObject(donors);
            out.close();
            outFile.close();
        }catch(IOException e){
            return;
        }
    }
    private static void load() throws IOException, ClassNotFoundException{
        try{
            FileInputStream inFile;
            ObjectInputStream in;
            inFile = new FileInputStream("data.drd");
            in = new ObjectInputStream(inFile);
            donors = (Donor[]) in.readObject();
            in.close();
            inFile.close();
        }catch(IOException e){
            System.out.println("IOException");
            System.out.println(e.getMessage());
            donors = new Donor[10000];
        }catch(ClassNotFoundException c){
            System.out.println("ClassNotFoundException");
        }
    }
}
