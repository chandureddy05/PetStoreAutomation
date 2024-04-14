package api.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.UserEndPoints;
import api.payload.User;
import io.restassured.response.Response;

public class UserTests {

	
	Faker faker;
	User userPayload;
	
	public Logger logger;
	
	@BeforeClass
	public void setupData(){
		
		faker=new Faker();
		userPayload=new User();
		
		userPayload.setId(faker.idNumber().hashCode());
		userPayload.setUsername(faker.name().username());
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		userPayload.setPassword(faker.internet().password());
		userPayload.setPhone(faker.phoneNumber().cellPhone());
		
		
		//logs
		logger=LogManager.getLogger(this.getClass());
		logger.debug("debugging");
	}
	
	@Test(priority=1)
	public void testPostUser(){
		
		logger.info("***Creating User");		
		Response response = UserEndPoints.createUser(userPayload);
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(), 200);
		
		logger.info("***User is Created");
	}
	
	@Test(priority=2)
	public void testGetUserByName(){
		
		logger.info("***Reading User Info****");
		Response response=UserEndPoints.readUser(this.userPayload.getUsername());
		response.then().log().all();
		
		Assert.assertEquals(response.statusCode(), 200);
		logger.info("***User Info retrievd****");
	}
	
	@Test(priority=3)
	public void testUpdateUser(){
		
		//update user details
		logger.info("***Update User Info****");
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());		
		
		Response response=UserEndPoints.updateUser(this.userPayload.getUsername(), userPayload);
		response.then().log().body();
		Assert.assertEquals(response.statusCode(), 200);
		
		//validate post update
		Response responsePostUpdate=UserEndPoints.readUser(this.userPayload.getUsername());
		responsePostUpdate.then().log().all();
		
		Assert.assertEquals(responsePostUpdate.statusCode(), 200);
		logger.info("***User Info updated****");
	}
	
	@Test(priority=4)
	public void testDeleteUser(){
		
		logger.info("***Delete User Info****");
		Response response = UserEndPoints.deleteUser(this.userPayload.getUsername());
		Assert.assertEquals(response.statusCode(), 200);
		logger.info("*** User Info deleted****");
	}
	
}
