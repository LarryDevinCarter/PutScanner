package org.example.putscanner.services;


import org.example.putscanner.model.Ticker;
import org.sikuli.script.*;

import java.util.Set;


public class ScreenRegulator {
    //properties
    private final String imageUsedPath = "C:\\Users\\larry\\workspace\\PutScanner\\src\\main\\resources\\navigationImages";
    private final String capturedImagePath = "C:\\Users\\larry\\workspace\\PutScanner\\src\\main\\resources\\capturedImages";
    private final Pattern Happy_Hat = new Pattern(imageUsedPath + "\\SearchBar.PNG");
    private final Pattern OPTIONS_TAB = new Pattern(imageUsedPath + "\\OptionsTap.PNG");
    private final Pattern SEPARATOR_BAR = new Pattern(imageUsedPath + "\\SeparatorBar.PNG");
    private final Pattern ANALYSIS_TAB = new Pattern(imageUsedPath + "\\AnalysisTab.PNG");
    private final Region PUT_DATE1 = new Region(288, 145, 125, 29);
    private final Region PUT_DATE2 = new Region(283, 178, 125, 22);
    private final Region ANALYSTS_PRICE_TARGET = new Region(711, 170, 441, 168);

    //constructors
    public ScreenRegulator()  {
    }

    //methods
    public void getImages(Set<Ticker> stocksToScan) throws FindFailed {

        Screen screen = new Screen();

        for (Ticker ticker : stocksToScan) {

            scanTicker(screen, ticker);

        }


    }

    private void scanTicker(Screen screen, Ticker ticker) throws FindFailed {

        Match match;
        Region putOption;

        //navigates to the options pages of a TICKER
        Region quarterPageRegion = new Region(0, 0, (screen.getBounds().width / 3) + 11, screen.getBounds().height / 2);
        quarterPageRegion.mouseMove(Happy_Hat.similar(.95));
        quarterPageRegion.click();
        quarterPageRegion.type(ticker.getTicker());
        quarterPageRegion.wait(.5);
        quarterPageRegion.type("\n");
        quarterPageRegion.mouseMove(OPTIONS_TAB.similar(.95));
        quarterPageRegion.click();
        //takes screenshots of options and their expiration dates
        createAndSaveImage(screen, ticker.getTicker() + "date1.PNG", PUT_DATE1);
        match = quarterPageRegion.find(SEPARATOR_BAR.similar(.95));
        putOption = new Region(match.getTopLeft().getX() - 50, match.getTopLeft().getY() - 20,
                350, 20);
        createAndSaveImage(screen, ticker.getTicker() + "option1.PNG", putOption);
        PUT_DATE1.click();
        PUT_DATE2.click();
        createAndSaveImage(screen, ticker.getTicker() + "date2.PNG", PUT_DATE2);
        quarterPageRegion.wait(.25);
        match = quarterPageRegion.find(SEPARATOR_BAR.similar(.95));
        putOption = new Region(match.getTopLeft().getX() - 50, match.getTopLeft().getY() - 20,
                350, 20);
        createAndSaveImage(screen, ticker.getTicker() + "option2.PNG", putOption);
        PUT_DATE2.click();
        //takes screenshots of ticker price targets
        quarterPageRegion.mouseMove(ANALYSIS_TAB.similar(.95));
        quarterPageRegion.click();
        createAndSaveImage(screen, ticker.getTicker() + "priceTargets.PNG", ANALYSTS_PRICE_TARGET);


    }

    private void createAndSaveImage(Screen screen, String s, Region region) {

        ScreenImage image = screen.capture(region);
        image.save(capturedImagePath, s);

    }

}
