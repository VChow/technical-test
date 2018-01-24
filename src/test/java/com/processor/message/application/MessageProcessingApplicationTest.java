package com.processor.message.application;

import com.processor.message.domain.MessageNotification;
import com.processor.message.domain.MessageOperation;
import com.processor.message.domain.Sale;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

import java.util.*;

/**
 * Test file for {@link MessageProcessingApplication}.
 *
 * @author victor
 */
public class MessageProcessingApplicationTest {

    private MessageProcessor mockMessageProcessor;
    private SalesReporter mockSalesReporter;

    @Before
    public void setup(){
        mockMessageProcessor = Mockito.mock(MessageProcessor.class);
        mockSalesReporter = Mockito.mock(SalesReporter.class);
        //MessageProcessingApplication.messageQueue.clear();
    }

    @Test
    public void testProcessTypeOneNotification(){
        //Setup test subject
        MessageProcessingApplication application = new MessageProcessingApplication(mockMessageProcessor, mockSalesReporter);

        MessageNotification message_1 = new MessageNotification("Apple", 10);
        MessageNotification message_2 = new MessageNotification("Banana", 20);
        MessageNotification message_3 = new MessageNotification("Orange", 30);

        Queue<MessageNotification> messageQueue = new LinkedList<MessageNotification>();
        messageQueue.add(message_1);
        messageQueue.add(message_2);
        messageQueue.add(message_3);

        MessageProcessingApplication.messageQueue = messageQueue;

        Sale sale_1 = new Sale("Apple", 10);
        Sale sale_2 = new Sale("Banana", 20);
        Sale sale_3 = new Sale("Orange", 30);

        Mockito.when(mockMessageProcessor.processTypeOneMessageNotification(Matchers.eq(message_1))).thenReturn(sale_1);
        Mockito.when(mockMessageProcessor.processTypeOneMessageNotification(Matchers.eq(message_2))).thenReturn(sale_2);
        Mockito.when(mockMessageProcessor.processTypeOneMessageNotification(Matchers.eq(message_3))).thenReturn(sale_3);

        //Invoke method under test
        application.start();

        //Verify the messageProcessor was interacted with
        Mockito.verify(mockMessageProcessor, Mockito.times(3)).processTypeOneMessageNotification(Mockito.any(MessageNotification.class));

        //Assert a new key was added to the Map for each new product
        Assert.assertTrue(MessageProcessingApplication.salesRecord.containsKey("Apple"));
        Assert.assertTrue(MessageProcessingApplication.salesRecord.containsKey("Banana"));
        Assert.assertTrue(MessageProcessingApplication.salesRecord.containsKey("Orange"));

        //Assert the Sale values were correctly set
        Assert.assertEquals(sale_1, MessageProcessingApplication.salesRecord.get("Apple").get(0));
        Assert.assertEquals(sale_2, MessageProcessingApplication.salesRecord.get("Banana").get(0));
        Assert.assertEquals(sale_3, MessageProcessingApplication.salesRecord.get("Orange").get(0));
    }

    @Test
    public void testProcessTypeTwoNotification(){
        MessageProcessingApplication application = new MessageProcessingApplication(mockMessageProcessor, mockSalesReporter);

        MessageNotification message_1 = new MessageNotification("Apple", 10, 1);
        MessageNotification message_2 = new MessageNotification("Banana", 20, 2);
        MessageNotification message_3 = new MessageNotification("Orange", 30, 2);

        Queue<MessageNotification> messageQueue = new LinkedList<MessageNotification>();
        messageQueue.add(message_1);
        messageQueue.add(message_2);
        messageQueue.add(message_3);

        MessageProcessingApplication.messageQueue = messageQueue;

        List<Sale> appleSales = new ArrayList<Sale>();
        Sale appleSale_1 = new Sale("Apple", 10);
        appleSales.add(appleSale_1);

        List<Sale> bananaSales = new ArrayList<Sale>();
        Sale bananaSale_1 = new Sale("Banana", 20);
        Sale bananaSale_2 = new Sale("Banana", 30);
        bananaSales.add(bananaSale_1);
        bananaSales.add(bananaSale_2);

        List<Sale> orangeSales = new ArrayList<Sale>();
        Sale orangeSale_1 = new Sale("Orange", 20);
        Sale orangeSale_2 = new Sale("Orange", 30);
        orangeSales.add(orangeSale_1);
        orangeSales.add(orangeSale_2);

        Mockito.when(mockMessageProcessor.processTypeTwoMessageNotification(Matchers.eq(message_1))).thenReturn(appleSales);
        Mockito.when(mockMessageProcessor.processTypeTwoMessageNotification(Matchers.eq(message_2))).thenReturn(bananaSales);
        Mockito.when(mockMessageProcessor.processTypeTwoMessageNotification(Matchers.eq(message_3))).thenReturn(orangeSales);

        //Invoke method under test
        application.start();

        //Verify the messageProcessor was interacted with
        Mockito.verify(mockMessageProcessor, Mockito.times(3)).processTypeTwoMessageNotification(Mockito.any(MessageNotification.class));

        //Assert a new key was added to the Map for each new product
        Assert.assertTrue(MessageProcessingApplication.salesRecord.containsKey("Apple"));
        Assert.assertTrue(MessageProcessingApplication.salesRecord.containsKey("Banana"));
        Assert.assertTrue(MessageProcessingApplication.salesRecord.containsKey("Orange"));

        //Assert number of Sales are correct
        Assert.assertEquals(1, MessageProcessingApplication.salesRecord.get("Apple").size());
        Assert.assertEquals(2, MessageProcessingApplication.salesRecord.get("Banana").size());
        Assert.assertEquals(2, MessageProcessingApplication.salesRecord.get("Orange").size());

        //Assert the Sale values were correctly set
        Assert.assertEquals(appleSale_1, MessageProcessingApplication.salesRecord.get("Apple").get(0));
        Assert.assertEquals(bananaSale_1, MessageProcessingApplication.salesRecord.get("Banana").get(0));
        Assert.assertEquals(bananaSale_2, MessageProcessingApplication.salesRecord.get("Banana").get(1));
        Assert.assertEquals(orangeSale_1, MessageProcessingApplication.salesRecord.get("Orange").get(0));
        Assert.assertEquals(orangeSale_2, MessageProcessingApplication.salesRecord.get("Orange").get(1));
    }

    @Test
    public void testProcessTypeThreeNotification(){
        MessageProcessingApplication application = new MessageProcessingApplication(mockMessageProcessor, mockSalesReporter);

        //Setup messageQueue
        MessageNotification message_1 = new MessageNotification("Apple", 10, 1, MessageOperation.ADD);
        MessageNotification message_2 = new MessageNotification("Apple", 10, 1, MessageOperation.SUBTRACT);

        Queue<MessageNotification> messageQueue = new LinkedList<MessageNotification>();
        messageQueue.add(message_1);
        messageQueue.add(message_2);

        MessageProcessingApplication.messageQueue = messageQueue;

        //Setup salesRecord
        Map<String, List<Sale>> salesRecord = new HashMap<String, List<Sale>>();
        List<Sale> appleSales = new ArrayList<Sale>();
        Sale sale_1 = new Sale("Apple", 10);
        Sale sale_2 = new Sale("Apple", 20);
        appleSales.add(sale_1);
        appleSales.add(sale_2);

        salesRecord.put("Apple", appleSales);

        MessageProcessingApplication.salesRecord = salesRecord;

        String mockLog_1 = ", +10p";
        Mockito.when(mockMessageProcessor.processTypeThreeMessageNotification(Matchers.eq(message_1), Mockito.anyList())).thenReturn(mockLog_1);

        String mockLog_2 = ", -10p";
        Mockito.when(mockMessageProcessor.processTypeThreeMessageNotification(Matchers.eq(message_2), Mockito.anyList())).thenReturn(mockLog_2);

        //Invoke method under test
        application.start();

        //Verify the messageProcessor was interacted with
        Mockito.verify(mockMessageProcessor, Mockito.times(1)).processTypeThreeMessageNotification(Matchers.eq(message_1), Mockito.anyList());
        Mockito.verify(mockMessageProcessor, Mockito.times(1)).processTypeThreeMessageNotification(Matchers.eq(message_2), Mockito.anyList());

        String finalLog = ", +10p, -10p";
        //Assert productAdjustmentLog was correctly updated
        Assert.assertEquals(finalLog, application.getProductAdjustmentHistory().get("Apple").toString());

    }

    @Test
    public void testProcessTypeThreeNotificationWhenNoSaleRecordExists(){
        MessageProcessingApplication application = new MessageProcessingApplication(mockMessageProcessor, mockSalesReporter);

        MessageNotification message_1 = new MessageNotification("Apple", 10, 1, MessageOperation.ADD);
        Queue<MessageNotification> messageQueue = new LinkedList<MessageNotification>();
        messageQueue.add(message_1);

        MessageProcessingApplication.messageQueue = messageQueue;

        //Invoke method under test
        application.start();

        Mockito.verifyZeroInteractions(mockMessageProcessor);
    }

    @Test
    public void testWhenMessageQueueIsEmptyNoInteractions(){
        MessageProcessingApplication application = new MessageProcessingApplication(mockMessageProcessor, mockSalesReporter);

        //Invoke method under test
        application.start();

        Mockito.verifyZeroInteractions(mockMessageProcessor);
    }

    @Test
    public void testTotalSalesAndValuesReportLoggedAfterTenMessagesProcessed(){
        MessageProcessingApplication application = new MessageProcessingApplication(mockMessageProcessor, mockSalesReporter);

        Queue<MessageNotification> messageQueue = new LinkedList<MessageNotification>();

        for(int i=0; i<20; i++){
            messageQueue.add(new MessageNotification("Apple", 1));
        }

        MessageProcessingApplication.messageQueue = messageQueue;

        Mockito.when(mockMessageProcessor.processTypeOneMessageNotification(Mockito.any(MessageNotification.class))).thenReturn(new Sale("Apple", 10));

        application.start();

        Mockito.verify(mockSalesReporter, Mockito.times(2)).getTotalSalesAndValuesReport();
    }

    @Test
    public void testProductAdjustmentReportIsLogged(){
        MessageProcessingApplication application = new MessageProcessingApplication(mockMessageProcessor, mockSalesReporter);

        Queue<MessageNotification> messageQueue = new LinkedList<MessageNotification>();

        for(int i=0; i<50; i++){
            messageQueue.add(new MessageNotification("Apple", 1));
        }

        MessageProcessingApplication.messageQueue = messageQueue;

        Mockito.when(mockMessageProcessor.processTypeOneMessageNotification(Mockito.any(MessageNotification.class))).thenReturn(new Sale("Apple", 10));

        application.start();

        Mockito.verify(mockSalesReporter, Mockito.times(1)).getProductAdjustmentReport(Mockito.anyMap());
    }
}
