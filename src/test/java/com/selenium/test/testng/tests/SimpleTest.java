package com.selenium.test.testng.tests;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;

import java.io.*;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import static com.codeborne.selenide.Selenide.*;

/**
 * Created by Sidelnikov Mikhail on 30.07.15.
 * Uses TestNG test framework
 * Test demonstrates simple Selenide functions : how to start browser, open url, insert some text and check that this text was inserted
 * For more information about Selenide visit <a href="http://selenide.org">Selenide</a>
 */
public class SimpleTest {

    String address = "http://www.tripadvisor.com";

    @Test
    public void testTripAdvisor() {

        System.setProperty("webdriver.chrome.driver", "/Users/mukul_sharma/Desktop/chromedriver");
        WebDriverRunner.setWebDriver(new ChromeDriver());

//        Given we are on trip advisor
        open(address);
        SelenideElement flightButton = $(By.id("rdoFlights"));
        flightButton.click();

//        and travelling from pune
        SelenideElement from = $(By.id("metaFlightFrom"));
        from.setValue("");
        from.sendKeys("pune");
        from.pressEnter();

//        to delhi
        SelenideElement to = $(By.id("metaFlightTo"));
        to.sendKeys("delhi");
        to.pressEnter();

        Random random = new Random();

//        with random travellers from 0 to 4
        SelenideElement travellers = $(By.id("fadults"));
        int travellersCount = random.nextInt(5);
        travellers.selectOption(travellersCount);

//        on random dates
        SelenideElement checkIn = $(By.id("checkIn"));
        checkIn.click();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, random.nextInt(15));
        int fromDay = calendar.get(Calendar.DAY_OF_MONTH);
        selectDate(String.valueOf(fromDay));


        SelenideElement checkOut = $(By.id("checkOut"));
        checkOut.click();
        calendar.add(Calendar.DAY_OF_MONTH, random.nextInt(15));
        int toDay = calendar.get(Calendar.DAY_OF_MONTH);
        selectDate(String.valueOf(toDay));

//        when submit is clicked
        SelenideElement submit = $(By.id("SUBMIT_FLIGHTS"));
        submit.click();

//        then if popup is shown close it
        SelenideElement element = $(By.className("ui_close_x"));
        if (element.isDisplayed()) {
            element.click();
        }

//        We should be directed to results page
        SelenideElement updateSearchTitle = $(By.id("updateSearch"));
        updateSearchTitle.shouldBe(Condition.visible);

//        Given we are on results page
        SelenideElement deal = $(By.className("mainButton"));
        deal.shouldBe(Condition.visible);

//        when deal button is clicked
        deal.click();

////        then redirect message should be shown
//        SelenideElement message = $(By.className("messageText"));
//        message.shouldBe(Condition.visible);
//
////        ad price should be visible
//        SelenideElement price = $(By.className("totalPrice"));
//        price.shouldBe(Condition.visible);
//
//        File file = Paths.get("file.txt").toFile();
//        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
//            writer.write(message.getValue());
//            writer.write(price.getValue());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public void selectDate(String date) {
        ElementsCollection allDates = $$(".month td");

        for (WebElement ele : allDates) {

            String data = ele.getText();

            if (date.equalsIgnoreCase(data)) {
                ele.click();
                break;
            }

        }
    }
}
