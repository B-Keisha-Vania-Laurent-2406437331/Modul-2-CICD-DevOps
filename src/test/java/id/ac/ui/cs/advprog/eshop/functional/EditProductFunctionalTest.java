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

import java.time.Duration;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ExtendWith(SeleniumJupiter.class)
class EditProductFunctionalTest {

    @LocalServerPort
    private int serverPort;

    @Value("${app.baseUrl:http://localhost}")
    private String testBaseUrl;

    private String baseUrl;

    @BeforeEach
    void setupTest() {
        baseUrl = String.format("%s:%d", testBaseUrl, serverPort);
    }

    void createProductForTest(ChromeDriver driver, String name) {
        driver.get(baseUrl + "/product/create");
        driver.findElement(By.id("nameInput")).sendKeys(name);
        driver.findElement(By.id("quantityInput")).sendKeys("10");
        driver.findElement(By.cssSelector("button[type='submit']")).click();
    }

    @Test
    void editProduct_isWorking_andUpdatesList(ChromeDriver driver) throws Exception {

        String initialName = "Produk Salah Nama";
        createProductForTest(driver, initialName);

        driver.get(baseUrl + "/product/list");
        WebElement editButton = driver.findElement(By.xpath("//td[contains(text(), '" + initialName + "')]/following-sibling::td/a[contains(@class, 'btn-warning')]"));
        editButton.click();

        String title = driver.getTitle();
        assertEquals("Edit Product", title);

        WebElement nameInput = driver.findElement(By.id("nameInput"));
        nameInput.clear();
        String newName = "Produk Nama Benar";
        nameInput.sendKeys(newName);

        WebElement quantityInput = driver.findElement(By.id("quantityInput"));
        quantityInput.clear();
        quantityInput.sendKeys("50");

        driver.findElement(By.cssSelector("button[type='submit']")).click();

        String pageSource = driver.getPageSource();

        assertTrue(pageSource.contains(newName));
        assertTrue(pageSource.contains("50"));

        assertFalse(pageSource.contains(initialName));
    }
}