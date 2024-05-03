package org.example.putscanner.services;


import org.sikuli.script.*;
import org.sikuli.script.Button;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Set;

import static org.sikuli.script.Mouse.*;

public class ScreenRegulator {
    private final Pattern Happy_Hat = new Pattern("C:\\Users\\larry\\workspace\\PutScanner\\src\\main\\resources\\navigationImages\\SearchBar.PNG");
    private final Pattern OPTIONS_TAB = new Pattern("C:\\Users\\larry\\workspace\\PutScanner\\src\\main\\resources\\navigationImages\\OptionsTap.PNG");
    private final Pattern STRIKE_COLUMN = new Pattern("C:\\Users\\larry\\workspace\\PutScanner\\src\\main\\resources\\navigationImages\\StrikeColumn.PNG");
    //methods
    public void getData(Set<String> stocksToScan) throws FindFailed, InterruptedException {

        Screen screen = new Screen();
        Region fullPage;
        Region putDate;

        for (String ticker : stocksToScan) {

            System.out.println(ticker);
            fullPage = new Region(0,0, (screen.getBounds().width / 3) + 11, screen.getBounds().height);
            fullPage.mouseMove(Happy_Hat.similar(.95));
            fullPage.click();
            fullPage.type(ticker);
            fullPage.wait(.5);
            fullPage.type("\n");
            fullPage.wait(.5);
            fullPage.mouseMove(OPTIONS_TAB.similar(.95));
            fullPage.click();
            fullPage.mouseMove(STRIKE_COLUMN.similar(.95));
            //putDate = new Region(new Rectangle())

            System.out.println("I made it here");

        }

    }

}