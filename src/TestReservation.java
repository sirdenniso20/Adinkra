import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class TestReservation {
	private WebDriver driver;
	private WebElement element;
			WebDriverWait wait;
			

	
	@BeforeMethod
	public void SetUp() {
		System.setProperty("webdriver.chromedriver", "C:/work/chromedriver.exe");
		driver = new ChromeDriver();
		//driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		//driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.get("http://adinkratransport.com/reservation/");

	}

	@Test
	public void verifyReservationPageTitleTest(){
		
		String title = driver.getTitle();
		System.out.println(" The page Title is: " + title);
		Assert.assertEquals(title, "Reservation – Adinkra Transport Services");
	}

	@Test
	public void verifytNameAndEmailConstraintTest() throws IOException {

		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream("E:\\Java_Projects_Practice\\AdinkraTransports\\src\\Test\\datadriven.properties");
		prop.load(fis);
		
	WebDriverWait d= new WebDriverWait(driver, 5);
		driver.findElement(By.name("please-Enter-Passenger-Name")).sendKeys(prop.getProperty("name"));
		driver.findElement(By.xpath("//input[@name='your-email']")).sendKeys(prop.getProperty("email"));
		driver.findElement(By.xpath("//input[@value='Send']")).sendKeys(Keys.RETURN);
		d.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='wpcf7-response-output wpcf7-display-none wpcf7-mail-sent-ok']")));
		WebElement messageSent = driver.findElement(By.xpath("//div[@class='wpcf7-response-output wpcf7-display-none wpcf7-mail-sent-ok']"));
		String sent = messageSent.getText();
		System.out.println(sent) ;
		}
	
	@Test
	public void verifyErrorMessageTest() throws IOException
	{
		
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream("E:\\Java_Projects_Practice\\AdinkraTransports\\src\\Test\\datadriven.properties");
		prop.load(fis);
		
		WebDriverWait d= new WebDriverWait(driver, 5);
		
		driver.findElement(By.name("please-Enter-Passenger-Name")).sendKeys("fdsfsd");
		driver.findElement(By.xpath("//input[@name='your-email']")).sendKeys(prop.getProperty("name"));
		driver.findElement(By.xpath("//input[@value='Send']")).sendKeys(Keys.RETURN);
		d.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='wpcf7-response-output wpcf7-display-none wpcf7-validation-errors']")));
		WebElement errrMssage = driver.findElement(By.xpath("//div[@class='wpcf7-response-output wpcf7-display-none wpcf7-validation-errors']"));
		String err = errrMssage.getText();
		System.out.println(err) ;
		Assert.assertEquals(err, "One or more fields have an error. Please check and try again.");
	}
	
	@Test
	public void verifyTypeOfServiceTest()
	{
	
		WebElement clickOn=	driver.findElement(By.xpath("//select[@name='menu-551']"));
		clickOn.sendKeys(Keys.RETURN);
		String ServiceRequired = driver.findElement(By.xpath("//option[@value='Hourly Transportation']")).getText();
		System.out.println(ServiceRequired);
		Assert.assertEquals(ServiceRequired, "Hourly Transportation");
		
	}

	@AfterMethod
	public void tearDown() {
		driver.quit();
	}

}


