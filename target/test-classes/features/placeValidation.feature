Feature: Validating Place APIs

@addPlace
Scenario Outline: Verify if Place is being Succesfully added using AddPlaceAPI
Given Add place payload with "<name>" "<language>" "<address>"
When user calls "AddPlaceAPI" with "POST" http request
Then the API call got success with status code 200
And "status" in response body is "OK"
And "scope" in response body is "APP"
And verify place_Id created maps to "<name>" using "getPlaceAPI"
Then validate the json schema

Examples:
|name|language|address|
|AAhouse|English|World cross center|
#|BBhouse|Spanish|Sea cross center|
 
 
 #
#Scenario: verify if delete place functionality is working
#
#Given DeletePlace Payload
#When user calls "deletePlaceAPI" with "POST" http request 
#Then the API call got success with status code 200
#And "status" in response body is "OK"
