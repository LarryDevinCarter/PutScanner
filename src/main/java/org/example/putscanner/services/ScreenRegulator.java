package org.example.putscanner.services;


import org.sikuli.script.*;

import java.util.Set;


public class ScreenRegulator {
    private final String imageUsedPath = "C:\\Users\\larry\\workspace\\PutScanner\\src\\main\\resources\\navigationImages";
    private final String capturedImagePath = "C:\\Users\\larry\\workspace\\PutScanner\\src\\main\\resources\\capturedImages";
    private final Pattern Happy_Hat = new Pattern(imageUsedPath + "\\SearchBar.PNG");
    private final Pattern OPTIONS_TAB = new Pattern(imageUsedPath + "\\OptionsTap.PNG");
    private final Pattern SEPARATOR_BAR = new Pattern(imageUsedPath + "\\SeparatorBar.PNG");
    private final Pattern ANALYSIS_TAB = new Pattern(imageUsedPath + "\\AnalysisTab.PNG");
    private final Region PUT_DATE1 = new Region(288, 145, 125, 29);
    private final Region PUT_DATE2 = new Region(283, 178, 125, 22);
    private final Region ANALYSTS_PRICE_TARGET = new Region(711, 170, 441, 168);


    public ScreenRegulator() throws FindFailed {
    }

    //methods
    public void getData(Set<String> stocksToScan) throws FindFailed {

        Screen screen = new Screen();

        for (String ticker : stocksToScan) {

            scanTicker(screen, ticker);

        }


    }

    private void scanTicker(Screen screen, String ticker) throws FindFailed {

        Match match;
        Region putOption;

        //navigates to the options pages of a TICKER
        Region quarterPageRegion = new Region(0, 0, (screen.getBounds().width / 3) + 11, screen.getBounds().height / 2);
        quarterPageRegion.mouseMove(Happy_Hat.similar(.95));
        quarterPageRegion.click();
        quarterPageRegion.type(ticker);
        quarterPageRegion.wait(.5);
        quarterPageRegion.type("\n");
        quarterPageRegion.wait(.5);
        quarterPageRegion.mouseMove(OPTIONS_TAB.similar(.95));
        quarterPageRegion.click();
        //takes screenshots of options and their expiration dates
        createAndSaveImage(screen, ticker + "date1.PNG", PUT_DATE1);
        match = quarterPageRegion.find(SEPARATOR_BAR.similar(.95));
        putOption = new Region(match.getTopLeft().getX() - 50, match.getTopLeft().getY() - 20,
                350, 20);
        createAndSaveImage(screen, ticker + "option1.PNG", putOption);
        PUT_DATE1.click();
        PUT_DATE2.click();
        createAndSaveImage(screen, ticker + "date2.PNG", PUT_DATE2);
        match = quarterPageRegion.find(SEPARATOR_BAR.similar(.95));
        putOption = new Region(match.getTopLeft().getX() - 50, match.getTopLeft().getY() - 20,
                350, 20);
        createAndSaveImage(screen, ticker + "option2.PNG", putOption);
        //takes screenshots of ticker price targets
        quarterPageRegion.mouseMove(ANALYSIS_TAB.similar(.95));
        quarterPageRegion.click();
        createAndSaveImage(screen, ticker + "priceTargets.PNG", ANALYSTS_PRICE_TARGET);


    }

    private void createAndSaveImage(Screen screen, String s, Region region) {

        ScreenImage image = screen.capture(region);
        image.save(capturedImagePath, s);

    }

}


//    Screen screen = new Screen();
//        Region quarterPageRegion = new Region(0,0, (screen.getBounds().width / 3) + 11, screen.getBounds().height / 2);
//        ScreenImage image;
//        Region putOption;
//        Match match;
//        Region analystPriceTarget = new Region(711, 170, 441, 168);
//
//        for (String ticker : stocksToScan) {
//
//            System.out.println(ticker);
//            quarterPageRegion.mouseMove(Happy_Hat.similar(.95));
//            quarterPageRegion.click();
//            quarterPageRegion.type(ticker);
//            quarterPageRegion.wait(.5);
//            quarterPageRegion.type("\n");
//            quarterPageRegion.wait(.5);
//            quarterPageRegion.mouseMove(OPTIONS_TAB.similar(.95));
//            quarterPageRegion.click();
//            image = screen.capture(PUT_DATE1);
//            image.save(capturedImagePath ,ticker + "date1.PNG");
//            match = quarterPageRegion.find(SEPARATOR_BAR.similar(.95));
//            putOption = new Region(match.getTopLeft().getX() - 50, match.getTopLeft().getY() - 20,
//                    350, 20);
//            image = screen.capture(putOption);
//            image.save(capturedImagePath ,ticker + "option1.PNG");
//            PUT_DATE1.click();
//            PUT_DATE2.click();
//            image = screen.capture(PUT_DATE2);
//            image.save(capturedImagePath ,ticker + "date2.PNG");
//            match = quarterPageRegion.wait(SEPARATOR_BAR.similar(.95));
//            putOption = new Region(match.getTopLeft().getX() - 50, match.getTopLeft().getY() - 20,
//                    350, 20);
//            image = screen.capture(putOption);
//            image.save(capturedImagePath ,ticker + "option2.PNG");
//            quarterPageRegion.mouseMove(ANALYSIS_TAB.similar(.95));
//            quarterPageRegion.click();
//            image = screen.capture(ANALYSTS_PRICE_TARGET);
//            image.save(capturedImagePath ,ticker + "priceTargets.PNG");
//
//        }
//
//    }
//
//}
////////////////////////////////////////////
//        fullPage = new Region(0,0, (screen.getBounds().width / 3) + 11, screen.getBounds().height);
//        match = fullPage.find(TEMP);
//        match.highlight(1.5);
//
//
//        System.out.println("TopLeftX = " + match.getTopLeft().getX());
//        System.out.println("TopLeftY = " + match.getTopLeft().getY());
//        System.out.println("TopRightX = " + match.getTopRight().getX());
//        System.out.println("TopRightY = " + match.getTopRight().getY());
//        System.out.println("BottomLeftX = " + match.getBottomLeft().getX());
//        System.out.println("BottomLeftY = " + match.getBottomLeft().getY());
//        System.out.println("BottomRightX = " + match.getBottomRight().getX());
//        System.out.println("BottomRightY = " + match.getBottomRight().getY());
//
//        Region region = new Region(match.getTopLeft().getX() - 50, match.getTopLeft().getY() - 20,
//                350, 20);
//        region.highlight(1.5);