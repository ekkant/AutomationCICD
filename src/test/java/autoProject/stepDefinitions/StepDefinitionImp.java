package autoProject.stepDefinitions;

import autoProject.TestComponents.BaseTest;
import autoProject.pageobjects.CartPage;
import autoProject.pageobjects.CheckoutPage;
import autoProject.pageobjects.ConfirmationPage;
import autoProject.pageobjects.LandingPage;
import autoProject.pageobjects.ProductCatalogue;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class StepDefinitionImp extends BaseTest {
	
	public LandingPage landingPage;
	public ProductCatalogue productCatalogue;
	public ConfirmationPage confirmationPage;
	
	@Given("I landed on Ecommerce Page")
	public void I_landed_on_Ecommerce_Page() throws IOException
	{
		landingPage = launchApplication();
	}
	
	@Given("^Logged in with username (.+) and password (.+)$")
	public void logged_in_username_and_password(String username, String password)
	{
		productCatalogue = LandingPage.loginApplication(username, password);
	}
	@When("^I added product (.+) to Cart$")
	public void i_add_product_to_cart(String productName)
	{
		List<WebElement> products = productCatalogue.getProductList();
		productCatalogue.addProductToCart(productName);
	}
	@And("^Checkout (.+) and submit the order$")
	public void checkout_submit_order(String productName) throws InterruptedException
	{
CartPage cartPage = productCatalogue.goToCartPage();
		
		Boolean match = cartPage.VerifyProductDisplay(productName);
		Assert.assertTrue(match);	
		CheckoutPage checkoutPage = cartPage.goToCheckout();
		checkoutPage.selectCountry("Georgia");
		//Scroll down twice
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
		Thread.sleep(2000);
		js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
		
		confirmationPage = checkoutPage.submitOrder();
	}
	@Then("{String} is displayed on ConfirmationPage")
	public void message_displayed_confirmation_Page(String string)
	{
		String confirmMessage = confirmationPage.getConfirmationMessage();
		Assert.assertTrue(confirmMessage.equalsIgnoreCase(string));
		driver.close();
	}
	@Then("{String} message is displayed")
	public void something_message_is_displayed(String strArg1) throws Throwable 
	{
		Assert.assertEquals(strArg1, LandingPage.getErrorMessage());
		driver.close();
	}
}
