package org.example;


import java.io.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Transactions {

    private double balance = 0.00;

    /*public void purchase(Map<String, List<Items>> inventory){
        Scanner userInput = new Scanner(System.in);
        File transactionLog = new File("Vending.log");
        if(!transactionLog.exists()){
            try {
                transactionLog.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        String selection;

        do {
            System.out.println("(1) Feed Money");
            System.out.println("(2) Select Product");
            System.out.println("(3) Finish Transaction");
            selection = userInput.nextLine();

            if(selection.equalsIgnoreCase("1")){
                String addMoreMoney = "";
                do {
                    System.out.println("Current Money Provided: " + String.format("%.2f", balance));
                    System.out.println("(1) $1.00");
                    System.out.println("(2) $5.00");
                    System.out.println("(3) $10.00");
                    String dollarAmount = userInput.nextLine();
                    double amount = 0.00;
                    if (dollarAmount.equalsIgnoreCase("1")) {
                        amount = 1.0;
                        balance = balance + 1.0;
                    } else if (dollarAmount.equalsIgnoreCase("2")) {
                        amount = 5.0;
                        balance = balance + 5.0;
                    } else if (dollarAmount.equalsIgnoreCase("3")) {
                        amount = 10.0;
                        balance = balance + 10.0;
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
                Menu menu = new Menu();
                //Map<String, List<Items>> inventory = menu.vm.getInventoryMap();
                menu.displayVendingMachineInventory();

                boolean containsEntry = false;
                boolean isSoldOut = false;
                String productSelection;
                Items selectedItem = new Items("placeholder", 0.00);
                do {
                    System.out.print("Enter product code: ");
                    productSelection = userInput.nextLine();
                    for (Map.Entry<String, List<Items>> item : inventory.entrySet()) {
                        if (productSelection.equalsIgnoreCase(item.getKey())) {
                            if(item.getValue().size() <= 0){
                                isSoldOut = true;
                            }
                            else {
                                selectedItem = item.getValue().get(0);
                                List<Items> purchased = item.getValue();
                                purchased.remove(0);
                                item.setValue(purchased);
                                System.out.println(item.getValue().size());
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
                    System.out.println("Insufficient Funds.");
                }
                else {
                    balance -= selectedItem.getCost();
                    System.out.println("Dispensing " + selectedItem.getName() + " for $" + selectedItem.getCost() + ". Balance remaining: $" + String.format("%.2f", balance));
                    System.out.println(selectedItem.getMessage());


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
        while (balance >= 0.25) {
            balance -= 0.25;
            quarterCount++;
        }
        while (balance >= 0.10) {
            balance -= 0.10;
            dimeCount++;
        }
        while (balance >= 0.05) {
            balance -= 0.05;
            nickelCount++;
        }

        try (PrintWriter writer = new PrintWriter(new FileOutputStream(transactionLog,true))){
            SimpleDateFormat dateAndTime = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
            String date = dateAndTime.format(new Date());
            writer.println(date + " GIVE CHANGE: $" + String.format("%.2f", totalChange) + " $" + String.format("%.2f", balance));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Your change is: " + quarterCount + " quarters, " + dimeCount + " dimes, and " + nickelCount + " nickels.");

        Menu menu = new Menu();
        menu.run();
    }*/
}
