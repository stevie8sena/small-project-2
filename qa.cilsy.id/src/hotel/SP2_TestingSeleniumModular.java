package hotel;

import java.sql.Driver;
import java.util.Random;
import java.util.UUID;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Sleeper;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

/**
 * @author Rasyid Sholeh Rosena
 *
 */

public class SP2_TestingSeleniumModular {
	public static String browser;
	static WebDriver driver;

	// Extent report
	// static ExtentTest testing;
	// static ExtentReports report;

	// Search Button ID on Homepage
	String btnSearchID = "search_room_submit";

	// Email for registration and Login
	String mail = "mariachiii@i-love-you-3000.net";

	//	Random randomGenerator = new Random();  
	//	int randomInt = randomGenerator.nextInt(1000);  
	//	sendKeys("mariachiii"+ randomInt +"i-love-you-3000.net"); 

	public static void main(String[] args) throws InterruptedException {
		System.out.println("Test start... \n");

		SP2_TestingSeleniumModular test = new SP2_TestingSeleniumModular();
		test.setBrowser("Firefox");
		test.setBrowserConfig();
		test.changeLang();
		test.emptySearch();
		test.fillSearchHotel();
		test.bookProcess();
		test.leftGuestInfo();
		test.fillGuestInfo();
		test.paymentStep();
		test.logoutAcc();
		test.loginAccInvalid();
		test.loginAcc();
		test.updateMyAddress();

		test.logoutAcc();
		driver.quit();
		System.out.println("Test finished. \n");

	}

	public void setBrowser(String browser) {
		this.browser = browser;
	}

	public void setBrowserConfig() {
		String projectLocation = System.getProperty("user.dir");

		if (browser.contains("Firefox")) {
			System.setProperty("webdriver.gecko.driver", projectLocation + "\\lib\\driver\\geckodriver.exe");
			driver = new FirefoxDriver();
		}
	}

//	@BeforeClass
//	public static void startTest() {
//		report = new ExtentReports(System.getProperty("user.dir") + "\\report\\QA Test Report.html");
//		testing = report.startTest("QA SP 2 Testing");
//	}

	@Test
	// TC01
	public void changeLang() {
		driver.manage().window().maximize();
		driver.get("http://qa.cilsy.id/");
		String expectedTitle = "QA";
		String actualTitle = driver.getTitle();

		String indoLang = "//a[@href='http://128.199.90.160/id/']";

		driver.findElement(By.xpath("//div[@class='current']")).click();
		driver.findElement(By.xpath(indoLang)).click();

		try {
			Assert.assertEquals(actualTitle, expectedTitle);
			System.out.println("Title web page verified. Language now in Indonesian. \n");
		} catch (AssertionError e) {
			System.out.println("Title web page not verified. \n");
			driver.close();
		}
	}

	// TC02
	public void emptySearch() {
		driver.findElement(By.id(btnSearchID)).click();
		if (driver.findElement(By.cssSelector(".error_border")).isDisplayed()) {
			System.out.println("Search failed successfully. Red border is displayed. \n");
		} else {
			System.out.println("Test failed. \n");
		}
	}

	// TC03
	public void fillSearchHotel() throws InterruptedException {
		String hotelLocID = "hotel_location";
		String selectHotelID = "id_hotel_button";
		String hotelNameXPath = "/html/body/div[1]/div[1]/header/div[5]/div/div/div[2]/div/form/div[2]/div/ul/li";
		String checkInTimeID = "check_in_time";
		String checkOutTimeID = "check_out_time";

		driver.findElement(By.id(hotelLocID)).sendKeys("Indonesia");
		driver.findElement(By.id(selectHotelID)).click();
		driver.findElement(By.xpath(hotelNameXPath)).click();
		driver.findElement(By.id(checkInTimeID)).sendKeys("01-01-2022");
		driver.findElement(By.id(checkOutTimeID)).sendKeys("08-01-2022");

		driver.findElement(By.id(btnSearchID)).click();
		// Select shID = new Select(driver.findElement(By.id(selectHotelID)));
		// shID.selectByVisibleText("The Hotel Prime");

		Thread.sleep(5000); // 5sec

		String expTitleSearch = "The Hotel Prime - QA";
		String testTitleSearch = driver.getTitle();

		try {
			Assert.assertEquals(testTitleSearch, expTitleSearch);
			System.out.println("Search for hotel success. Navigate to result. \n");
		} catch (AssertionError e) {
			System.out.println("Search failed. \n");
		}

	}

	// TC04
	public void bookProcess() throws InterruptedException {
		String sortBoxPriceID = "price_ftr";
		// String highestFirstValue = "2";
		String highestFirstXpath = "/html/body/div[1]/div[2]/div/div[2]/div[2]/div/div/div[1]/div[3]/div/ul/li[2]/a";
		String btnPlusXpath = "/html/body/div[1]/div[2]/div/div[2]/div[2]/div/div/div[2]/div[1]/div/div[2]/div[4]/div/div[2]/a[1]/span/i";
		String btnBookNowXpath = "//body[@id='category']/div[@id='page']/div[@class='columns-container']/div[@id='columns']/div[@class='row']/div[@id='center_column']/div[@class='row cat_cont']/div[@class='col-sm-12']/div[@id='category_data_cont']/div[1]/div[1]/div[2]/a[1]/span[1]";

		String btnBelanjaLagiXpath = "//body[@id='category']/div[@id='page']/div[@class='header-container']/header[@id='header']/div/div[@class='container']/div[@class='row']/div[@id='layer_cart']/div[@class='clearfix']/div[@class='layer_cart_cart col-xs-12 col-md-6']/div[@class='button-container']/span[@class='continue btn btn-default button exclusive-medium']/span[1]";

		driver.findElement(By.id(sortBoxPriceID)).click();
		driver.findElement(By.xpath(highestFirstXpath)).click();

		Thread.sleep(2000);

		// Change QTY to 2
		driver.findElement(By.xpath(btnPlusXpath)).click();

		driver.findElement(By.xpath(btnBookNowXpath)).click();
		Thread.sleep(2000);

		driver.findElement(By.xpath(btnBelanjaLagiXpath)).click();

		String roomLeftTextXpath = "/html/body/div[1]/div[2]/div/div[2]/div[2]/div/div/div[2]/div[1]/div/div[2]/span";
		WebElement roomLeft = driver.findElement(By.xpath(roomLeftTextXpath));

		if (roomLeft.getText().equals("Hurry! 5 rooms left")) {
			System.out.println("Room left not updated. \n");
		} else {
			System.out.println("Room left has updated. \n");
		}

		// Click Cart Selesai
		driver.findElement(By.xpath("/html/body/div[1]/div[1]/header/div[3]/div/div/div[4]/div/a")).click();

		String expTitleCO = "Order - QA";
		String testTitleCO = driver.getTitle();
		String btnProceed = "/html/body/div/div[2]/div/div[2]/div/section/div/section/div/div[1]/div/div[1]/div[2]/div/div[2]/div[2]/div/a";

		try {
			Assert.assertEquals(testTitleCO, expTitleCO);
			System.out.println("Check out cart success. Move to next step. \n");
			driver.findElement(By.xpath(btnProceed)).click();
		} catch (AssertionError e) {
			System.out.println("Check out cart failed. \n");
		}
	}

	// TC05
	public void leftGuestInfo() {
		String btnSubmit = "submitAccount";
		driver.findElement(By.id(btnSubmit)).click();

		if (driver.getPageSource().contains("There is 5 Error")) {
			System.out.println("Test error success. Please fill Guest Information. \n");
		} else {
			System.out.println("Test error failed. \n");
		}

	}

	// TC06
	public void fillGuestInfo() throws InterruptedException {
		String nonaValue = "2";
		String tuanValue = "1";

		Select socialTitle = new Select(driver.findElement(By.id("id_gender")));
		socialTitle.selectByValue(nonaValue);

		driver.findElement(By.id("customer_firstname")).sendKeys("Maria");
		driver.findElement(By.id("customer_lastname")).sendKeys("Chi");
		driver.findElement(By.id("email")).sendKeys(mail);
		driver.findElement(By.id("passwd")).sendKeys("12345");
		driver.findElement(By.id("phone_mobile")).sendKeys("087654321");

		driver.findElement(By.id("submitAccount")).click();

		Thread.sleep(1000);

		if (driver.getPageSource().contains(mail)) {
			System.out.println("Email success registered. \n");
		} else {
			System.out.println("Register failed. \n");
		}

		String btnProceed = "/html/body/div/div[2]/div/div[2]/div/section/div/section/div/div[1]/div/div[2]/div[2]/div/div[4]/div/a/span";
		driver.findElement(By.xpath(btnProceed)).click();
	}

	// TC07
	public void paymentStep() throws InterruptedException {
		driver.findElement(By.id("cgv")).click();
		Thread.sleep(1000);

		driver.findElement(By.partialLinkText("Bayar via Transfer Bank")).click();

		String urlPaymentSum = driver.getCurrentUrl();

		try {
			Assert.assertEquals(urlPaymentSum, "http://128.199.90.160/id/module/bankwire/payment");
			System.out.println("Payment selected. Go to next step. \n");
			driver.findElement(By.cssSelector("button[type=submit]")).click();
			if (driver.getPageSource().contains("Pembelian Anda pada QA telah selesai.")) {
				System.out.println("Booking hotel success. \n");
			} else {
				System.out.println("Booking hotel fail. \n");
			}

		} catch (AssertionError e) {
			System.out.println("Payment not selected. \n");
		}

		String orderReference = driver.findElement(By.xpath("/html/body/div/div[2]/div/div[2]/div/div[1]/p[4]/span"))
				.getText();
		System.out.println("My order reference: " + orderReference);
		driver.findElement(By.partialLinkText("View your order history")).click();

		String orderReferenceHistory = driver
				.findElement(By.xpath("/html/body/div/div[2]/div/div[2]/div/div/table/tbody/tr/td[1]/a")).getText();
		System.out.println("My order reference history: " + orderReferenceHistory);

		try {
			Assert.assertEquals(orderReference, orderReferenceHistory);
			System.out.println("Order and payment finish. \n");
		} catch (AssertionError e) {
			System.out.println("Order and payment fail. \n");
		}

	}

	// TC08
	public void logoutAcc() throws InterruptedException {
		driver.findElement(By.id("user_info_acc")).click();
		Thread.sleep(1000);

		driver.findElement(By.partialLinkText("Logout")).click();

		String expLogoutTitle = "Login - QA";
		String testLogoutTitle = driver.getTitle();

		try {
			Assert.assertEquals(testLogoutTitle, expLogoutTitle);
			System.out.println("Account logged out. \n");
		} catch (AssertionError e) {
			System.out.println("Fail log out. \n");
		}

	}

	// TC09
	public void loginAccInvalid() {
//		String urlLogin1 = "http://128.199.90.160/id/login?back=history";
//		String urlLogin2 = "http://128.199.90.160/id/login?back=my-account";
//
//		driver.navigate().to(urlLogin1);

		driver.findElement(By.id("email")).sendKeys(mail);
		driver.findElement(By.id("passwd")).sendKeys("1234");
		driver.findElement(By.id("SubmitLogin")).click();
		if (driver.getPageSource().contains("Sandi tidak valid")) {
			System.out.println("Wrong password, please try again. \n");
		}

		driver.findElement(By.id("email")).clear();
		driver.findElement(By.id("email")).sendKeys("mariachiii@i-love-you-3000");
		driver.findElement(By.id("passwd")).sendKeys("12345");
		driver.findElement(By.id("SubmitLogin")).click();
		if (driver.getPageSource().contains("Tidak valid alamat e-mail")) {
			System.out.println("Wrong email, please try again. \n");
		}
	}

	// TC10
	public void loginAcc() {
//		String urlLogin1 = "http://128.199.90.160/id/login?back=history";
//		String urlLogin2 = "http://128.199.90.160/id/login?back=my-account";
//
//		driver.navigate().to(urlLogin1);

		driver.findElement(By.id("email")).clear();
		driver.findElement(By.id("email")).sendKeys(mail);
		driver.findElement(By.id("passwd")).sendKeys("12345");
		driver.findElement(By.id("SubmitLogin")).click();

		String expTitleLogin = "Riwayat pembelian - QA";
		String testTitleLogin = driver.getTitle();

		try {
			Assert.assertEquals(testTitleLogin, expTitleLogin);
			System.out.println("Login success. \n");
		} catch (AssertionError e) {
			System.out.println("Login failed. \n");
		}

	}

	// TC11
	public void updateMyAddress() throws InterruptedException {
		driver.findElement(By.id("user_info_acc")).click();
		Thread.sleep(1000);

		driver.findElement(By.partialLinkText("Accounts")).click();
		driver.findElement(By.className("icon-building")).click();
		driver.findElement(By.xpath("/html/body/div/div[2]/div/div[2]/div/div[1]/div/div/ul/li[12]/a[1]")).click();

		driver.findElement(By.id("address1")).clear();
		driver.findElement(By.id("address1")).sendKeys("Madripoor Street No. 8");
		driver.findElement(By.id("postcode")).clear();
		driver.findElement(By.id("postcode")).sendKeys("57588");
		driver.findElement(By.id("city")).clear();
		driver.findElement(By.id("city")).sendKeys("Madripoor");

		Select state = new Select(driver.findElement(By.id("id_state")));
		state.selectByVisibleText("North Sumatra");

		driver.findElement(By.id("phone")).sendKeys("087654321");

		driver.findElement(By.id("submitAddress")).click();

		String actualAddress = driver.findElement(By.className("address_address1")).getText();
		try {
			Assert.assertEquals(actualAddress, "Madripoor Street No. 8");
			System.out.println("Address updated. \n");
		} catch (AssertionError e) {
			System.out.println("Address not updated. \n");
		}

	}
	
	

//	@AfterClass
//	public static void endTest() {
//		report.endTest(testing);
//		report.flush();
//	}

}
