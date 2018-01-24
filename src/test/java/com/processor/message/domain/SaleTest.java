package com.processor.message.domain;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test file for {@link Sale}.
 *
 * @author victor
 */
public class SaleTest {

    @Test
    public void testConstructor(){
        String productType = "apples";
        int price = 20;

        Sale sale = new Sale(productType, price);

        Assert.assertEquals(productType,sale.getProductType());
        Assert.assertEquals(price,sale.getPrice(),0);
    }

    @Test
    public void testSetPrice(){
        int price = 50;

        Sale sale = new Sale();

        sale.setPrice(price);

        Assert.assertEquals(price, sale.getPrice());
    }

    @Test
    public void testToString(){
        String productType = "apples";
        int price = 20;

        Sale sale = new Sale(productType, price);

        String expectedOutput = "Sale{productType='apples', price=20}";
        String actualOutput = sale.toString();

        Assert.assertEquals(expectedOutput, actualOutput);
    }
}
