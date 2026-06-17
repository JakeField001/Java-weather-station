package src;

// CurrentWeather is a subclass of WeatherData that fetches and stores real-time weather data
public class CurrentWeather extends WeatherData {

    /**
     * Updates the current weather data for a given city.
     * This method overrides the abstract method in the parent class (WeatherData)
     * and fills in the temperature, precipitation, wind speed, and humidity values
     * using the OpenMeteoClient API.
     *
     * @param city The city for which to update weather data (e.g., "London")
     * @throws Exception if data retrieval from the OpenMeteo API fails
     */
    @Override
    public void updateWeatherData(String city) throws Exception {
        // Updates the current weather data for the specified city
        OpenMeteoClient.updateCurrentWeather(city);

        // Store the weather values retrieved from the API into the object's fields
        this.temperature = OpenMeteoClient.getCurrentTemperature();
        this.precipitation = OpenMeteoClient.getCurrentPrecipitation();
        this.windSpeed = OpenMeteoClient.getCurrentWind();
        this.humidity = OpenMeteoClient.getCurrentHumidity();
    }
}

/*
Inheritance	- CurrentWeather extends WeatherData
Method Overriding -	@Override allows customizing behavior of a superclass method
Abstraction - The abstract concept of "WeatherData" is made specific with real data in CurrentWeather
-> Data abstraction is the process of hiding certain details and showing only essential information to the user. Abstraction can be achieved with either abstract classes or interfaces --https://www.w3schools.com/java/java_abstract.asp
Encapsulation - The data is stored in protected variables from the parent class
-> The meaning of Encapsulation, is to make sure that "sensitive" data is hidden from users -- https://www.w3schools.com/java/java_encapsulation.asp
API Integration - Uses static methods from OpenMeteoClient to pull live data
 */