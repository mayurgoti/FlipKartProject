package flipkart;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import flipkart.utility.TestUtility;

public class LoopOrderTest {
	
	WebDriver driver;
	
	@BeforeMethod
	public void setUp() throws InterruptedException {
		System.setProperty("webdriver.gecko.driver", "C:\\SeleniumJars\\geckodriver.exe");
		driver = new FirefoxDriver();
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
		driver.get("https://www.flipkart.com/");
		//login popup
		List<WebElement> popUp = driver.findElements(By.xpath("//button[contains(text(),'✕')]"));
		System.out.println("at Login Page pop up size: "+popUp.size());
		if(popUp.size()>0) {
			popUp.get(0).click();
			}		
	}

	@AfterMethod
	public void tearDown() {
		driver.switchTo().defaultContent();
		driver.manage().deleteAllCookies();
		driver.quit();	
	}
	
	@DataProvider
	public Iterator<Object[]> getLoginData() {
		ArrayList<Object[]> testData = TestUtility.getDataFromExcel();
		return testData.iterator();
	}


	@Test(dataProvider = "getLoginData")
	public void loopOrderTest(String emailOrMobile, String password) throws InterruptedException, IOException {
		
		//user login
				driver.findElement(By.linkText("Login")).click();
				driver.findElement(By.xpath("//div[@class='IiD88i _351hSN']//input[@type='text']")).sendKeys(emailOrMobile);
				driver.findElement(By.xpath("//input[@type='password']")).sendKeys(password);
				driver.findElement(By.xpath("//div[@class='_1D1L_j']//button[@type='submit']")).click();
				
				WebElement flipkart;
				JavascriptExecutor jse = (JavascriptExecutor)driver;
				
				do {
					Set<String> allwindows;
					
				flipkart = driver.findElement(By.xpath("//img[@title='Flipkart']"));
				jse.executeScript("arguments[0].click();", flipkart);
				Thread.sleep(2000);
				//search item
				driver.findElement(By.name("q")).sendKeys("TAPARIA 903IBT and 905 I and 813 and ws-06 pack of 4(long and short 2 in 1 magnetic screwdriver with tester and wire cutter pack of 4) Hand Tool Kit  (4 Tools)");
				
				WebElement searchButton = driver.findElement(By.className("L0Z3Pu"));
				jse.executeScript("arguments[0].click();", searchButton);
				
				//item selection
				WebElement item = driver.findElement(By.xpath("//a[contains(text(),'TAPARIA 903IBT and 905 I and 813 and ws-06 pack of 4(lo...')]"));
				jse.executeScript("arguments[0].click();", item);
				
				//switching to different window
				allwindows = driver.getWindowHandles();
				System.out.println("all window count: "+allwindows.size());
				Iterator<String> iter = allwindows.iterator();
				String parentWindow = iter.next();
				String childWindow = iter.next();
				System.out.println(parentWindow);
				System.out.println(childWindow);
				driver.switchTo().window(childWindow);
				
				Thread.sleep(5000);
				
				//add to cart
				driver.findElement(By.xpath("//ul[@class='row']//li[1]/button")).click();
				System.out.println("add to cart");
				
				
				//place order or buy now
				WebElement placeOrder = driver.findElement(By.xpath("//span[contains(text(),'Place Order')]"));
				jse.executeScript("arguments[0].click();", placeOrder);

				//deliver here
				//WebElement deliverHere = driver.findElement(By.xpath("//button[contains (text(), 'Deliver Here')]"));
				//jse.executeScript("arguments[0].click();", deliverHere);
				
				
				WebElement deliverHere = driver.findElement(By.xpath("//button[contains(text(),'CONTINUE')]"));
			    jse.executeScript("arguments[0].click();", deliverHere);
				
				WebElement pay = driver.findElement(By.id("to-payment"));
				jse.executeScript("arguments[0].click();", pay);
				Thread.sleep(2000);
				WebElement cashOption = driver.findElement(By.id("COD"));
				jse.executeScript("arguments[0].click();", cashOption);
				
				boolean b = cashOption.isSelected();
				if(!b)
					System.out.println("COD Disabled");
				
				//captcha screenshot
				Thread.sleep(2000);
				File srcFile = driver.findElement(By.xpath("//img[@class='AVFlbS']")).getScreenshotAs(OutputType.FILE);
				String path = System.getProperty("user.dir")+"/screenshots/captcha.png";
				FileHandler.copy(srcFile, new File(path));
				Thread.sleep(3000);

				//read captcha
				DetectText dt = new DetectText();
				String filePath = System.getProperty("user.dir")+"/screenshots/captcha.png";
				dt.detectText(filePath);
				String imgtext = dt.myDigits;
				
				System.out.println(imgtext);
				Thread.sleep(2000);
				driver.findElement(By.name("captcha")).sendKeys(imgtext);
				
				//order conform
				WebElement confirmOrder = driver.findElement(By.xpath("//span[contains(text(),'Confirm Order')]"));
				if(!confirmOrder.isDisplayed())
					break;
				confirmOrder.click();
				driver.switchTo().window(parentWindow);
				allwindows.clear();
				//driver.close();
				Thread.sleep(2000);
				flipkart = driver.findElement(By.xpath("//img[@title='Flipkart']"));
				jse.executeScript("arguments[0].click();", flipkart);
				}while (flipkart.isDisplayed());
		
	}

}
