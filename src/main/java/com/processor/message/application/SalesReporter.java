package com.processor.message.application;

import com.processor.message.domain.Sale;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Generate reports of sales.
 *
 * @author victor
 */
public class SalesReporter {

    /**
     * Default no argument constructor
     */
    public SalesReporter(){
    }

    /**
     * Builds a report detailing the number of sales of each product and their total values.
     *
     * @return String with the number of sales of each product and their total values
     */
    public String getTotalSalesAndValuesReport(){
        StringBuilder report = new StringBuilder();
        String tempString;
        Set<String> productTypes = MessageProcessingApplication.salesRecord.keySet();

        System.out.println("[Log Report]");

        //Iterate through each product in the HashMap
        for(String productType : productTypes){

            List<Sale> productSales = MessageProcessingApplication.salesRecord.get(productType);
            int totalPrice = 0;

            for(Sale sale : productSales){
                totalPrice += sale.getPrice();
            }
            tempString = "Product Type: \'" + productType + "\', Total Number of Sales: " + productSales.size() + ", Total Price: " + totalPrice + "p\n";
            report.append(tempString);
        }

        return report.toString();
    }

    /**
     * Builds a report detailing the adjustment history of each product during run-time.
     *
     * @param productAdjustmentHistory map of product adjustment strings to build the report from
     * @return String with the history of adjustments
     */
    public String getProductAdjustmentReport(Map<String, StringBuilder> productAdjustmentHistory){
        StringBuilder report = new StringBuilder();
        String tempString;
        Set<String> products = productAdjustmentHistory.keySet();

        System.out.println("[Adjustment Report]");

        for(String productType : products){

            tempString = "Product Type: \'" + productType + "\': Adjustment History: " + productAdjustmentHistory.get(productType).toString() + ".\n";
            report.append(tempString);
        }

        return report.toString();
    }
}
