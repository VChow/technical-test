package com.processor.message.domain;

/**
 * Encapsulates the data for a Sale.
 *
 * @author victor
 */
public class Sale {

    /** The type of the product that was sold */
    private String productType;

    /** The price that the product was sold for (pence) */
    private int price;

    /**
     * Default no argument constructor
     */
    public Sale(){}

    /**
     * Constructor
     *
     * @param productType the productType to set
     * @param price the price to set
     */
    public Sale(String productType, int price){
        this.productType = productType;
        this.price = price;
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
     * Sets the price
     *
     * @param price the price to set
     */
    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Sale{" +
                "productType='" + productType + '\'' +
                ", price=" + price +
                '}';
    }
}
