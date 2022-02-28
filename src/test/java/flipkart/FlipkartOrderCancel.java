package flipkart;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

public class FlipkartOrderCancel {

	public static void main(String[] args) throws InterruptedException {
		
		System.setProperty("webdriver.gecko.driver", "C:\\SeleniumJars\\geckodriver.exe");
		FirefoxDriver driver = new FirefoxDriver();
		driver.manage().window().maximize();
		
		driver.get("https://www.flipkart.com/");
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
		Thread.sleep(2000);
		//login popup
		List<WebElement> popUp = driver.findElements(By.xpath("//button[contains(text(),'✕')]"));
		System.out.println("pop up size: "+popUp.size());
		if(popUp.size()>0) {
			popUp.get(0).click();
		}
		
		//user login
		driver.findElement(By.linkText("Login")).click();
		driver.findElement(By.xpath("//div[@class='IiD88i _351hSN']//input[@type='text']")).sendKeys("9033457948");
		driver.findElement(By.xpath("//input[@type='password']")).sendKeys("9825953665");
		driver.findElement(By.xpath("//div[@class='_1D1L_j']//button[@type='submit']")).click();
		
		WebElement flipkart;
		JavascriptExecutor jse = (JavascriptExecutor)driver;
			
		flipkart = driver.findElement(By.xpath("//img[@title='Flipkart']"));
		jse.executeScript("arguments[0].click();", flipkart);
		Thread.sleep(2000);
		
		// hover over my profile
		WebElement myOrder = driver.findElement(By.xpath("(//div[@class='exehdJ'])[1]"));
		Actions builder = new Actions(driver);
		builder.moveToElement(myOrder).build().perform();
		Thread.sleep(3000);
		
		WebElement orderButton = driver.findElement(By.linkText("Orders"));
		jse.executeScript("arguments[0].click();", orderButton);
		
		List<WebElement> allPendingOrders = driver.findElements(By.xpath("//span[contains(text(),'TAPARIA BS 31 Impact Screwdriver Set')]"));
		System.out.println(allPendingOrders.size());
		
		//
		for(WebElement a:allPendingOrders) {
		
			//allPendingOrders.get(0).click();
			a.click();
			List<WebElement> cancelOrders = driver.findElements(By.className("_2LAzKy"));
		
			if(cancelOrders.size()>0) {
			WebElement cancelOrder = driver.findElement(By.className("_2LAzKy"));
			jse.executeScript("arguments[0].click();", cancelOrder);
			
			Thread.sleep(2000);
			WebElement dropdown = driver.findElement(By.name("reasonList"));
			Select s = new Select(dropdown);
			
			s.selectByValue("mind_changed");//I have changed my mind
			Thread.sleep(2000);
			
			WebElement continueCancel = driver.findElement(By.xpath("//span[contains(text(),'CONTINUE')]"));
			jse.executeScript("arguments[0].click();", continueCancel);
			
			//WebElement reason = driver.findElement(By.xpath("(//input[@name='cancellation-reason'])[2]"));
			WebElement reason = driver.findElement(By.id("COD"));
			jse.executeScript("arguments[0].click();", reason);
			
			//WebElement requestCancel = driver.findElement(By.xpath("(//span[contains (text(), 'Request Cancellation')])[2]"));
			WebElement requestCancel = driver.findElement(By.className("Gu5wxT"));
			jse.executeScript("arguments[0].click();", requestCancel);
			
			Thread.sleep(2000);
			
			myOrder = driver.findElement(By.xpath("(//div[@class='exehdJ'])[1]"));
			builder = new Actions(driver);
			builder.moveToElement(myOrder).build().perform();
			Thread.sleep(3000);
			
			orderButton = driver.findElement(By.linkText("Orders"));
			jse.executeScript("arguments[0].click();", orderButton);
			}
			else {
				myOrder = driver.findElement(By.xpath("(//div[@class='exehdJ'])[1]"));
				builder = new Actions(driver);
				builder.moveToElement(myOrder).build().perform();
				Thread.sleep(3000);
				orderButton = driver.findElement(By.linkText("Orders"));
				jse.executeScript("arguments[0].click();", orderButton);
			}
		}

	}

}
