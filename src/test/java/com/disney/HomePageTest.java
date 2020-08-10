package com.disney;

import com.disney.base.BasePage;
import com.disney.pages.HomePage;
import com.disney.pages.ShopPage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

public class HomePageTest extends BasePage {
    HomePage homePage = new HomePage();
    ShopPage shopPage = new ShopPage();


    public HomePageTest() {
        super();
    }

    @BeforeMethod
    public void setUp() {
        initialization();
    }

     @Test
    public void findShopTest() throws Exception {
        homePage.findShop();
        shopPage.findSignUp();
        shopPage.createAcct();
        shopPage.captureInfo();
        shopPage.logout();

    }

    @Test(dependsOnMethods = { "findShopTest" })
    public void loginTest() {
        homePage.findShop();
        shopPage.findSignUp();

        File file = new File("src/main/resources/login_info.xlsx");
        FileInputStream xlfile = null;
        String username = "";
        String password = "";
        String firstname = "";
        String lastname = "";
        String email = "";
        try {
            xlfile = new FileInputStream(file);
            XSSFWorkbook xlwb = new XSSFWorkbook(xlfile);
            XSSFSheet datatypeSheet = xlwb.getSheetAt(0);
            // Get the number of rows and columns
            Iterator<Row> iterator = datatypeSheet.iterator();

            while (iterator.hasNext()) {
                Row currentRow = iterator.next();
                Cell cell1 = currentRow.getCell(1);
                Cell cell2 = currentRow.getCell(2);
                Cell cell3 = currentRow.getCell(3);
                Cell cell4 = currentRow.getCell(4);

                firstname = cell1.getStringCellValue();
                lastname = cell2.getStringCellValue();
                email = cell3.getStringCellValue();
                username = email;
                password = cell4.getStringCellValue();

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (xlfile != null) try {
                xlfile.close();
            } catch (IOException e) {
            }
        }

        shopPage.loginXl(username, password);
        shopPage.validateInfo(firstname, lastname, email);
    }

    @AfterMethod
    public void tearDown() {
        //driver.quit();
    }


}