package com.processor.message.application;

import com.processor.message.domain.MessageNotification;
import com.processor.message.domain.MessageOperation;
import com.processor.message.domain.Sale;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Test file for {@link MessageProcessor}.
 *
 * @author victor
 */
public class MessageProcessorTest {

    private static List<Sale> sales = new ArrayList<Sale>();

    @Before
    public void setup(){
        sales.clear();
    }

    @Test
    public void testProcessTypeOneMessageNotificationReturnsSale(){
        String name = "apples";
        int cost = 20;

        MessageNotification message = new MessageNotification(name, cost);

        MessageProcessor messageProcessor = new MessageProcessor();

        Sale saleRecord = messageProcessor.processTypeOneMessageNotification(message);

        Assert.assertEquals(name, saleRecord.getProductType());
        Assert.assertEquals(cost, saleRecord.getPrice());
    }

    @Test
    public void testProcessTypeTwoMessageNotificationReturnsListOfSales(){
        String name = "apples";
        int cost = 20;
        int quantity = 3;

        MessageNotification message = new MessageNotification(name, cost, quantity);

        MessageProcessor messageProcessor = new MessageProcessor();

        List<Sale> saleRecords = messageProcessor.processTypeTwoMessageNotification(message);

        Assert.assertEquals(3, saleRecords.size());

        Assert.assertEquals("apples", saleRecords.get(0).getProductType());
        Assert.assertEquals(20, saleRecords.get(0).getPrice());

        Assert.assertEquals("apples", saleRecords.get(1).getProductType());
        Assert.assertEquals(20, saleRecords.get(1).getPrice());

        Assert.assertEquals("apples", saleRecords.get(2).getProductType());
        Assert.assertEquals(20, saleRecords.get(2).getPrice());
    }

    @Test
    public void testProcessTypeThreeMessageNotificationForAddOperation(){
        String name = "apples";
        int cost = 10;
        int quantity = 3;
        MessageOperation operation = MessageOperation.ADD;

        sales.add(new Sale("apples", 20));
        sales.add(new Sale("apples", 10));

        MessageNotification message = new MessageNotification(name, cost, quantity, operation);

        MessageProcessor messageProcessor = new MessageProcessor();

        String expectedResult = ", +10p";
        String actualResult = messageProcessor.processTypeThreeMessageNotification(message, sales);

        Assert.assertEquals(expectedResult, actualResult);
        Assert.assertEquals(30, sales.get(0).getPrice());
        Assert.assertEquals(20, sales.get(1).getPrice());
    }

    @Test
    public void testProcessTypeThreeMessageNotificationForSubtractOperation(){
        String name = "apples";
        int cost = 10;
        int quantity = 3;
        MessageOperation operation = MessageOperation.SUBTRACT;

        sales.add(new Sale("apples", 20));
        sales.add(new Sale("apples", 10));

        MessageNotification message = new MessageNotification(name, cost, quantity, operation);

        MessageProcessor messageProcessor = new MessageProcessor();

        String expectedResult = ", -10p";
        String actualResult = messageProcessor.processTypeThreeMessageNotification(message, sales);

        Assert.assertEquals(expectedResult, actualResult);
        Assert.assertEquals(10, sales.get(0).getPrice());
        Assert.assertEquals(0, sales.get(1).getPrice());
    }

    @Test
    public void testProcessTypeThreeMessageNotificationForMultiplyOperation(){
        String name = "apples";
        int cost = 10;
        int quantity = 3;
        MessageOperation operation = MessageOperation.MULTIPLY;

        sales.add(new Sale("apples", 20));
        sales.add(new Sale("apples", 10));

        MessageNotification message = new MessageNotification(name, cost, quantity, operation);

        MessageProcessor messageProcessor = new MessageProcessor();

        String expectedResult = ", *10p";
        String actualResult = messageProcessor.processTypeThreeMessageNotification(message, sales);

        Assert.assertEquals(expectedResult, actualResult);
        Assert.assertEquals(200, sales.get(0).getPrice());
        Assert.assertEquals(100, sales.get(1).getPrice());
    }

}
