package src;

/**
 * Abstract base class for representing weather data.
 * It cannot be instantiated directly and must be extended by subclasses like CurrentWeather and ForecastWeather.
 */
public abstract class WeatherData {

    // These variables store the core weather metrics.
    // They're protected so subclasses can directly access them.
    protected String temperature;
    protected String precipitation;
    protected String windSpeed;
    protected String humidity;
    protected String[] forecastTimes; // only used for forecast data

    /**
     * Abstract method to be implemented by subclasses.
     * Each type of weather data (current or forecast) must define
     * how it updates its values using the provided city.
     *
     * @param city The city to fetch weather data for
     * @throws Exception If the update process encounters issues
     */
    public abstract void updateWeatherData(String city) throws Exception;

    // Getter methods provide external access to the weather data.
    public String getTemperature() {
        return temperature;
    }

    public String getPrecipitation() {
        return precipitation;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public String getHumidity() {
        return humidity;
    }

    /*
     * We pass everything as a string because OpenMeteoClient returns
     * all weather data as strings. By initializing everything as a string,
     * we reduce the amount of parsing logic required in our program.
     */
}
 /*
Abstraction - WeatherData defines a blueprint with an abstract method
Inheritance - Other classes extend this base class
Encapsulation - Fields are accessed via getters
Polymorphism - Subclasses override updateWeatherData()
Data Representation - Uses String to simplify interaction with the external API
  */