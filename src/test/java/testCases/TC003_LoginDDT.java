package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;
import utilities.DataProviders;

public class TC003_LoginDDT extends BaseClass {
	//getting data from different class&package so we provide data provider class parameter if its in same class&package we don't need provider
	@Test(dataProvider="LoginData",dataProviderClass=DataProviders.class,groups= "DataDriven")
	public void verify_loginDDT(String email,String pwd,String exp)
	{
		logger.info("**** starting TC003_LoginDDT ****");
		try
		{
			//HomePage
			HomePage hp=new HomePage(driver);
			hp.clickMyAccount();
			hp.clickLogin();
			
			//LoginPage
			LoginPage lp=new LoginPage(driver);
			lp.setEmail(p.getProperty("email"));
			lp.setPassword(p.getProperty("password"));
			lp.clickLogin();
			
			//MyAccountPage
			MyAccountPage macc=new MyAccountPage(driver);
			boolean targetPage=macc.isMyAccountPageExists();
			
			/*Data is valid-login success-testpass-logout
			 *             -login failed -testfail
			 *Data is invalid-login success-testfail-logout
			 *               -login failed-testpass
			 */
			if(exp.equalsIgnoreCase("valid"))
			{
				if(targetPage==true)
				{
					macc.clickLogout();
					Assert.assertTrue(true);
				}
				else
				{
					Assert.assertTrue(false);
				}
			}
			
			if(exp.equalsIgnoreCase("invalid"))
			{
				if(targetPage==true)
				{
					macc.clickLogout();
					Assert.assertTrue(false);
				}
				else
				{
					Assert.assertTrue(true);
				}
			}
		}
		catch(Exception e)
		{
			Assert.fail();
		}
		logger.info("**** Finished TC003_LoginDDT ****");
	}

}
