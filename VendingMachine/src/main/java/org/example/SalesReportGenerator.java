package org.example;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class SalesReportGenerator {

    public double generateSalesTotal(VendingMachine vm) {
        double salesTotal = 0.00;

        SimpleDateFormat dateAndTime = new SimpleDateFormat("MM-dd-yyyy_hh-mm-ss_a");
        String date = dateAndTime.format(new Date());
        File salesReportDirectory = new File("salesReports");
        if(!salesReportDirectory.exists()) {
            salesReportDirectory.mkdir();
        }
        String reportName = date + "_sales_report.log";
        File newSalesReport = new File(salesReportDirectory, reportName);
        if(!newSalesReport.exists()) {
            try {
                newSalesReport.createNewFile();
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        try(PrintWriter output = new PrintWriter(newSalesReport)) {
            for(Map.Entry<String, List<Items>> slot : vm.getInventoryMap().entrySet()) {
                int amountSold = 6 - slot.getValue().size();
                output.println(slot.getValue().get(0).getName() + "," + amountSold);
                salesTotal += slot.getValue().get(0).getCost() * amountSold;
            }

            output.println();
            output.println("SALES TOTAL: $" + String.format("%.2f", salesTotal));
        } catch (FileNotFoundException e) {

        }

        return salesTotal;
    }

}
