package com.processor.message.application;

import com.processor.message.domain.*;

import java.util.Random;

/**
 * Simulates an external source providing Message Notifications to the MessageQueue.
 *
 * @author victor
 */
public class MessageProducer {

    /** Maximum value for generating indexes */
    private static final int MAX_VALUE = 3;

    /** Number of messages to seed onto the messageQueue */
    private static final int MESSAGE_COUNT = 60;

    /** Array of prices to be used for generating {@link MessageNotification}s */
    private int[] prices = new int[]{1, 2, 5};

    /** Array of quantities to be used for generating {@link MessageNotification}s */
    private int[] quantities = new int[]{1, 2, 3};

    /** Array of productTypes to be used for generating {@link MessageNotification}s */
    private String[] productTypes = new String[]{"Apple", "Banana", "Orange"};

    /** Random number generator */
    private Random randomNumberGenerator;

    /**
     * Default no argument constructor
     */
    public MessageProducer(){

    }

    public void generateAndSendMessageNotifications(){
        randomNumberGenerator = new Random();

        if(MessageProcessingApplication.isAcceptingMessages){

            for(int i=0; i<MESSAGE_COUNT;i++) {
                int messageNotificationType = randomNumberGenerator.nextInt(MAX_VALUE);

                switch (messageNotificationType) {
                    case 0:
                        generateAndSendMessageNotificationTypeOne();
                        break;

                    case 1:
                        generateAndSendMessageNotificationTypeTwo();
                        break;

                    case 2:
                        generateAndSendMessageNotificationTypeThree();
                        break;
                }
            }
        }
    }

    /**
     * Generates a TypeOne {@link MessageNotification} with randomized data.
     */
    private void generateAndSendMessageNotificationTypeOne(){
        int productTypeIndex = randomNumberGenerator.nextInt(MAX_VALUE);
        int priceIndex = randomNumberGenerator.nextInt(MAX_VALUE);

        MessageNotification messageNotification = new MessageNotification(productTypes[productTypeIndex], prices[priceIndex]);
        MessageProcessingApplication.messageQueue.add(messageNotification);
    }

    /**
     * Generates a TypeTwo {@link MessageNotification} with randomized data.
     */
    private void generateAndSendMessageNotificationTypeTwo(){
        int productTypeIndex = randomNumberGenerator.nextInt(MAX_VALUE);
        int priceIndex = randomNumberGenerator.nextInt(MAX_VALUE);
        int quantityIndex = randomNumberGenerator.nextInt(MAX_VALUE);

        MessageNotification messageNotification = new MessageNotification(productTypes[productTypeIndex], prices[priceIndex], quantities[quantityIndex]);
        MessageProcessingApplication.messageQueue.add(messageNotification);
    }

    /**
     * Generates a TypeThree {@link MessageNotification} with randomized data.
     */
    private void generateAndSendMessageNotificationTypeThree(){
        int productTypeIndex = randomNumberGenerator.nextInt(MAX_VALUE);
        int priceIndex = randomNumberGenerator.nextInt(MAX_VALUE);
        int quantityIndex = randomNumberGenerator.nextInt(MAX_VALUE);
        int messageOperationIndex = randomNumberGenerator.nextInt(MAX_VALUE);

        MessageOperation messageOperation = null;

        switch(messageOperationIndex){
            case 0:
                messageOperation = MessageOperation.ADD;
                break;
            case 1:
                messageOperation = MessageOperation.SUBTRACT;
                break;
            case 2:
                messageOperation = MessageOperation.MULTIPLY;
                break;
        }

        MessageNotification messageNotification = new MessageNotification(productTypes[productTypeIndex], prices[priceIndex], quantities[quantityIndex], messageOperation);
        MessageProcessingApplication.messageQueue.add(messageNotification);
    }
}
