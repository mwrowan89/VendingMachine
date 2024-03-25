package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class VendingMachine {

    //    File newFile = new File("vendingmachine.csv");
    Map<String, List<Items>> inventoryMap = new HashMap<>();


    public void readInventoryFile (File newFile)  {
        try (Scanner fileScanner = new Scanner(newFile)) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
//            List<String> lines = Files.readAllLines(Paths.get(newFile));
                //for (String line : lines) {

                String[] parts = line.split(",");
                if (parts.length >= 2) {

                    String vendingMachineNumber = parts[0].trim();
                    //String inventoryItem = parts[1].trim();
                    Items inventoryItem = new Items("placeholder", 0.00);
                    List<Items> stock = new ArrayList<>();
                    stock.clear();
                    if(parts[3].equals("Duck")) {
                        inventoryItem = (Duck) new Duck(parts[1], Double.parseDouble(parts[2]));
                    }
                    else if(parts[3].equals("Penguin")) {
                        inventoryItem = (Penguin) new Penguin(parts[1], Double.parseDouble(parts[2]));
                    }
                    else if(parts[3].equals("Cat")) {
                        inventoryItem = (Cat) new Cat(parts[1], Double.parseDouble(parts[2]));
                    }
                    else if(parts[3].equals("Pony")) {
                        inventoryItem = (Pony) new Pony(parts[1], Double.parseDouble(parts[2]));
                    }
                    else {
                        System.out.println("Invalid Item!");
                    }
                    for(int i = 0; i < 6; i++) {
                        stock.add(inventoryItem);
                    }

                    inventoryMap.put(vendingMachineNumber, stock);
                } else {
                    System.out.println("Invalid line: " + line);
                }
            }
        }catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void vendItem(String productCode){
        inventoryMap.get(productCode);
        List<Items> vend = inventoryMap.get(productCode);
        vend.remove(0);
        inventoryMap.put(productCode,vend);
    }

    public Map<String, List<Items>> getInventoryMap() {
        return inventoryMap;
    }

}

