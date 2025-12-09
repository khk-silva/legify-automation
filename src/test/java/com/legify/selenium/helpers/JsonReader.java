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
            rootNode = mapper.readTree(new File("src/test/resources/testdata/testdata.json"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // For login credentials
    public static String getUsername(String userKey) {
        return rootNode.get(userKey).get("username").asText();
    }

    public static String getPassword(String userKey) {
        return rootNode.get(userKey).get("password").asText();
    }

    // Generic method for ANY field inside any user object
    public static String getUserData(String userKey, String field) {
        return rootNode.get(userKey).get(field).asText();
    }

    // Generic method for ANY field inside any ticket object
    public static String getTicketData(String ticketKey, String field) {
        return rootNode.get(ticketKey).get(field).asText();
    }

    // Generic method for ANY field inside any resource object
    public static String getResourceData(String resourceKey, String field) {
        return rootNode.get(resourceKey).get(field).asText();
    }

    // Generic method for ANY field inside any resource object
    public static String getDocumentData(String documentKey, String field) {
        return rootNode.get(documentKey).get(field).asText();
    }

    // Generic method for ANY field inside any resource object
    public static String getcollaboratorData(String collaboratorKey, String field) {
        return rootNode.get(collaboratorKey).get(field).asText();
    }
}



