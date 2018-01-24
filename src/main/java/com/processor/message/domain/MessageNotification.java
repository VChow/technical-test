package com.processor.message.domain;

/**
 * Encapsulates a message notification containing information of a sale
 *
 * @author victor
 */
public class MessageNotification {

    /** The {@link MessageType} of the message */
    private MessageType messageType;

    /** The {@link MessageOperation} of the message */
    private MessageOperation operation;

    /** The productType of the product. */
    private String productType;

    /** The price of the product (pence) */
    private int price;

    /** The quantity of the product */
    private int quantity;

    /**
     * Default no argument constructor
     */
    public MessageNotification(){

    }

    /** Constructor for Type 1 message notification */
    public MessageNotification(String productType, int price){
        this.messageType = MessageType.TYPE_ONE;
        this.productType = productType;
        this.price = price;
    }

    /** Constructor for Type 2 message notification */
    public MessageNotification(String productType, int price, int quantity){
        this.messageType = MessageType.TYPE_TWO;
        this.productType = productType;
        this.price = price;
        this.quantity = quantity;
    }

    /** Constructor for Type 3 message notification */
    public MessageNotification(String productType, int price, int quantity, MessageOperation operation){
        this.messageType = MessageType.TYPE_THREE;
        this.productType = productType;
        this.price = price;
        this.quantity = quantity;
        this.operation = operation;
    }

    /**
     * Gets the messageType
     *
     * @return messageType
     */
    public MessageType getMessageType() {
        return messageType;
    }

    /**
     * Gets the operation
     *
     * @return operation
     */
    public MessageOperation getOperation() {
        return operation;
    }

    /**
     * Gets the productType
     *
     * @return productType
     */
    public String getProductType() {
        return productType;
    }

    /**
     * Gets the price
     *
     * @return price
     */
    public int getPrice() {
        return price;
    }

    /**
     * Gets the quantity
     *
     * @return quantity
     */
    public int getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return "MessageNotification{" +
                "messageType=" + messageType +
                ", operation=" + operation +
                ", productType='" + productType + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }
}
