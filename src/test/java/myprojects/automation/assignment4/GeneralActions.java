package myprojects.automation.assignment4;


import myprojects.automation.assignment4.model.ProductData;
import myprojects.automation.assignment4.utils.Properties;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;


/**
 * Contains main script actions that may be used in scripts.
 */
public class GeneralActions {
    private WebDriver driver;
    private WebDriverWait wait;
    public ProductData newProduct = ProductData.generate();

    public GeneralActions(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, 30);
    }


    /**
     * Logs in to Admin Panel.
     * @param login
     * @param password
     */
    public void login(String login, String password) {
        // TODO implement logging in to Admin Paneld
        driver.get(Properties.getBaseAdminUrl());
        driver.findElement(By.id("email")).sendKeys(login);
        driver.findElement(By.id("passwd")).sendKeys(password);
        driver.findElement(By.name("submitLogin")).click();
        if (login == null || password == null) {
            throw new UnsupportedOperationException();
        }
    }

    public void createProduct() {
        // TODO implement product creation scenario
        waitForContentLoad(By.id("nav-sidebar"));

        Actions builder = new Actions(driver);
        builder.moveToElement(driver.findElement(By.id("subtab-AdminCatalog")));
        builder.moveToElement(driver.findElement(By.id("subtab-AdminProducts")));
        builder.click(driver.findElement(By.id("subtab-AdminProducts"))).perform();

        waitForContentLoad(By.id("product_catalog_list"));

        driver.findElement(By.id("page-header-desc-configuration-add")).click();

        waitForContentLoad(By.id("ps_categoryTags"));

        driver.findElement(By.id("form_step1_name_1")).sendKeys(newProduct.getName());
        driver.findElement(By.id("form_step1_qty_0_shortcut")).sendKeys(String.valueOf(newProduct.getQty()));
        driver.findElement(By.id("form_step1_price_ttc_shortcut")).sendKeys(String.valueOf(newProduct.getPrice()));
        driver.findElement( By.className("switch-input ")).click();

        waitForContentLoad(By.className("growl-message"));

        driver.findElement(By.cssSelector("button[class='btn btn-primary js-btn-save']")).click();

        waitForContentLoad(By.className("growl-message"));

       if (newProduct.getName() == null || newProduct.getQty() == 0 || newProduct.getPrice() == null) {
            throw new UnsupportedOperationException();
        }
    }
    public void checkNewProduct() {
        waitForContentLoad(By.cssSelector("section[id='products']"));
        Assert.assertTrue(driver.findElement(By.cssSelector(".products.row > article:last-child * h1 > a"))
                .getText().contains(newProduct.getName()));
        }
    public void checkNameNewProduct() {
        Assert.assertEquals(driver.findElement(By.cssSelector("h1[class='h1']")).getText(), newProduct.getName().toUpperCase());
    }
    public void checkQtyNewProduct() {
        Assert.assertEquals(driver.findElement(By.xpath("//div[@class='product-quantities']/span"))
                .getText(), newProduct.getQty() + " Товары");
    }
    public void checkPriceNewProduct() {
        Assert.assertEquals(driver.findElement(By.cssSelector("span[content]"))
                .getText(), newProduct.getPrice() + " ₴");
    }

    /**
     * Waits until page loader disappears from the page
     */
    public void waitForContentLoad(By locator) {
        // TODO implement generic method to wait until page content is loaded

         wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
        // ...
    }
}
