package stepDefinitions;
import java.io.IOException;

import org.testng.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import utility.API;

public class APITestSD {
    private static String response;
    private static String userInput;

    //Getting a user input
    @Given("User provides an input {string}")
    public void user_provides_an_input(String cityNameOrZipcode){
        userInput = cityNameOrZipcode;
    }

    //Calling API
    @When("API call is made")
    public void user_Calls_API() throws IOException {
        response = API.findLocation(userInput);
    };


    //Validating response is valid
    @Then("User gets the location details {string}")
    public void user_Gets_Valid_Response(String locationDetails){
        Assert.assertTrue(response.contains(locationDetails));
    }

    //Validating expected error message
    @Then("User gets an error message {string}")
    public void user_Gets_Error_Message(String ErrorMessage){
        Assert.assertTrue(response.contains(ErrorMessage));
    }

}
