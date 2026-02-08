package id.ac.ui.cs.advprog.eshop.functional;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ExtendWith(SeleniumJupiter.class)
class CreateProductFunctionalTest {

    @LocalServerPort
    private int serverPort;

    @Value("${app.baseUrl:http://localhost}")
    private String testBaseUrl;

    private String baseUrl;

    @BeforeEach
    void setupTest() {
        baseUrl = String.format("%s:%d", testBaseUrl, serverPort);
    }

    @Test
    void createProductPage_titleIsCorrect(ChromeDriver driver) throws Exception {
        driver.get(baseUrl + "/product/create");
        String pageTitle = driver.getTitle();

        assertEquals("Create New Product", pageTitle);
    }

    @Test
    void createProductForm_elementsAreDisplayed(ChromeDriver driver) throws Exception {
        driver.get(baseUrl + "/product/create");

        WebElement nameInput = driver.findElement(By.id("nameInput"));
        assertTrue(nameInput.isDisplayed());

        WebElement quantityInput = driver.findElement(By.id("quantityInput"));
        assertTrue(quantityInput.isDisplayed());

        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));
        assertTrue(submitButton.isDisplayed());
    }

    @Test
    void createProduct_isWorking_andAppearsInList(ChromeDriver driver) throws Exception {
        driver.get(baseUrl + "/product/create");

        // input
        driver.findElement(By.id("nameInput")).sendKeys("Sampo Cap Bambang");
        driver.findElement(By.id("quantityInput")).sendKeys("10");

        // submit
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.contains("/product/list"));

        String pageSource = driver.getPageSource();
        assertTrue(pageSource.contains("Sampo Cap Bambang"));
        assertTrue(pageSource.contains("10"));
    }
}