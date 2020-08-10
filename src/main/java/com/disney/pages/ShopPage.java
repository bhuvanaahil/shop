package com.disney.pages;

import com.disney.base.BasePage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class ShopPage extends BasePage {

    public ShopPage() {
        super();
    }

    public void findSignUp() {
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        WebElement sign_up = driver.findElement(By.xpath("(//span[@class='user-message'])[1]"));
        actions.moveToElement(sign_up).click().perform();
    }

    public void createAcct() {
        driver.switchTo().frame("disneyid-iframe");
        WebElement create_acct = driver.findElement(By.cssSelector("a.btn.btn-secondary.ng-isolate-scope"));
        actions.moveToElement(create_acct).click().perform();
        WebElement firstNameElement = driver.findElement(By.name("firstName"));
        WebElement lastNameElement = driver.findElement(By.name("lastName"));
        WebElement emailElement = driver.findElement(By.name("email"));
        WebElement passwordElement = driver.findElement(By.name("newPassword"));
        WebElement verifyPassElement = driver.findElement(By.name("verifyPassword"));
        WebElement dobElement = driver.findElement(By.name("dateOfBirth"));
        WebElement submitBtn = driver.findElement(By.cssSelector("button.btn.btn-primary.ng-scope.ng-isolate-scope"));
        firstNameElement.sendKeys(prop.getProperty("firstName"));
        lastNameElement.sendKeys(prop.getProperty("lastName"));
        emailElement.sendKeys(prop.getProperty("email"));
        passwordElement.sendKeys(prop.getProperty("passWord"));
        verifyPassElement.sendKeys(prop.getProperty("verifyPassword"));
        dobElement.sendKeys(prop.getProperty("birthDate"));
        submitBtn.click();
    }

    public void captureInfo() throws IOException {
        WebElement firstNameElement = driver.findElement(By.name("firstName"));
        WebElement lastNameElement = driver.findElement(By.name("lastName"));
        WebElement emailElement = driver.findElement(By.name("email"));
        WebElement passwordElement = driver.findElement(By.name("newPassword"));
        WebElement verifyPassElement = driver.findElement(By.name("verifyPassword"));
        WebElement dobElement = driver.findElement(By.name("dateOfBirth"));
        String firstName = firstNameElement.getAttribute("value");
        String lastName = lastNameElement.getAttribute("value");
        String email = emailElement.getAttribute("value");
        String password = passwordElement.getAttribute("value");
        String verifyPassword = verifyPassElement.getAttribute("value");
        String dob = dobElement.getAttribute("value");

        //Create blank workbook
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Section Info");
        int rowCount = 0;

        XSSFRow row = sheet.createRow(++rowCount);
        int columnCount = 0;
        Cell cell1 = row.createCell(++columnCount);
        cell1.setCellValue((firstName));
        Cell cell2 = row.createCell(++columnCount);
        cell2.setCellValue((lastName));
        Cell cell3 = row.createCell(++columnCount);
        cell3.setCellValue((email));
        Cell cell4 = row.createCell(++columnCount);
        cell4.setCellValue((password));
        Cell cell5 = row.createCell(++columnCount);
        cell5.setCellValue((verifyPassword));
        Cell cell6 = row.createCell(++columnCount);
        cell6.setCellValue((dob));
        FileOutputStream out = null;
        try {
            String path = "src/main/resources";
            File file = new File(path);
            String absolutePath = file.getAbsolutePath();
            out = new FileOutputStream(new File(absolutePath + "/login_info.xlsx"));
            workbook.write(out);
        } catch (Exception e) {
            throw e;
        } finally {
            try {
                if (out != null) out.close();
            } catch (IOException e) {
            }
        }
    }

    public void logout() {
        waitLcl(5000);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        WebElement contBtn = driver.findElement(By.cssSelector("button.btn.btn-primary.ng-isolate-scope"));
        contBtn.click();
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        waitLcl(2000);
        //driver.findElement(By.cssSelector("button.user-message > svg > use")).click();
        driver.close();
    }

    public void loginXl(String username, String password) {
        driver.switchTo().frame("disneyid-iframe");
        driver.findElement(By.xpath("(//span[@class = 'input-wrapper']/input)[1]")).sendKeys(username);
        driver.findElement(By.xpath("(//span[@class = 'input-wrapper']/input)[2]")).sendKeys(password);
        driver.findElement(By.xpath("//button[@class='btn btn-primary btn-submit ng-isolate-scope']")).click();

    }

    public void validateInfo(String _firstname, String _lastname, String _email) {
        waitLcl(20000);
        driver.get("https://www.shopdisney.com/secure/account");
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        waitLcl(20000);
        WebElement accountNameElem = driver.findElement(By.cssSelector("div.account-name"));
        String name = accountNameElem.getAttribute("innerText");
        String email = driver.findElement(By.cssSelector("div.account-email")).getAttribute("innerText");
        String fullname = _firstname + " " + _lastname;
        Assert.assertEquals(fullname, name);
        Assert.assertEquals(_email, email);
        driver.close();
    }
}
