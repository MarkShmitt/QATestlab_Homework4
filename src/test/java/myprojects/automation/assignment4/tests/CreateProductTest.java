package myprojects.automation.assignment4.tests;

import myprojects.automation.assignment4.BaseTest;
import myprojects.automation.assignment4.utils.Properties;
import org.openqa.selenium.By;

import org.testng.annotations.Test;

public class CreateProductTest extends BaseTest {


      @Test(dataProvider = "authorization")
    public void createNewProduct(String login, String password) {
        // TODO implement test for product creation
        actions.login(login, password);
        actions.createProduct();
    }


    // TODO implement logic to check product visibility on website
    @Test(dependsOnMethods = "createNewProduct")
    public void checkNewProduct() {
        driver.get(Properties.getBaseUrl());
        driver.findElement(By.cssSelector("a[class = 'all-product-link pull-xs-left pull-md-right h4']")).click();
        actions.checkNewProduct();
    }

     @Test(dependsOnMethods = "checkNewProduct")
     public void checkNewProductOptions() {
        driver.findElement(By.cssSelector(".products.row > article:last-child * h1 > a")).click();
        actions.checkNameNewProduct();
        actions.checkQtyNewProduct();
        actions.checkPriceNewProduct();
    }

}



