package stepdefs;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static io.restassured.RestAssured.given;
import static stepdefs.Hooks.cucumberReportInfoLog;

public class getVaccationDatesBasedOnWeatherStepDefinitions {

    private Response response;
    private ValidatableResponse json;
    private RequestSpecification request;

    private String openWeatherMapEndpoint = "https://openweathermap.org/data/2.5/forecast/?appid=b6907d289e10d714a6e88b30761fae22&id=2147714&units=metric";

    @Given("I prepare request for OpenWeather api")
    public void prepareRequestOpenWeatherApi() {
        request = given().contentType("application/json; charset=utf-8");
    }

    @When("I request OpenWeather api to know the weather of Sydney")
    public void requestOpenWeatherApiForSyd() {
        response = request.when().get(openWeatherMapEndpoint);
        cucumberReportInfoLog("response: " + response.prettyPrint());
    }

    @Then("the status code is (\\d+)")
    public void verifyStatusCode(int statusCode) {
        json = response.then().statusCode(statusCode);
        cucumberReportInfoLog(String.format("Status Code %s", String.valueOf(statusCode)));
    }

    @And("I should see the dates which has temperature greater than (.*) on THURSDAY")
    public void getDatesOfTemparatureLessThanTen(String degree) throws ParseException {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        JsonPath jp = new JsonPath(response.asString());
        ArrayList<String> listOfDatesInResponse = jp.get("list.dt_txt");
        ArrayList<String> listOfTemparaturesInResponse = jp.get("list.main.temp");
        int responseCount = listOfDatesInResponse.size();
        for (int responseIndex = 0; responseIndex < responseCount; responseIndex++) {
            double actualTemp = Double.valueOf(String.valueOf(listOfTemparaturesInResponse.get(responseIndex)));
            double expectedTemp = Double.valueOf(degree);
            cal.setTime(dateFormat.parse(listOfDatesInResponse.get(responseIndex)));
            boolean isTheDayThursday = cal.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY;

            if (actualTemp > expectedTemp && isTheDayThursday) {
                cucumberReportInfoLog(String.format("The Temperature on %s is %s", listOfDatesInResponse.get(responseIndex), listOfTemparaturesInResponse.get(responseIndex)));
            }
        }
    }
}


