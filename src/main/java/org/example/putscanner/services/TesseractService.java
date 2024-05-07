package org.example.putscanner.services;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.example.putscanner.model.Option;
import org.example.putscanner.model.Ticker;
import org.sikuli.script.ScreenImage;

import javax.imageio.ImageIO;

public class TesseractService {
    //properties
    File tesseractLanguageData = new File("C:\\Program Files\\Tesseract-OCR\\tessdata");
    Tesseract tesseract = new Tesseract();

    //methods
    public List<Option> createStockData(Ticker ticker) throws TesseractException {

        List<Option> options = new ArrayList<>();
        String imagePath = "C:\\Users\\larry\\workspace\\PutScanner\\src\\main\\resources\\capturedImages\\";
        tesseract.setDatapath(tesseractLanguageData.getAbsolutePath());
        Ticker updatedTicker = updateTickerFromPriceTargets(new File(imagePath + ticker.getTicker() + "priceTargets.PNG"), ticker);
        Date expirationDate = getDateFromImage(new File(imagePath + ticker.getTicker() + "date1.PNG"));
        options.add(buildOption(updatedTicker, expirationDate, new File(imagePath + ticker.getTicker() + "option1.PNG")));
        expirationDate = getDateFromImage(new File(imagePath + ticker.getTicker() + "date2.PNG"));
        options.add(buildOption(updatedTicker, expirationDate, new File(imagePath + ticker.getTicker() + "option2.PNG")));
        return options;

    }

    private Option buildOption(Ticker updatedTicker, Date expirationDate, File image) throws TesseractException {

        BigDecimal strike = new BigDecimal(0);
        BigDecimal bid = new BigDecimal(0);
        BigDecimal ask = new BigDecimal(0);
        BigDecimal last = new BigDecimal(0);

        try {

            BufferedImage oldImage = ImageIO.read(image);
            int newWidth = oldImage.getWidth()*2;
            int newHeight = oldImage.getHeight()*2;
            Image scaledUpImage = oldImage.getScaledInstance(newWidth, newHeight, Image.SCALE_DEFAULT);
            BufferedImage newImage = new BufferedImage(newWidth, newHeight, oldImage.getType());
            newImage.createGraphics().drawImage(scaledUpImage, 0, 0, null);
            String text = tesseract.doOCR(newImage);
            System.out.println(text);
            String[] option = text.split("\\s+");

            for (int i = 0; i < option.length; i++) {

                option[i] = option[i].replaceAll("[^0-9.]", "");

                if (option[i].contains(".")) {

                    String firstHalf = option[i].substring(0, option[i].indexOf(".") + 1);
                    option[i] = firstHalf + option[i].substring(option[i].indexOf(".")).replaceAll("[^0-9]", "");

                }

            }

            strike = new BigDecimal(option[0]);
            bid = new BigDecimal(option[1]);
            ask = new BigDecimal(option[2]);
            last = new BigDecimal(option[3]);

        } catch (IOException e) {

            e.printStackTrace();

        } return new Option(updatedTicker, expirationDate, strike, bid, ask, last);

    }

    private Ticker updateTickerFromPriceTargets(File image, Ticker ticker) throws TesseractException {

        List<String> priceTargets = new ArrayList<>();

        try {
            BufferedImage oldImage = ImageIO.read(image);
            int newWidth = oldImage.getWidth() * 2;
            int newHeight = oldImage.getHeight() * 2;
            Image scaledUpImage = oldImage.getScaledInstance(newWidth, newHeight, Image.SCALE_DEFAULT);
            BufferedImage newImage = new BufferedImage(newWidth, newHeight, oldImage.getType());
            newImage.createGraphics().drawImage(scaledUpImage, 0, 0, null);
            String text = tesseract.doOCR(image);
            String[] priceTarget = text.split("\\s+");

            for (int i = 0; i < priceTarget.length; i++) {

                if (priceTarget[i].contains(".")) {

                    priceTargets.add(priceTarget[i]);

                }

            }
            for (int i = 0; i < priceTargets.size(); i++) {

                priceTargets.set(i, priceTargets.get(i).replaceAll("[^0-9.]", ""));
                System.out.println(priceTargets.get(i));

            }
            if (!priceTargets.isEmpty()) {

                ticker.setHigh(new BigDecimal(priceTargets.getFirst()));

            }
            if (priceTargets.size() > 1) {

                ticker.setAverage(new BigDecimal(priceTargets.get(1)));

            }
            if (priceTargets.size() > 2) {

                ticker.setLow(new BigDecimal(priceTargets.get(2)));

            }

        } catch (IOException e) {

            e.printStackTrace();

        } return ticker;

    }

    private Date getDateFromImage(File image) throws TesseractException {
        String text = tesseract.doOCR(image);
        String[] date = text.split("\\s+");
        return new Date(Integer.parseInt(date[2]) + 100, getMonthNumber(date[1]), Integer.parseInt(date[0]));
    }

    private int getMonthNumber(String s) {
        if (s.equals("Jan")) {
            return Calendar.JANUARY;
        } if (s.equals("Feb")) {
            return Calendar.FEBRUARY;
        } if (s.equals("Mar")) {
            return Calendar.MARCH;
        } if (s.equals("Apr")) {
            return Calendar.APRIL;
        } if (s.equals("May")) {
            return Calendar.MAY;
        } if (s.equals("Jun")) {
            return Calendar.JUNE;
        } if (s.equals("Jul")) {
            return Calendar.JULY;
        } if (s.equals("Aug")) {
            return Calendar.AUGUST;
        } if (s.equals("Sep")) {
            return Calendar.SEPTEMBER;
        } if (s.equals("Oct")) {
            return Calendar.OCTOBER;
        } if (s.equals("Nov")) {
            return Calendar.NOVEMBER;
        } if (s.equals("Dec")) {
            return Calendar.DECEMBER;
        } return -1;
    }

}
