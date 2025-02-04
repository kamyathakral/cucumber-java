Feature: Test API class with different possible City & State names & Zip Codes

 Scenario Outline: User gets correct location details for a valid City & State names & Zip Codes
  Given User provides an input "<cityNameOrZipcode>"
  When API call is made
  Then User gets the location details "<locationDetails>"
  Examples:
   | cityNameOrZipcode| locationDetails  |
   | Hopkins, MN      | 44.92712         |
   | Madison, WI      | 43.07476         |
   | 12345            | Schenectady      |
   | 55347            | Eden Prairie     |


 Scenario Outline: User gets an error message for an invalid country name or code
  Given User provides an input "<invalidInput>"
  When API call is made
  Then User gets an error message "<ErrorMessage>"
  Examples:
   | invalidInput | ErrorMessage |
   |     11       | Kindly enter a valid US CityName & StateCode separated by comma or ZipCode|
   |     &*       | Kindly enter a valid US CityName & StateCode separated by comma or ZipCode|
   |              | Kindly enter a valid US CityName & StateCode separated by comma or ZipCode|
   |     878      | Kindly enter a valid US CityName & StateCode separated by comma or ZipCode|
   |     1US      | Kindly enter a valid US CityName & StateCode separated by comma or ZipCode|
   |     US1      | Kindly enter a valid US CityName & StateCode separated by comma or ZipCode|