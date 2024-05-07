package org.example.putscanner.services;


import org.example.putscanner.model.Ticker;
import org.sikuli.script.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Set;


public class SikuliService {
    //properties
    private final String IMAGE_USED_PATH = "C:\\Users\\larry\\workspace\\PutScanner\\src\\main\\resources\\navigationImages";
    private final String CAPTURED_IMAGE_PATH = "C:\\Users\\larry\\workspace\\PutScanner\\src\\main\\resources\\capturedImages\\";
    private final Pattern Happy_Hat = new Pattern(IMAGE_USED_PATH + "\\SearchBar.PNG");
    private final Pattern OPTIONS_TAB = new Pattern(IMAGE_USED_PATH + "\\OptionsTap.PNG");
    private final Pattern SEPARATOR_BAR = new Pattern(IMAGE_USED_PATH + "\\SeparatorBar.PNG");
    private final Pattern ANALYSIS_TAB = new Pattern(IMAGE_USED_PATH + "\\AnalysisTab.PNG");
    private final Region PUT_DATE1 = new Region(288, 145, 125, 29);
    private final Region PUT_DATE2 = new Region(283, 178, 125, 22);
    private final Region ANALYSTS_PRICE_TARGET = new Region(711, 170, 441, 168);

    //constructors
    public SikuliService()  {
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

        try {

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
                    350, 24);
            createAndSaveImage(screen, ticker.getTicker() + "option1.PNG", putOption);
            PUT_DATE1.click();
            PUT_DATE2.click();
            createAndSaveImage(screen, ticker.getTicker() + "date2.PNG", PUT_DATE2);
            quarterPageRegion.wait(.25);
            match = quarterPageRegion.find(SEPARATOR_BAR.similar(.95));
            putOption = new Region(match.getTopLeft().getX() - 50, match.getTopLeft().getY() - 20,
                    350, 24);
            createAndSaveImage(screen, ticker.getTicker() + "option2.PNG", putOption);
            PUT_DATE2.click();
            //takes screenshots of ticker price targets
            quarterPageRegion.mouseMove(ANALYSIS_TAB.similar(.95));
            quarterPageRegion.click();
            createAndSaveImage(screen, ticker.getTicker() + "priceTargets.PNG", ANALYSTS_PRICE_TARGET);

        } catch (FindFailed e) {

            e.printStackTrace();

        }


    }

    private void createAndSaveImage(Screen screen, String s, Region region) {

        try {

            BufferedImage image = screen.capture(region).getImage();

            for (int y = 0; y < image.getHeight(); y++) {

                for (int x = 0; x < image.getWidth(); x++) {

                    int rgb = image.getRGB(x, y);
                    int red = (rgb >> 16) & 0xFF;
                    int green = (rgb >> 8) & 0xFF;
                    int blue = rgb & 0xFF;
                    int avg = (red + green + blue) / 3;
                    rgb = (avg << 16) + (avg << 8) + avg;
                    image.setRGB(x, y, rgb);

                }

            } ImageIO.write(image, "png", new File(CAPTURED_IMAGE_PATH + s));

        } catch (IOException e) {

            System.err.println("Error reading or writing the image: " + e.getMessage());

        }

    }

}
