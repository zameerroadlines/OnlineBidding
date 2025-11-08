package com.bosch.automation.utilities;

import java.io.*;
import java.util.Properties;

/** Utility class to read test properties file **/
public class ApplicationProperties {
    private final Properties properties;
    public ApplicationProperties() {
        this.properties = new Properties();
        try {
            properties.load(getClass().getClassLoader().getResourceAsStream("test.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public String readProperty(String keyName){
        return properties.getProperty(keyName, "Key not found");
    }
    public void setPropertySaveChanges(String keyName,String value){
         properties.setProperty(keyName,value);
        try {
            flush(keyName);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void flush(String key) throws IOException {
        try (
                final OutputStream outputstream
                     = new FileOutputStream(("./src/main/resources/test.properties"))) {
            properties.store(outputstream,"File Updated for key : "+key);
        }
    }
}