package com.processor.message.application;

import com.processor.message.domain.*;

import java.util.*;

/**
 * Application to receive, process and log {@link MessageNotification}s.
 *
 * @author victor
 */
public class MessageProcessingApplication {

    /** The queue containing Message Notifications to be processed. */
    public static Queue<MessageNotification> messageQueue;

    /** Map containing a record of all sales made during run-time. */
    public static Map<String, List<Sale>> salesRecord;

    /** If the messageQueue is still accepting new messages */
    public static boolean isAcceptingMessages;

    /** The count value to stop processing at */
    private static final int END_COUNT = 50;

    /** Track the adjustments made to each product */
    private Map<String, StringBuilder> productAdjustmentHistory;

    /** The {@link MessageProcessor} to process Message Notifications */
    private MessageProcessor messageProcessor;

    /** The {@link SalesReporter} to log records to the console */
    private SalesReporter salesReporter;

    /** The number of messages processed */
    private int messageProcessedCounter;

    /**
     * Default no argument constructor.
     */
    public MessageProcessingApplication(){

    }

    /**
     * Constructor
     */
    public MessageProcessingApplication(MessageProcessor messageProcessor, SalesReporter salesReporter){
        messageQueue = new LinkedList<MessageNotification>();
        salesRecord = new HashMap<String, List<Sale>>();
        productAdjustmentHistory = new HashMap<String, StringBuilder>();
        isAcceptingMessages = true;

        this.messageProcessor = messageProcessor;
        this.salesReporter = salesReporter;

        messageProcessedCounter = 0;

    }

    /**
     * Consumes and processes messages on the messageQueue.
     * Logs sales report after every 10 messages.
     * Logs adjustment report after every 50 messages.
     */
    public void start(){
        messageProcessedCounter = 0;

        System.out.println("Starting message processing cycle");

        while((messageProcessedCounter < END_COUNT) && null!=messageQueue.peek()){
            MessageNotification message = messageQueue.remove();

            if(message.getMessageType() == MessageType.TYPE_ONE){
                processTypeOneMessageNotification(message);
            }else if(message.getMessageType() == MessageType.TYPE_TWO){
                processTypeTwoMessageNotification(message);
            }else if(message.getMessageType() == MessageType.TYPE_THREE){
                processTypeThreeMessageNotification(message);
            }

            messageProcessedCounter++;

            //Log sales and values report after every 10 messages have been processed
            if(messageProcessedCounter%10==0){
                System.out.println(salesReporter.getTotalSalesAndValuesReport());
            }
        }

        //Log product adjustment report after 50 messages have been processed
        if(messageProcessedCounter == 50) {
            isAcceptingMessages = false;
            System.out.println("Pausing application...");
            System.out.println(salesReporter.getProductAdjustmentReport(productAdjustmentHistory));
        }

    }

    /**
     * Gets the productAdjustmentHistory
     *
     * @return productAdjustmentHistory
     */
    public Map<String, StringBuilder> getProductAdjustmentHistory(){
        return productAdjustmentHistory;
    }

    /**
     * Handler for Type One {@link MessageNotification}.
     *
     * @param messageNotification the messageNotification to process
     */
    private void processTypeOneMessageNotification(MessageNotification messageNotification){

        Sale sale = messageProcessor.processTypeOneMessageNotification(messageNotification);

        String productType = messageNotification.getProductType();

        //If Map already has Key, add the Sale
        if(salesRecord.containsKey(productType)){
            salesRecord.get(productType).add(sale);
        }else{
            //Add new Key and Sale
            List<Sale> salesListForProduct = new ArrayList<Sale>();
            salesListForProduct.add(sale);
            salesRecord.put(productType, salesListForProduct);
        }
    }

    /**
     * Handler for Type Two {@link MessageNotification}.
     *
     * @param messageNotification the messageNotification to process
     */
    private void processTypeTwoMessageNotification(MessageNotification messageNotification){
        List<Sale> sales = messageProcessor.processTypeTwoMessageNotification(messageNotification);

        String productType = messageNotification.getProductType();

        //If Map already has Key, merge all new Sales with existing Sales
        if(salesRecord.containsKey(productType)){
            salesRecord.get(productType).addAll(sales);
        }else{
            //Add new Key and Sale
            salesRecord.put(productType, sales);
        }
    }

    /**
     * Handler for Type Two {@link MessageNotification}.
     *
     * @param messageNotification the messageNotification to process
     */
    private void processTypeThreeMessageNotification(MessageNotification messageNotification){

        String productType = messageNotification.getProductType();

        //Check if product exists to modify
        if(null != salesRecord.get(productType)){
            List<Sale> existingSalesHistory = salesRecord.get(productType);
            String adjustmentMessage = messageProcessor.processTypeThreeMessageNotification(messageNotification, existingSalesHistory);

            if(null != productAdjustmentHistory.get(productType)) {
                productAdjustmentHistory.get(productType).append(adjustmentMessage);
            }else{
                StringBuilder log = new StringBuilder();
                log.append(adjustmentMessage);
                productAdjustmentHistory.put(productType, log);
            }
        }
    }


}
