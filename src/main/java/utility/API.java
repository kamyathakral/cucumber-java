package utility;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class API {

    //declaring static variables
    private static String userInput;
    private static String response;
    private static String key;
    private static String apiResponse;
    private static String defaultResponse;

    public static String findLocation(String userChoice) throws IOException {

            userChoice = userChoice.trim();
            key = getGlobalValue("apiKey");
            defaultResponse = "Kindly enter a valid US CityName & StateCode separated by comma or ZipCode and hit enter to find the location details. To Exit press X.";

            //Verifying if user wants to exit
            if (userChoice.equalsIgnoreCase("X")) {
                System.out.println("Have A GOOD DAY!!");
                System.exit(0);
            }

            Pattern pattern = Pattern.compile("\\d");
            Matcher numberString = pattern.matcher(userChoice);
            boolean containsZip = numberString.find();

            if (userChoice.trim().isEmpty() || userChoice.length() == 1) {
                response = defaultResponse;
                return response;
            }

            //calling API to get the response
            if (!containsZip) {
                String uri = getGlobalValue("cityNameURI");
                String cityState = userChoice.trim().replace(" ","");
                uri = uri+cityState+",US&limit=1&appid="+key;

                //Get API response
                response = getAPIResponse(userChoice, uri);
                return response;

            }
 
            else {
                String uri = getGlobalValue("zipCodeURI");
                String zipCountryCode= userChoice;
                uri = uri + zipCountryCode + "&appid="+key;

                //Get API response
                response = getAPIResponse(userChoice, uri);
                return response;
            }
    }

    public static String getGlobalValue(String key) throws IOException {
        Properties prop = new Properties();
        FileInputStream in = new FileInputStream("src/main/resources/global.properties");
        prop.load(in);
        return prop.getProperty(key);
    }

    public static String getAPIResponse(String userChoice, String uri){

        RestAssured.baseURI = uri ;
        apiResponse = RestAssured.get(uri).asString();
        JsonPath js = new JsonPath(apiResponse);// for parsing Json

        String cityName = js.getString("name");
        String latitude =  js.getString("lat");
        String longitude = js.getString("lon");
        String country = js.getString("country");


        if (cityName!=null) {
            response = "Location Details for " + userChoice + "  are - \n City: " + cityName + 
            " \n Latitude: " +  latitude +
            " \n Longitude: " +  longitude + 
            " \n and Country: " +  country + 
            ". \n " + defaultResponse;
            return response;
        } else {
            response = defaultResponse;
            return response;
        }
    }

        public static void main (String[]args) throws IOException {
            //Getting user input for a city or state code or zip code
            System.out.print(defaultResponse);
            Scanner sc = new Scanner(System.in);
            do {
                userInput = sc.nextLine();
                //calling method to get the location deatils
                response = findLocation(userInput);
                System.out.println(response);
            }
            while (!(userInput.equals("X")) || (userInput.equals("x"))) ;

        }
    }


