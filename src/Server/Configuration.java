package Server;

import java.io.InputStream;
import java.util.Properties;

public class Configuration {
    private static  Configuration instance = null; // Singleton instance
    private Properties properties; // Properties object to hold configuration settings

    private Configuration(){
        properties = new Properties(); // Initialize properties
        try(InputStream in = getClass().getResourceAsStream("resources/config.properties")) { // Load properties from file
            if (in == null) {
                throw new RuntimeException("Unable to find config.properties file"); // Check if file is found
            }
            properties.load(in); // Load properties from the input stream
        } catch (Exception e) {
            e.printStackTrace(); // Print any exceptions that occur
        }
    }

    public static Configuration getInstance() {
        if (instance == null) { // If instance is null, create a new one
            instance = new Configuration();
        }
        return instance; // Return the singleton instance
    }

    public int getThereadPoolSize(){
        return Integer.parseInt(properties.getProperty("threadPoolSize")); // Get thread pool size from properties
    }

    public String getMazeGeneratingAlgorithm() {
        return properties.getProperty("mazeGeneratingAlgorithm");
    }

    public String getMazeSearchingAlgorithm() {
        return properties.getProperty("mazeSearchingAlgorithm");
    }
}
