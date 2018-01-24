package com.processor.message.application;

import com.processor.message.domain.Sale;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Test file for {@link SalesReporter}.
 *
 * @author victor
 */
public class SalesReporterTest {

    private MessageProcessingApplication mockMessageProcessingApplication;

    @Before
    public void setup(){
        mockMessageProcessingApplication = Mockito.mock(MessageProcessingApplication.class);

    }

    @Test
    public void testGetTotalSalesAndValuesReport(){
        Map<String, List<Sale>> mockMap = new HashMap<String, List<Sale>>();

        List<Sale> appleSales = new ArrayList<Sale>();
        appleSales.add(new Sale("Apple", 10));
        appleSales.add(new Sale("Apple", 10));

        List<Sale> bananaSales = new ArrayList<Sale>();
        bananaSales.add(new Sale("Banana", 15));
        bananaSales.add(new Sale("Banana", 15));

        List<Sale> orangeSales = new ArrayList<Sale>();
        orangeSales.add(new Sale("Orange", 20));
        orangeSales.add(new Sale("Orange", 20));

        mockMap.put("Apple", appleSales);
        mockMap.put("Banana", bananaSales);
        mockMap.put("Orange", orangeSales);

        MessageProcessingApplication.salesRecord = mockMap;

        SalesReporter reporter = new SalesReporter();

        String expectedResult = "Product Type: 'Apple', Total Number of Sales: 2, Total Price: 20p\n" +
                "Product Type: 'Orange', Total Number of Sales: 2, Total Price: 40p\n" +
                "Product Type: 'Banana', Total Number of Sales: 2, Total Price: 30p\n";
        String actualResult = reporter.getTotalSalesAndValuesReport();

        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testGetProductAdjustmentReport(){
        Map<String, StringBuilder> productAdjustmentHistory = new HashMap<String, StringBuilder>();

        StringBuilder appleBuilder = new StringBuilder();
        appleBuilder.append(", +10p, -10p, *10p");

        StringBuilder orangeBuilder = new StringBuilder();
        orangeBuilder.append(", -15p, +1p, *20p");

        productAdjustmentHistory.put("Apple", appleBuilder);
        productAdjustmentHistory.put("Orange", orangeBuilder);

        SalesReporter reporter = new SalesReporter();

        String expectedResult = "Product Type: 'Apple': Adjustment History: , +10p, -10p, *10p.\n" +
                "Product Type: 'Orange': Adjustment History: , -15p, +1p, *20p.\n";
        String actualResult = reporter.getProductAdjustmentReport(productAdjustmentHistory);

        Assert.assertEquals(expectedResult, actualResult);
    }
}
