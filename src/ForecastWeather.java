package src;

/**
 * ForecastWeather is a subclass of WeatherData that fetches and stores
 * multi-day weather forecast data using the OpenMeteoClient API.
 */
public class ForecastWeather extends WeatherData {

    /**
     * Updates the forecast weather data for the given city.
     * Retrieves arrays of weather metrics from OpenMeteoClient,
     * joins them into comma-separated strings for storage, and
     * stores forecast times directly as a String array.
     *
     * @param city The city to fetch forecast data for (e.g., "London")
     * @throws Exception if data fetching fails
     */

    @Override
    public void updateWeatherData(String city) throws Exception {
        // Fetch forecast data from the OpenMeteo API
        OpenMeteoClient.updateForecast(city);

        // Join the arrays into comma-separated strings and store
        this.temperature = String.join(",", OpenMeteoClient.getForecastTemperature());
        this.precipitation = String.join(",", OpenMeteoClient.getForecastPrecipitation());
        this.windSpeed = String.join(",", OpenMeteoClient.getForecastWind());
        this.humidity = String.join(",", OpenMeteoClient.getForecastHumidity());

        // Store the forecast time values directly as an array
        this.forecastTimes = OpenMeteoClient.getForecastTime();
    }

    /**
     * Returns the array of forecast times.
     * @return an array of forecast date/time values
     */
    public String[] getForecastTimes() {
        return forecastTimes;
    }
}

/*

Inheritance - ForecastWeather inherits from WeatherData
Method Overriding - Customizes updateWeatherData()
Encapsulation - Stores data in inherited fields, provides a getter for forecastTimes
API Integration - Grabs forecast data from OpenMeteoClient
Array to String conversion - String.join() is used to simplify storing array data
Polymorphism - Different implementations of updateWeatherData() for current vs. forecast

 */
