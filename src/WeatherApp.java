package src;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/*
The WeatherApp class represents the main user interface of the weather station application.
It provides a graphical interface for the user to input a city name, fetches the weather data for that city (both current and forecast), and displays it in an interactive window.
The app uses Swing components such as labels, buttons, and tables to present the data.
 */

public class WeatherApp {
    private final JTextField locationField;
    private final JButton setLocationButton;
    private final JPanel currentPanel;
    private final JLabel temperatureValue;
    private final JLabel precipitationValue;
    private final JLabel windSpeedValue;
    private final JLabel humidityValue;
    private final JLabel statusLabel;
    private final JTable forecastTable;
    private final DefaultTableModel forecastModel;

    private final CurrentWeather currentWeather = new CurrentWeather();
    private final ForecastWeather forecastWeather = new ForecastWeather();

    /*
    locationField: A text field where the user types in the name of the city to fetch weather data.
    setLocationButton: A button that triggers the update of weather information when clicked.
    currentPanel: A panel that contains the labels for displaying the current weather information.
    forecastTable: A table that will display the forecast data.
    currentWeather and forecastWeather: Instances of CurrentWeather and ForecastWeather, which handle the retrieval and management of weather data.
     */

    public WeatherApp() {
        JFrame frame = new JFrame("Weather Station");
        frame.setLayout(new BorderLayout(10, 10));
        /*
            this constructor sets up the GUI components and layout for the WeatherApp. The main window (JFrame) is created with a BorderLayout layout manager.
         */

        // --- TOP PANEL (City Input) ---
        JPanel topPanel = new JPanel();
        locationField = new JTextField(20);
        setLocationButton = new JButton("Set Location");
        setLocationButton.setToolTipText("Click to fetch weather for the city");
        topPanel.add(new JLabel("City:"));
        topPanel.add(locationField);
        topPanel.add(setLocationButton);
        frame.add(topPanel, BorderLayout.NORTH);
        /*
        Purpose: This panel contains the text field where users type the city name and the button that triggers the weather update.
        locationField: A text field where the user types the city.
        setLocationButton: A button labeled "Set Location" that the user clicks to fetch weather data.
        Tool Tip: A tooltip is added to the button for guidance.
         */


        // --- CENTER PANEL (Current Weather) ---
        currentPanel = new JPanel(new GridBagLayout());
        currentPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Current Weather"),
                BorderFactory.createEmptyBorder(12, 12, 12, 12)));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        currentPanel.add(new JLabel("Temperature:"), gbc);
        gbc.gridx = 1;
        temperatureValue = new JLabel("-");
        currentPanel.add(temperatureValue, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        currentPanel.add(new JLabel("Precipitation:"), gbc);
        gbc.gridx = 1;
        precipitationValue = new JLabel("-");
        currentPanel.add(precipitationValue, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        currentPanel.add(new JLabel("Wind Speed:"), gbc);
        gbc.gridx = 1;
        windSpeedValue = new JLabel("-");
        currentPanel.add(windSpeedValue, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        currentPanel.add(new JLabel("Humidity:"), gbc);
        gbc.gridx = 1;
        humidityValue = new JLabel("-");
        currentPanel.add(humidityValue, gbc);

        statusLabel = new JLabel("Enter a city and click Set Location to load weather.");
        statusLabel.setForeground(Color.DARK_GRAY);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        currentPanel.add(statusLabel, gbc);

        frame.add(currentPanel, BorderLayout.CENTER);
         /*
            This panel is used to display the current weather information, including temperature, precipitation, wind speed, and humidity.
            The information will be updated dynamically when the user clicks the "Set Location" button.
         */

        // --- BOTTOM PANEL (Forecast) ---
        String[] columns = {"Time", "Temp (°C)", "Precip (mm)", "Wind (m/s)", "Humidity (%)"};
        forecastModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        forecastTable = new JTable(forecastModel);
        forecastTable.setFillsViewportHeight(true);
        forecastTable.setRowHeight(28);
        forecastTable.getTableHeader().setReorderingAllowed(false);
        forecastTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        JScrollPane forecastScroll = new JScrollPane(forecastTable);
        forecastScroll.setBorder(BorderFactory.createTitledBorder("Forecast"));
        frame.add(forecastScroll, BorderLayout.SOUTH);

        /*
        Purpose: This section creates a table to display the forecast data, showing multiple rows of forecasted weather for the next few hours
        (time, temperature, precipitation, wind speed, and humidity).
        JScrollPane: The table is wrapped inside a scroll pane, which allows for scrolling if the data extends beyond the window size.
         */

        // --- Event Listeners ---
        setLocationButton.addActionListener(e -> {
            try {
                updateWeather(locationField.getText().trim());
            } catch (Exception ex) {
                statusLabel.setText("Unable to load weather: " + ex.getMessage());
            }
        });

        /*
        When the user clicks the 'Set Location' button this grab the text they typed in the location field, clean it up, and use it to update the weather data.
        If something goes wrong then crash and print why
         */

        locationField.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER)
                    setLocationButton.doClick();
            }
        });

        //If the user is typing in the city input box and presses Enter, pretend they clicked the 'Set Location' button

        // Frame settings
        frame.setSize(1080, 720);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private void updateWeather(String city) throws Exception {
        if (city.isEmpty()) return;

        /*
        Purpose: This method is responsible for updating both the current weather and the forecast weather when a valid city name is provided.
        Early Exit: If the city name is empty, the method immediately returns without doing anything.
         */

        // Update Current
        currentWeather.updateWeatherData(city);
        temperatureValue.setText(currentWeather.getTemperature());
        precipitationValue.setText(currentWeather.getPrecipitation());
        windSpeedValue.setText(currentWeather.getWindSpeed());
        humidityValue.setText(currentWeather.getHumidity());
        statusLabel.setText("Current weather updated for " + city + ".");
        /*
        Purpose: This block updates the current weather data by calling the updateWeatherData() method on the currentWeather object,
        then updates the corresponding labels in the currentPanel to display the fetched data.
         */

        // Update Forecast Table
        forecastWeather.updateWeatherData(city);
        String[] times = forecastWeather.getForecastTimes();
        String[] temps = forecastWeather.getTemperature().split(",");
        String[] precs = forecastWeather.getPrecipitation().split(",");
        String[] winds = forecastWeather.getWindSpeed().split(",");
        String[] hums = forecastWeather.getHumidity().split(",");
        /*
        Purpose: This part retrieves the forecast data, splits it into arrays for each data type (temperature, precipitation, wind speed, humidity), and stores the forecast times.
         */
        List<Integer> forecastIndices = getForecastIndices(times, 24);
        int len = Math.min(forecastIndices.size(), 24);
        forecastModel.setRowCount(0);
        LocalDate lastDate = null;
        for (int i = 0; i < len; i++) {
            int index = forecastIndices.get(i);
            LocalDateTime forecastDateTime = parseForecastTime(times[index]);
            if (forecastDateTime != null && (lastDate == null || !forecastDateTime.toLocalDate().isEqual(lastDate))) {
                forecastModel.addRow(new Object[]{
                        formatForecastDayLabel(forecastDateTime),
                        "",
                        "",
                        "",
                        ""
                });
                lastDate = forecastDateTime.toLocalDate();
            }
            forecastModel.addRow(new Object[]{
                    formatForecastTime(times[index]),
                    safeValue(temps, index),
                    safeValue(precs, index),
                    safeValue(winds, index),
                    safeValue(hums, index)
            });
        }
        if (len == 0) {
            statusLabel.setText("Forecast data not available for " + city + ".");
        } else {
            statusLabel.setText("Showing forecast for " + city + " (next " + len + " hours).");
        }
        /*
        This block constructs a table with the first 5 rows of forecast data and updates the forecastTable with the new data.
         */
    }

    private String safeValue(String[] values, int index) {
        if (values == null || index < 0 || index >= values.length) {
            return "-";
        }
        String value = values[index].trim();
        return value.isEmpty() ? "-" : value;
    }

    /**
     * Returns the list of forecast indices starting at the current hour.
     * It will return up to maxEntries hourly forecast items for today and future days.
     */
    private List<Integer> getForecastIndices(String[] times, int maxEntries) {
        List<Integer> indices = new ArrayList<>();
        if (times == null || times.length == 0) {
            return indices;
        }
        LocalDate today = LocalDate.now();
        int currentHour = LocalDateTime.now().getHour();

        for (int i = 0; i < times.length; i++) {
            LocalDateTime forecastDateTime = parseForecastTime(times[i]);
            if (forecastDateTime == null) {
                continue;
            }
            if ((forecastDateTime.toLocalDate().isEqual(today) && forecastDateTime.getHour() >= currentHour)
                    || forecastDateTime.toLocalDate().isAfter(today)) {
                indices.add(i);
            }
            if (indices.size() >= maxEntries) {
                break;
            }
        }
        if (indices.isEmpty()) {
            for (int i = 0; i < Math.min(times.length, maxEntries); i++) {
                indices.add(i);
            }
        }
        return indices;
    }

    private LocalDateTime parseForecastTime(String rawTime) {
        if (rawTime == null || rawTime.isEmpty()) {
            return null;
        }
        try {
            return LocalDateTime.parse(rawTime, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
        } catch (Exception e) {
            return null;
        }
    }

    private String formatForecastDayLabel(LocalDateTime dateTime) {
        if (dateTime == null) {
            return "";
        }
        LocalDate today = LocalDate.now();
        LocalDate forecastDate = dateTime.toLocalDate();
        if (forecastDate.isEqual(today)) {
            return "Today";
        }
        if (forecastDate.isEqual(today.plusDays(1))) {
            return "Tomorrow";
        }
        return forecastDate.getDayOfWeek().toString().substring(0, 1)
                + forecastDate.getDayOfWeek().toString().substring(1).toLowerCase();
    }

    private String formatForecastTime(String rawTime) {
        if (rawTime == null || rawTime.isEmpty()) {
            return "-";
        }
        LocalDateTime dt = parseForecastTime(rawTime);
        if (dt == null) {
            return rawTime;
        }
        int hour = dt.getHour();
        String period = hour < 12 ? "am" : "pm";
        int displayHour = hour % 12;
        if (displayHour == 0) {
            displayHour = 12;
        }
        return displayHour + period;
    }

    public static void main(String[] args) {
        new WeatherApp();
    }
}

/*
Creating and using classes - defined and used WeatherApp, CurrentWeather, and ForecastWeather.
Encapsulation - used private fields and public methods like updateWeatherData() to manage access to data.
Event-driven programming / GUI events - added an ActionListener and KeyListener to interact with user input.
Using GUI components with Swing - used JFrame, JPanel, JButton, JLabel, JTable, JTextField, and JScrollPane.
Arrays and array indexing - Arrays are used to construct the forecast data (e.g., String[][] tableData).
2D arrays (grids) - Used in the forecastTable to structure weather forecast data in rows and columns.
Using external classes (composition) - used instances of CurrentWeather and ForecastWeather inside WeatherApp.
Using libraries (e.g., javax.swing, java.awt) - You imported and used multiple Java standard libraries.
Control structures (if, try-catch, for loop) - if (city.isEmpty()) return;, try-catch block, and the for loop for filling forecast data.
Polymorphism via method overriding (getText/setText on JLabels, JTable model) - updated GUI components polymorphically via Swing's component model.
GUI layout managers - used BorderLayout and GridLayout for layout design.
Input validation - if (city.isEmpty()) return; ensures the app doesn’t crash on empty input.
Iterating over components (e.g., currentPanel.getComponents()) - Used to dynamically update labels in updateWeather().
Table models in Swing (DefaultTableModel) - dynamically set data into the table using setModel().
Exception handling - Used try-catch in the button’s event handler for robustness.
 */