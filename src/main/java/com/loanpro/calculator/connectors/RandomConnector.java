package com.loanpro.calculator.connectors;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class RandomConnector {

    // Create constant for the URL
    private static final String RANDOM_URL = "https://www.random.org/strings/";

    private RestClient restClient;

    public RandomConnector() {
        restClient = RestClient.create();
    }

    public String generateRandomString() {
        // Call the HTTP service to generate a random string
        //TODO - Implement the logic to receive parameters from the user
        String params = "?num=1&len=15&digits=on&upperalpha=on&loweralpha=on&unique=on&format=plain&rnd=new";

        String result = restClient.get()
                .uri(RANDOM_URL + params)
                .retrieve()
                .body(String.class);

        if (result == null) {
            throw new RuntimeException("Failed to generate random string");
        }
        return result;
    }
}
