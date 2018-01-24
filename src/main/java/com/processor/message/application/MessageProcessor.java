package com.processor.message.application;

import com.processor.message.domain.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Processes a {@link MessageNotification}.
 *
 * @author victor
 */
public class MessageProcessor {

    /**
     * Default no argument constructor
     */
    public MessageProcessor(){
    }

    /**
     * Type One MessageNotification contains the details of 1 sale.
     * Convert the MessageNotification into a Sale record
     *
     * @param message the message to be processed
     * @return Sale containing the productType and price it was sold for
     */
    public Sale processTypeOneMessageNotification(MessageNotification message){
        return new Sale(message.getProductType(), message.getPrice());
    }

    /**
     * Type Two MessageNotification contains the details of a sale, and the number of occurrences of that sale
     * Convert the MessageNotification into a List of Sale records
     *
     * @param message the message to be processed
     * @return List of all Sales
     */
    public List<Sale> processTypeTwoMessageNotification(MessageNotification message){

        List<Sale> saleRecords = new ArrayList<Sale>();

        String productType = message.getProductType();
        int price = message.getPrice();

        for(int i = 0; i<message.getQuantity(); i++){
            saleRecords.add(new Sale(productType, price));
        }

        return saleRecords;
    }

    /**
     * Type Three MessageNotification contains the details of a sale and an adjustment operation to be
     * applied to all stored sales of this product type.
     *
     * Use the operation provided in the MessageNotification and adjust the prices of all Sales.
     *
     * @param message the message to be processed
     * @param recordedSalesList a handle to the list of Sales to be adjusted
     * @return log of the adjustment made to sales
     */
    public String processTypeThreeMessageNotification(MessageNotification message, List<Sale> recordedSalesList){

        MessageOperation operation = message.getOperation();
        int newPrice = message.getPrice();
        int updatedPrice;
        String adjustmentLog = "";

        if(operation==MessageOperation.ADD){
            adjustmentLog = ", +" + newPrice + "p";
        }else if(operation==MessageOperation.SUBTRACT) {
            adjustmentLog = ", -" + newPrice + "p";
        }else if(operation==MessageOperation.MULTIPLY){
            adjustmentLog = ", *" + newPrice + "p";
        }

        for(Sale sale : recordedSalesList){

            if(operation==MessageOperation.ADD){
                updatedPrice = sale.getPrice() + newPrice;
            }else if(operation==MessageOperation.SUBTRACT) {
                updatedPrice = sale.getPrice() - newPrice;
            }else if(operation==MessageOperation.MULTIPLY){
                updatedPrice = sale.getPrice() * newPrice;
            }else {
                updatedPrice = sale.getPrice();
                System.out.println("Unrecognized Operation: \'" + operation + "\'.");
            }

            sale.setPrice(updatedPrice);
        }

        return adjustmentLog;
    }

}
