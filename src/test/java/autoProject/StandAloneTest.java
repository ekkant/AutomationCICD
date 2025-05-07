package autoProject;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import autoProject.pageobjects.LandingPage;
import io.github.bonigarcia.wdm.WebDriverManager;

public class StandAloneTest {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		//New Comment is added for Demo pupose
		
		//System.setProperty("webdriver.chrome.driver", "C:\\Users\\ekkant\\Downloads\\chromedriver\\chromedriver.exe");
		String productName = "ZARA COAT 3";
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get("https://rahulshettyacademy.com/client");
		LandingPage LandingPage = new LandingPage(driver);
		driver.findElement(By.id("userEmail")).sendKeys("ekkant@example.com");
		driver.findElement(By.id("userPassword")).sendKeys("Ek123456@");
		driver.findElement(By.id("login")).click();
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mb-3")));
		List<WebElement> products = driver.findElements(By.cssSelector(".mb-3"));
		
		WebElement prod = products.stream().filter(product->product.findElement(By.cssSelector("b")).getText().equals(productName)).findFirst().orElse(null);
		prod.findElement(By.cssSelector(".card-body button:last-of-type")).click();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#toast-container")));
		//wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".ng-animating")));
		wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animating"))));
		driver.findElement(By.cssSelector("[routerlink*='cart']")).click();
		
		List<WebElement> cartProducts = driver.findElements(By.cssSelector(".cartSection h3"));
		Boolean match = cartProducts.stream().anyMatch(cartProduct->cartProduct.getText().equals(productName));
		Assert.assertTrue(match);
		driver.findElement(By.cssSelector(".totalRow button")).click();
		
		Actions a = new Actions(driver);
		a.sendKeys(driver.findElement(By.cssSelector("[placeholder='Select Country']")),"georgia").build().perform();
		//driver.findElement(By.xpath("//input[@placeholder='Select Country']")).sendKeys("Georgia");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ta-results")));
		driver.findElement(By.xpath("(//button[@type='button'])[1]")).click();
		//driver.findElement(By.xpath("//a[text()='Place Order ']")).click();
		//Scroll down to click - button was not visible and I got failed test
		JavascriptExecutor js = (JavascriptExecutor)driver;
		//js.executeScript("window.scrollBy(0,800)");
		//Somehow one scroll script was not enough and I run it twice to get the result.
		js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
		Thread.sleep(1000);
		js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
		
		driver.findElement(By.cssSelector(".action__submit")).click();
		String successText = driver.findElement(By.cssSelector(".hero-primary")).getText();
		Assert.assertTrue(successText.equalsIgnoreCase("Thankyou for the order."));
		driver.close();
	}

}
