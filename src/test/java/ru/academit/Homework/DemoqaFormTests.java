package ru.academit.Homework;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.time.Duration;


public class DemoqaFormTests {
    private WebDriver driver;

    String firstName = "Jane";
    String lastName = "Doe";
    String email = "test@test.com";
    String phoneNumber = "1234567890";
    String address = "Test address";

    File file = new File("src/test/java/ru/academit/Homework/TestIMG.png");


    @BeforeEach
    public void startDriver() {

        String browser = System.getProperty("browser");
        if (browser.equals("chrome")) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions(); //
            options.addArguments("--remote-allow-origins=*"); //Проблема с Хромом версии 111.0.5563.65
            driver = new ChromeDriver(options);
        } else if (browser.equals("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        } else if (browser.equals("edge")) {
            WebDriverManager.edgedriver().setup();
            driver = new EdgeDriver();
        }

        driver.get("https://demoqa.com/automation-practice-form");
        driver.manage().window().maximize();
    }

    @Test

    public void formTests() throws InterruptedException {

        // First Name
        driver.findElement(By.xpath("//*[@id='firstName']")).sendKeys(firstName);

        //Last Name
        driver.findElement(By.xpath("//*[@id='lastName']")).sendKeys(lastName);

        //Email
        driver.findElement(By.xpath("//*[@id='userEmail']")).sendKeys(email);

        //Gender
        WebElement radioGender = driver.findElement(By.xpath("(//*[@id='genterWrapper']//label)[2]"));
        radioGender.click();
        radioGender.isSelected();

        //Phone number
        driver.findElement(By.xpath("//*[@id='userNumber']")).sendKeys(phoneNumber);

        //Birthday
        driver.findElement(By.xpath("//*[@id='dateOfBirthInput']")).click();
        WebElement dataPickerMonth = driver.findElement(By.xpath("//*[contains(@class,'react-datepicker__month-select')]"));
        Select selectMonth = new Select(dataPickerMonth);
        selectMonth.selectByIndex(1);
        WebElement dataPickerYear = driver.findElement(By.xpath("//*[contains(@class,'react-datepicker__year-select')]"));
        Select selectYear = new Select(dataPickerYear);
        selectYear.selectByValue("2000");
        driver.findElement(By.xpath("//*[contains(@class,'react-datepicker__day--015')]")).click();

        //Subjects
        WebElement subject = driver.findElement(By.xpath("//*[@id='subjectsInput']"));
        subject.sendKeys("Arts");
        subject.sendKeys(Keys.RETURN);

        //Hobbies
        driver.findElement(By.xpath("//label[text()='Sports']")).click();

        //Picture
        WebElement uploadFile = driver.findElement(By.xpath("//*[@id='uploadPicture']"));
        uploadFile.sendKeys(file.getAbsolutePath());

        //Address
        driver.findElement(By.xpath("//*[@id='currentAddress']")).sendKeys(address);

        //State and City
        WebElement selectState = driver.findElement(By.xpath("//*[@id='react-select-3-input']"));
        selectState.sendKeys("NCR");
        selectState.sendKeys(Keys.RETURN);

        WebElement selectCity = driver.findElement(By.xpath("//*[@id='react-select-4-input']"));
        selectCity.sendKeys("Delhi");
        selectCity.sendKeys(Keys.RETURN);

        driver.findElement(By.xpath("//*[@id='submit']")).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='example-modal-sizes-title-lg']")));

        // ---- ПРОВЕРКИ ----

        //проверка Student Name
        WebElement student = driver.findElement(By.xpath("//*[contains(@class,'table')]//tbody/tr[1]/td[2]"));
        student.getText();
        System.out.println(student.getText());
        Assertions.assertEquals(firstName + " " + lastName, student.getText());

        //проверка Student Email
        WebElement studentEmail = driver.findElement(By.xpath("//*[contains(@class,'table')]//tbody/tr[2]/td[2]"));
        studentEmail.getText();
        System.out.println(studentEmail.getText());
        Assertions.assertEquals(email, studentEmail.getText());

        //проверка Gender
        WebElement studentGender = driver.findElement(By.xpath("//*[contains(@class,'table')]//tbody/tr[3]/td[2]"));
        studentGender.getText();
        System.out.println(studentGender.getText());
        Assertions.assertEquals("Female", studentGender.getText());

        //проверка Mobile
        WebElement studentPhone = driver.findElement(By.xpath("//*[contains(@class,'table')]//tbody/tr[4]/td[2]"));
        studentPhone.getText();
        System.out.println(studentPhone.getText());
        Assertions.assertEquals(phoneNumber, studentPhone.getText());

        //проверка Date of Birth
        WebElement studentBirthday = driver.findElement(By.xpath("//*[contains(@class,'table')]//tbody/tr[5]/td[2]"));
        studentBirthday.getText();
        System.out.println(studentBirthday.getText());
        Assertions.assertEquals("15 February,2000", studentBirthday.getText());

        //проверка Subjects
        WebElement studentSubject = driver.findElement(By.xpath("//*[contains(@class,'table')]//tbody/tr[6]/td[2]"));
        studentSubject.getText();
        System.out.println(studentSubject.getText());
        Assertions.assertEquals("Arts", studentSubject.getText());

        //проверка Hobbies
        WebElement studentHobbies = driver.findElement(By.xpath("//*[contains(@class,'table')]//tbody/tr[7]/td[2]"));
        studentHobbies.getText();
        System.out.println(studentHobbies.getText());
        Assertions.assertEquals("Sports", studentHobbies.getText());

        //проверка Picture
        WebElement studentPicture = driver.findElement(By.xpath("//*[contains(@class,'table')]//tbody/tr[8]/td[2]"));
        studentPicture.getText();
        System.out.println(studentPicture.getText());
        Assertions.assertEquals("TestIMG.png", studentPicture.getText());

        //проверка Address
        WebElement studentAddress = driver.findElement(By.xpath("//*[contains(@class,'table')]//tbody/tr[9]/td[2]"));
        studentAddress.getText();
        System.out.println(studentAddress.getText());
        Assertions.assertEquals(address, studentAddress.getText());

        //проверка State and City
        WebElement studentState = driver.findElement(By.xpath("//*[contains(@class,'table')]//tbody/tr[10]/td[2]"));
        studentState.getText();
        System.out.println(studentState.getText());
        Assertions.assertEquals("NCR Delhi", studentState.getText());

        Thread.sleep(3000);
    }

    @AfterEach
    public void stopDriver() {

        driver.quit();
    }
}