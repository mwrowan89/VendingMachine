package org.example;


import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Menu {
    Scanner userInput = new Scanner(System.in);
    File newFile = new File("vendingmachine.csv");
    VendingMachine vm = new VendingMachine();
    private double balance = 0.00;

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_STOCKED_GREEN = "\u001B[38;5;148m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[48;5;46m";


    public void run() {
        System.out.println("***************************************");
        System.out.println("Welcome to the Virtual Vending Machine!");
        System.out.println("***************************************");
        String action = "";
//        File newFile = new File("vendingmachine.csv");
//        VendingMachine vm = new VendingMachine();
        vm.readInventoryFile(newFile);

        do {
            System.out.println();
            System.out.println("(1) Display Vending Machine Items");
            System.out.println("(2) Purchase");
            System.out.println("(3) Exit");

            System.out.print("Selection: ");
            action = userInput.nextLine();

            if(action.equalsIgnoreCase("1")){
                Map<String, List<Items>> inventory = vm.getInventoryMap();
                displayVendingMachineInventory();

            } else if(action.equalsIgnoreCase("2")){
//                Transactions transactions = new Transactions();
//                transactions.purchase(vm.getInventoryMap());
                purchase();

            } else if (action.equalsIgnoreCase("3")){

            } else if(action.equalsIgnoreCase("4")){
                SalesReportGenerator srg = new SalesReportGenerator();
                srg.generateSalesTotal(vm);
            } else {
                System.out.println("Input not valid, please try again.");
            }
        } while (!action.equalsIgnoreCase("3"));

        System.out.println("Thank you for using Vending Machine!!");

    }

    public void displayVendingMachineInventory() {
        for(Map.Entry<String, List<Items>> slot : vm.getInventoryMap().entrySet()) {
            if(slot.getValue().size() <= 1) {
                System.out.println(slot.getKey() + ":" + ANSI_RED +  " SOLD OUT" + ANSI_RESET);
            }
            else {
                //int spacer = 20 - slot.getValue().get(0).getName().length();
                //String.format("%" + spacer + "0.f", " ") +
                System.out.println(slot.getKey() + ": " + slot.getValue().get(0).getName() + " cost: $" + String.format("%.2f",slot.getValue().get(0).getCost()) + (slot.getValue().size() - 1 == 1 ? ANSI_YELLOW:ANSI_STOCKED_GREEN) + " amount remaining: " + (slot.getValue().size() - 1) + ANSI_RESET);
            }
            //System.out.println(slot.getKey() + ": " + slot.getValue().get(0).getName() + " cost: " + String.format("%.2f",slot.getValue().get(0).getCost()) + " amount left: " + slot.getValue().size());
        }
    }

    public void purchase() {
        Scanner userInput = new Scanner(System.in);
        File transactionLog = new File("Vending.log");
        Map<String, List<Items>> inventory = vm.getInventoryMap();
        if(!transactionLog.exists()){
            try {
                transactionLog.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        String selection;

        do {
            System.out.println();
            System.out.println("Current Money Provided: $" + String.format("%.2f", balance));
            System.out.println("(1) Feed Money");
            System.out.println("(2) Select Product");
            System.out.println("(3) Finish Transaction");
            System.out.print("Selection: ");

            selection = userInput.nextLine();

            if(selection.equalsIgnoreCase("1")){
                String addMoreMoney = "";
                do {
                    System.out.println();
                    System.out.println("Current Money Provided: $" + String.format("%.2f", balance));
                    System.out.println("(1) $1.00");
                    System.out.println("(2) $5.00");
                    System.out.println("(3) $10.00");
                    System.out.print("Selection: ");

                    String dollarAmount = userInput.nextLine();
                    double amount = 0.00;
                    if (dollarAmount.equalsIgnoreCase("1")) {
                        amount = 1.0;
                        balance = checkBalance(1,balance);
                    } else if (dollarAmount.equalsIgnoreCase("2")) {
                        amount = 5.0;
                        balance = checkBalance(5,balance);
                    } else if (dollarAmount.equalsIgnoreCase("3")) {
                        amount = 10.0;
                        balance = checkBalance(10,balance);
                    } else {
                        System.out.println("Invalid selection, try again.");
                    }
                    try (PrintWriter writer = new PrintWriter(new FileOutputStream(transactionLog,true))){
                        SimpleDateFormat dateAndTime = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
                        String date = dateAndTime.format(new Date());
                        writer.println(date + " " + "FEED MONEY: $" + String.format("%.2f", amount) + " $" + String.format("%.2f", balance));
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("Current money in machine: S" + String.format("%.2f", balance));
                    System.out.print("Do you wish to add more money? (Y/N)");
                    addMoreMoney = userInput.nextLine();
                } while (addMoreMoney.equalsIgnoreCase("Y"));


            } else if(selection.equalsIgnoreCase("2")){
                System.out.println("Please select a product using the product code from the list below.");
                //Map<String, List<Items>> inventory = menu.vm.getInventoryMap();
                displayVendingMachineInventory();

                boolean containsEntry = false;
                boolean isSoldOut = false;
                String productSelection;
                Items selectedItem = new Items("placeholder", 0.00);
                do {
                    System.out.println();
                    System.out.print("Enter product code: ");
                    productSelection = userInput.nextLine();
                    for (Map.Entry<String, List<Items>> item : inventory.entrySet()) {
                        if (productSelection.equalsIgnoreCase(item.getKey())) {
                            if(item.getValue().size() <= 1){
                                isSoldOut = true;
                            }
                            else {
                                if(balance > selectedItem.getCost()){
                                    selectedItem = item.getValue().get(0);
                                    List<Items> purchased = item.getValue();
                                    purchased.remove(0);
                                    item.setValue(purchased);
                                    isSoldOut = false;
                                } else {
                                    System.out.println("Insufficient funds, please add money.");
                                }
                            }
                            containsEntry = true;
                        }
                    } if(!containsEntry){
                        System.out.println("Invalid selection please try again.");
                    }
                    if(isSoldOut){
                        System.out.println("Item is sold out, please select a different item.");
                    }
                } while (!containsEntry || isSoldOut);

                if(balance < selectedItem.getCost()) {
                    System.out.println("Insufficient funds, please add money.");
                }
                else {
                    if(balance >= 0.9){
                        balance -= selectedItem.getCost();
                        System.out.println("Dispensing " + selectedItem.getName() + " for $" + String.format("%.2f", selectedItem.getCost()) + ". Balance remaining: $" + String.format("%.2f", balance));
                        System.out.println(selectedItem.getMessage());
                    }


                    try (PrintWriter writer = new PrintWriter(new FileOutputStream(transactionLog,true))){
                        SimpleDateFormat dateAndTime = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
                        String date = dateAndTime.format(new Date());
                        writer.println(date + " " + selectedItem.getName() + " " + productSelection + " $" + String.format("%.2f", selectedItem.getCost()) + " $" + String.format("%.2f", balance));
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }


            } else if (selection.equalsIgnoreCase("3")){

            } else {
                System.out.println("Invalid selection, try again");
            }
        }
        while (!selection.equalsIgnoreCase("3"));
        double totalChange = balance;

        int quarterCount = 0;
        int dimeCount = 0;
        int nickelCount = 0;
        while (balance >= 0.24) {
            quarterCount++;
            balance -= 0.25;
        }
        while (balance >= 0.09) {
            dimeCount++;
            balance -= 0.10;
        }
        while (balance >= 0.04) {
            nickelCount++;
            balance -= 0.05;
        }

        try (PrintWriter writer = new PrintWriter(new FileOutputStream(transactionLog,true))){
            SimpleDateFormat dateAndTime = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
            String date = dateAndTime.format(new Date());
            writer.println(date + " GIVE CHANGE: $" + String.format("%.2f", totalChange) + " $" + String.format("%.2f", Math.abs(balance)));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        String changeStatement = "Your change is: ";
        if(quarterCount == 0 && dimeCount == 0 && nickelCount == 0) {
            changeStatement = "No change needed!";
        }
        else {
            if(quarterCount > 0) {
                changeStatement += quarterCount + " quarter" + ((quarterCount == 1) ? " ":"s ");
            }
            if(dimeCount > 0) {
                changeStatement += dimeCount + " dime" + (dimeCount == 1 ? " ":"s ");
            }
            if(nickelCount > 0) {
                changeStatement += nickelCount + " nickel" + (nickelCount == 1 ? " ":"s ");
            }
        }

        System.out.println(changeStatement);
    }

    public VendingMachine getVm() {
        return vm;
    }

    public double checkBalance(double moneyAdded, double startingMoney){
        return moneyAdded + startingMoney;
    }
//    public double correctChangeGiven(double changeGiven, double remainingBalance){
//        return
//    }
}