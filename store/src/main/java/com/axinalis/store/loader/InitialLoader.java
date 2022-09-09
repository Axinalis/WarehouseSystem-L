package com.axinalis.store.loader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@Component
public class InitialLoader {

    private static Logger log = LoggerFactory.getLogger(InitialLoader.class);

    public Map<String, Integer> initiateCategory(String categoryName){
        Map<String, Integer> productMaxStock = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(getCategoryFile(categoryName))){

            String productItem = reader.readLine();
            checkFileContent(productItem, categoryName);
            while((productItem = reader.readLine()) != null){
                productMaxStock.put(getProductTitle(productItem), getProductStock(productItem));
            }

        } catch (IOException e){
            log.error("Error while initializing category {}: {}", categoryName, e.getMessage());
        }
        return productMaxStock;
    }

    public Map<String, List<String>> listStoresCategories(){
        Properties props = new Properties();
        Map<String, List<String>> stores = new HashMap<>();
        try {

            log.debug("Loading of stores.properties");
            props.load(getStoreProperties());

        } catch (IOException e) {
            log.error("Error while reading stores.properties file: {}", e.getMessage());
            throw new RuntimeException("Error while reading stores.properties file");
        }
        props.forEach((key, value) -> {
            stores.put(key.toString(), List.of(value.toString().split(",")));
            log.debug("New store was loaded. The title is {}, and categories are {}", key, stores.get(key.toString()));
        });
        return stores;
    }

    private void checkFileContent(String line, String categoryName){
        if( ! line.matches("#*" + categoryName + "#*")){
            log.warn("File and file content not match. Maybe file was renamed");
        }
    }

    private String getProductTitle(String line){
        return line.split("=")[0];
    }

    private Integer getProductStock(String line){
        try{
            return Integer.valueOf(line.split("=")[1]);
        } catch(ArrayIndexOutOfBoundsException | NumberFormatException e){
            log.error("File content cannot be read properly. Make sure that file content matches pattern \"Red gloves=80\"");
            throw new RuntimeException("Error while reading file content");
        }
    }


    private InputStreamReader getCategoryFile(String category){
        InputStream stream = InitialLoader.class.getClassLoader().getResourceAsStream(category + ".txt");
        if(stream != null){
            return new InputStreamReader(stream);
        } else {
            log.error("File for category {} was not found", category);
            throw new RuntimeException("File for some category was not found");
        }
    }
    private InputStream getStoreProperties(){
        InputStream stream = InitialLoader.class.getClassLoader().getResourceAsStream("stores.properties");
        if(stream != null){
            return stream;
        } else {
            log.error("File stores.properties not found");
            throw new RuntimeException("File stores.properties not found");
        }
    }
}
