package mail.yandex;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class TestMail {
    public static LoginPage loginPage;
    public static MailPage mailPage;
    public static WebDriver driver;
    static String theme = "Simbirsoft theme";
    static Integer beforeSize;
    static Integer afterSize;

    @Before
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        driver = new ChromeDriver();
        loginPage = new LoginPage(driver);
        mailPage = new MailPage(driver);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
        driver.get("https://mail.yandex.ru/");
        JavascriptExecutor js = (JavascriptExecutor) driver;
    }

    public void testSetUp() throws MalformedURLException {
        String Node = "http://localhost:4444";
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setBrowserName("chrome");
        driver = new RemoteWebDriver(new URL(Node), caps);
    }

    @Test
    public void sendingMessage() throws InterruptedException {
        loginPage.cliclMail()
                .inputLogin(PropertyManager.getInstance().getAddressmail())
                .clickSubmit()
                .inputPassword(PropertyManager.getInstance().getPassword())
                .clickSubmit();
        mailPage.fiterByTheme("Simbirsoft Тестовое задание папка:Входящие");

        beforeSize = mailPage.getNumSize();
        mailPage.clickNew()
                .sendAddress(PropertyManager.getInstance().getAddressmail())
                .sendTheme("Simbirsoft Тестовое задание Ативанов")
                .sendText("Найдено ")
                .sendMessage()
    }

    @After
    public void after() {
        driver.quit();

    }
}