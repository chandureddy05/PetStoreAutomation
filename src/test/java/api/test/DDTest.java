package api.test;

import org.testng.Assert;
import org.testng.annotations.Test;

import api.endpoints.UserEndPoints;
import api.payload.User;
import api.utilities.DataProviders;
import io.restassured.response.Response;

public class DDTest {
	
	@Test(priority=1, dataProvider="Data", dataProviderClass=DataProviders.class)
	public void testPostUser(String userID, String username, String fname, String lname, String email, String password, String phone)
	{
		User userPayload=new User();
		
		userPayload.setId(Integer.parseInt(userID));
		userPayload.setUsername(username);
		userPayload.setFirstName(fname);
		userPayload.setLastName(lname);
		userPayload.setEmail(email);
		userPayload.setPassword(password);
		userPayload.setPhone(phone);
		
		Response response = UserEndPoints.createUser(userPayload);
		Assert.assertEquals(response.statusCode(), 200);
		
	}
	
	@Test(priority=2, dataProvider="UserNames", dataProviderClass=DataProviders.class)
	public void testGetUserByName(String userName)
	{
		Response response = UserEndPoints.readUser(userName);
		response.then().log().body();
		Assert.assertEquals(response.statusCode(), 200);
	}
	
	@Test(priority=3, dataProvider="UserNames", dataProviderClass=DataProviders.class)
	public void testDeleteUserByName(String userName)
	{
		Response response = UserEndPoints.deleteUser(userName);
		Assert.assertEquals(response.statusCode(), 200);
	}

}
