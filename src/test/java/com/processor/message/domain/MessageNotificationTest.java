package com.processor.message.domain;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test file for {@link MessageNotification}.
 *
 * @author victor
 */
public class MessageNotificationTest {

    @Test
    public void testTypeOneConstructor(){
        String productType = "Apple";
        int price = 20;

        MessageNotification messageNotification = new MessageNotification(productType, price);

        Assert.assertEquals(MessageType.TYPE_ONE, messageNotification.getMessageType());
        Assert.assertEquals("Apple", messageNotification.getProductType());
        Assert.assertEquals(20, messageNotification.getPrice());
    }

    @Test
    public void testTypeTwoConstructor(){
        String productType = "Apple";
        int price = 20;
        int quantity = 500;

        MessageNotification messageNotification = new MessageNotification(productType, price, quantity);

        Assert.assertEquals(MessageType.TYPE_TWO, messageNotification.getMessageType());
        Assert.assertEquals("Apple", messageNotification.getProductType());
        Assert.assertEquals(20, messageNotification.getPrice());
        Assert.assertEquals(500, messageNotification.getQuantity());
    }

    @Test
    public void testTypeThreeConstructor(){
        String productType = "Apple";
        int price = 20;
        int quantity = 0;
        MessageOperation operation = MessageOperation.ADD;

        MessageNotification messageNotification = new MessageNotification(productType, price, quantity, operation);

        Assert.assertEquals(MessageType.TYPE_THREE, messageNotification.getMessageType());
        Assert.assertEquals("Apple", messageNotification.getProductType());
        Assert.assertEquals(20, messageNotification.getPrice());
        Assert.assertEquals(0, messageNotification.getQuantity());
    }

    @Test
    public void testToString(){
        String productType = "Apple";
        int price = 20;
        int quantity = 0;
        MessageOperation operation = MessageOperation.ADD;

        MessageNotification messageNotification = new MessageNotification(productType, price, quantity, operation);

        String expectedOutput = "MessageNotification{messageType=TYPE_THREE, operation=ADD, productType='Apple', price=20, quantity=0}";
        String actualOutput = messageNotification.toString();

        Assert.assertEquals(expectedOutput, actualOutput);
    }
}
