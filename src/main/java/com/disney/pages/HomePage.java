package com.disney.pages;

import com.disney.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class HomePage extends BasePage {

    public HomePage() {
        super();
    }

    public void findShop() {
        waitLcl(10000);
        WebElement shop_element = driver.findElement(By.cssSelector("#goc-bar-left > li:nth-child(3) > a > u"));
        shop_element.click();
        driver.manage().window().maximize();
    }
}