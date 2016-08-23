package com.selenium.test.testng.tests;

import com.codeborne.selenide.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
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
        Configuration.timeout = 10000;
        Configuration.openBrowserTimeoutMs = 10000;
        Configuration.collectionsTimeout = 10000;

        WebDriver driver = new ChromeDriver();
        WebDriverRunner.setWebDriver(driver);

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
        SelenideElement closeFirstPopup = $(By.className("ui_close_x"));
        if (closeFirstPopup.isDisplayed()) {
            closeFirstPopup.click();
        }

//        We should be directed to results page
        SelenideElement updateSearchTitle = $(By.id("updateSearch"));
        updateSearchTitle.shouldBe(Condition.visible);

//        Given we are on results page
        SelenideElement deal = $(By.className("mainButton"));
        deal.shouldBe(Condition.visible);

//        when deal button is clicked
        deal.click();

        String window = driver.getWindowHandles().iterator().next();
        driver.switchTo().window(window);

        SelenideElement closeSecondPopup = $(By.className("ui_close_x"));
        if (closeSecondPopup.isDisplayed()) {
            closeSecondPopup.click();
        }

        SelenideElement more = $(By.xpath("//span[@class = 'sort_item sort_item_more']/label"));
        more.shouldBe(Condition.visible);
        more.click();

        ElementsCollection subItems = $$(By.className("sub_sort_item"));
        subItems.get(random.nextInt(4)).click();

//        then redirect message should be shown
        updateSearchTitle.shouldBe(Condition.visible);

//        ad price should be visible
        SelenideElement price = $(By.className("price"));
        price.shouldBe(Condition.visible);

        System.out.println("flight details : " + updateSearchTitle.getText());
        System.out.println("Price: " + price.getText());

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
