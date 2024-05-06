package org.example.putscanner.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class TesseractService {
    //properties
    File tesseractLanguageData = new File("C:\\Program Files\\Tesseract-OCR\\tessdata");
    Tesseract tesseract = new Tesseract();

    //methods
    public void createStockData(List<String> tickers) throws TesseractException {

        String imagePath = "C:\\Users\\larry\\workspace\\PutScanner\\src\\main\\resources\\capturedImages\\";
        tesseract.setDatapath(tesseractLanguageData.getAbsolutePath());

        for (String ticker : tickers) {

            String text = tesseract.doOCR(new File(imagePath + ticker + "date1.PNG"));


        }
    }

}
