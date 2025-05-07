package autoProject;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import autoProject.TestComponents.BaseTest;
import autoProject.TestComponents.Retry;
import autoProject.pageobjects.CartPage;
import autoProject.pageobjects.ProductCatalogue;

public class ErrorValidationsTest extends BaseTest{

	@Test(groups = {"ErrorHandling"},retryAnalyzer=Retry.class)
	public void LoginErrorValidation() throws IOException, InterruptedException
	{
		//ExtentReports, ExtentSparkReporter
		
		LandingPage.loginApplication("ekkant1@example.com", "Ek1123456@");
		Assert.assertEquals("Incorrect email or password.", LandingPage.getErrorMessage());
	}
	
	@Test
	public void ProductErrorValidation() throws IOException, InterruptedException
	{
	String productName = "ZARA COAT 3";
	ProductCatalogue productCatalogue = LandingPage.loginApplication("ekkant@example.com", "Ek123456@");
	List<WebElement> products = productCatalogue.getProductList();
	productCatalogue.addProductToCart(productName);
	CartPage cartPage = productCatalogue.goToCartPage();
	Boolean match = cartPage.VerifyProductDisplay("ZARA COAT 33");
	Assert.assertFalse(match);	
	}
}
