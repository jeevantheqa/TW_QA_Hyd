package tw;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.Select;

public class PageClass {

	WebDriver driver = null;

	Properties prop = new Properties();
	InputStream input = null;

	public void setup() throws Exception {

		ExcelUtils.setExcelFile(".\\src\\test_data\\testdata.xlsx", "Sheet1");

		input = new FileInputStream(".\\config.properties");
		prop.load(input);

		if (prop.getProperty("browser_name").equals("ff")) {
		
			driver = new FirefoxDriver();

		}

		if (prop.getProperty("browser_name").equals("chrome")) {
			System.setProperty("webdriver.chrome.driver",
					".\\drivers\\chromedriver.exe");
			
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--disable-extensions");

			driver = new ChromeDriver(options);
		}

		if (prop.getProperty("browser_name").equals("ie")) {

			System.setProperty("webdriver.ie.driver",
					".\\drivers\\IEDriverServer.exe");

			driver = new InternetExplorerDriver();

		}
		
		driver.manage().window().maximize();
		driver.get(prop.getProperty("url"));
	}

	public void closeBrowser() {

		driver.quit();
	}

	public void login(String uname, String password) {

		driver.findElement(By.id("userName")).sendKeys(prop.getProperty("username"));
		driver.findElement(By.id("password")).sendKeys(prop.getProperty("password"));
		driver.findElement(By.xpath("//button[text()='Login']")).click();

	}

	public void enterValues() throws Exception {

		driver.findElement(By.id("name")).sendKeys(ExcelUtils.getCellData(1, 1));
		driver.findElement(By.id("age")).sendKeys(ExcelUtils.getCellData(2, 1));
		driver.findElement(By.xpath("//input[@value='" + ExcelUtils.getCellData(3, 1) + "']")).click();
		driver.findElement(By.id("blogArea")).sendKeys(ExcelUtils.getCellData(4, 1));

		new Select(driver.findElement(By.id("profession"))).selectByValue(ExcelUtils.getCellData(5, 1));

		driver.findElement(By.xpath("//button[@type='submit']")).click();
		
		Thread.sleep(2000);

	}

	public void verify() throws Exception {

		driver.findElement(By.linkText("Blogs")).click();
		
		 Thread.sleep(3000);
		 
		List<WebElement> ele = driver.findElements(By.id("accordion"));
		int count = ele.size();
		
		for(int i=1;i<=count;i++){
			
		String expanded = driver.findElement(By.xpath("//*[@id='accordion']/div["+i+"]/div/a")).getAttribute("aria-expanded");
		
			if(expanded.equals("true")){
			
				String name=driver.findElement(By.xpath("//*[@id='accordion']/div["+i+"]/div[2]/div/span[1]")).getText();
		
				if(name.equals(ExcelUtils.getCellData(1, 1))){
					
					ExcelUtils.setCellData("Pass", 1, 2);
					
					ExcelUtils.setCellData(compare(ExcelUtils.getCellData(2, 1), driver.findElement(By.xpath("//*[@id='accordion']/div["+i+"]/div[2]/div/span[2]")).getText()), 2, 2);
					
					ExcelUtils.setCellData(compare(ExcelUtils.getCellData(3, 1), driver.findElement(By.xpath("//*[@id='accordion']/div["+i+"]/div[2]/div/span[3]")).getText()), 3, 2);
					
					ExcelUtils.setCellData(compare(ExcelUtils.getCellData(4, 1), driver.findElement(By.xpath("//*[@id='accordion']/div["+i+"]/div[2]/div/span[4]")).getText()), 4, 2);
					
					ExcelUtils.setCellData(compare(ExcelUtils.getCellData(5, 1), driver.findElement(By.xpath("//*[@id='accordion']/div["+i+"]/div[2]/div/span[5]")).getText()), 5, 2);
					
				}
		}
			
			else if(expanded.equals("false")){
			
			driver.findElement(By.xpath("//*[@id='accordion']/div["+i+"]/div/a")).click();
			
			String name=driver.findElement(By.xpath("//*[@id='accordion']/div["+i+"]/div[2]/div/span[1]")).getText();
			
			if(name.equals(ExcelUtils.getCellData(1, 1))){
				
				ExcelUtils.setCellData("Pass", 1, 2);
				
				ExcelUtils.setCellData(compare(ExcelUtils.getCellData(2, 1), driver.findElement(By.xpath("//*[@id='accordion']/div["+i+"]/div[2]/div/span[2]")).getText()), 2, 2);
				
				ExcelUtils.setCellData(compare(ExcelUtils.getCellData(2, 1), driver.findElement(By.xpath("//*[@id='accordion']/div["+i+"]/div[2]/div/span[3]")).getText()), 3, 2);
				
				ExcelUtils.setCellData(compare(ExcelUtils.getCellData(2, 1), driver.findElement(By.xpath("//*[@id='accordion']/div["+i+"]/div[2]/div/span[4]")).getText()), 4, 2);
				
				ExcelUtils.setCellData(compare(ExcelUtils.getCellData(2, 1), driver.findElement(By.xpath("//*[@id='accordion']/div["+i+"]/div[2]/div/span[5]")).getText()), 5, 2);
				
			}
			
		}
			
		}

	}
	
	public void logout() throws InterruptedException{
		
		driver.findElement(By.id("logout")).click();
		Thread.sleep(2000);
	}
	
	public String compare(String actual , String expected){
		
		if(actual.equals(expected)){
			
			return "Pass";
		}
		
		else return "Fail";
	}

}
