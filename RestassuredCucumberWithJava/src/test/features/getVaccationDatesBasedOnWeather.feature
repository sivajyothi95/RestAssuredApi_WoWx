Feature: get Dates For Vaccation Based On the Weather

  Background:
    
  Scenario Outline: Get the dates of Thursday which has temperature of 10 degrees or higher at Sydney
    Given I prepare request for OpenWeather api
    When I request OpenWeather api to know the weather of Sydney
    Then the status code is 200
    And I should see the dates which has temperature greater than <Degree> on THURSDAY
    Examples:
    |Degree|
    |10    |

