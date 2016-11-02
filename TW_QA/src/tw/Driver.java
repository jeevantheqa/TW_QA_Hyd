package tw;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Driver {
	
	PageClass obj = new PageClass();

	@BeforeMethod(groups={"Smoke","Regression","Functional"})
	public void start() throws Exception{
		obj.setup();
	}
	
	@AfterMethod(groups={"Smoke","Regression","Functional"})
	public void exit(){
		obj.closeBrowser();
	}
	
	@Test(groups={"Smoke"})
	public void write_a_blog() throws Exception{
		obj.login("admin", "admin");
		obj.enterValues();
		obj.verify();
		obj.logout();
	}
	
}
