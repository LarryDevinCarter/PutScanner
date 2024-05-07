package org.example.putscanner.model;

import java.math.BigDecimal;

public class Ticker {
    //properties
    String ticker;
    BigDecimal high = new BigDecimal(0);
    BigDecimal average = new BigDecimal(0);
    BigDecimal low = new BigDecimal(0);

    //constructors
    public Ticker(String ticker) {
        this.ticker = ticker;
    }

    public void setHigh(BigDecimal high) {
        this.high = high;
    }

    public void setAverage(BigDecimal average) {
        this.average = average;
    }

    public void setLow(BigDecimal low) {
        this.low = low;
    }

    public String getTicker() {
        return ticker;
    }

    public BigDecimal getHigh() {
        return high;
    }

    public BigDecimal getAverage() {
        return average;
    }

    public BigDecimal getLow() {
        return low;
    }
}
