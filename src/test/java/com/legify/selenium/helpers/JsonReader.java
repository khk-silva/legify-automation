package com.legify.selenium.helpers;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class JsonReader {

    private static JsonNode rootNode;

    static {
        try {
            ObjectMapper mapper = new ObjectMapper();
            rootNode = mapper.readTree(new File("src/test/resources/testdata/loginData.json"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getUsername(String userType) {
        return rootNode.get(userType).get("username").asText();
    }

    public static String getPassword(String userType) {
        return rootNode.get(userType).get("password").asText();
    }
}
