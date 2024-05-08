package org.example.putscanner.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

import org.example.putscanner.jdbc.JdbcTicker;
import javafx.beans.property.*;

public class Option {
    //properties
    Ticker ticker;
    String symbol;
    Date expiration;
    String expirationText;
    BigDecimal strike;
    BigDecimal bid;
    BigDecimal ask;
    BigDecimal last;
    BigDecimal prRating;
    BigDecimal profitPerWeek;
    BigDecimal myAsk;
    SimpleDateFormat format = new SimpleDateFormat("dd MMM yy");

    //constructors

    public Option(Ticker ticker, Date expiration, BigDecimal strike, BigDecimal bid, BigDecimal ask, BigDecimal last) {
        this.ticker = ticker;
        this.symbol = this.ticker.getTicker();
        this.expiration = expiration;
        if (this.expiration != null) {
            this.expirationText = format.format(new java.util.Date(this.expiration.getTime()));
            System.out.println(this.expirationText);
        }
        this.strike = strike;
        this.bid = bid;
        this.ask = ask;
        this.last = last;
        if (last.subtract(new BigDecimal(".01")).compareTo(ask) < 0 && last.subtract(new BigDecimal(".01")).compareTo(bid) > 0) {
            this.myAsk = last.subtract(new BigDecimal(".01"));
        } else if (last.subtract(new BigDecimal(".01")).compareTo(ask) > 0) {
            this.myAsk = ask.subtract(new BigDecimal(".01"));
        } else {
            this.myAsk = bid.add(new BigDecimal(".01"));
        }
        if (this.expiration != null) {
            this.profitPerWeek = myAsk.divide(strike, 4, RoundingMode.UP).divide(new BigDecimal(ChronoUnit.WEEKS.between(LocalDate.now(), this.expiration.toLocalDate()) + 1), 4, RoundingMode.UP);
        }
        if (this.profitPerWeek != null) {
            this.prRating = profitPerWeek.multiply(new BigDecimal(1).subtract(strike.divide(ticker.getAverage(), 4, RoundingMode.UP)));
        }
    }
    public Option(String ticker, Date expiration, BigDecimal strike, BigDecimal bid, BigDecimal ask, BigDecimal last) {
        this.ticker = JdbcTicker.getTicker(ticker);
        this.symbol = ticker;
        this.expiration = expiration;
        if (this.expiration != null) {
            this.expirationText = format.format(new java.util.Date(this.expiration.getTime()));
            System.out.println(this.expirationText);
        }
        this.strike = strike;
        this.bid = bid;
        this.ask = ask;
        this.last = last;
        if (last.subtract(new BigDecimal(".01")).compareTo(ask) < 0 && last.subtract(new BigDecimal(".01")).compareTo(bid) > 0) {
            this.myAsk = last.subtract(new BigDecimal(".01"));
        } else if (last.subtract(new BigDecimal(".01")).compareTo(ask) > 0) {
            this.myAsk = ask.subtract(new BigDecimal(".01"));
        } else {
            this.myAsk = bid.add(new BigDecimal(".01"));
        }
        if (this.expiration != null) {
            this.profitPerWeek = myAsk.divide(strike, 4, RoundingMode.UP).divide(new BigDecimal(ChronoUnit.WEEKS.between(LocalDate.now(), this.expiration.toLocalDate()) + 1), 4, RoundingMode.UP);
        }
        if (this.ticker.getAverage() != null && !this.ticker.getAverage().equals(new BigDecimal("0.00")) && this.profitPerWeek != null) {
            this.prRating = profitPerWeek.multiply(new BigDecimal(1).subtract(strike.divide(this.ticker.getAverage(), 4, RoundingMode.UP)));
        }
    }

    public Ticker getTicker() {
        return ticker;
    }

    public Date getExpiration() {
        return expiration;
    }

    public BigDecimal getStrike() {
        return strike;
    }

    public BigDecimal getBid() {
        return bid;
    }

    public BigDecimal getAsk() {
        return ask;
    }

    public BigDecimal getLast() {
        return last;
    }

    public BigDecimal getPrRating() {
        return prRating;
    }

    public BigDecimal getProfitPerWeek() {
        return profitPerWeek;
    }

    public BigDecimal getMyAsk() {
        return myAsk;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getExpirationText() {
        return expirationText;
    }
}

