package com.gradle.ex1;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class ReadEx {
    public static void main(String[] args) {
        JSONParser parser = new JSONParser();

        try (FileReader reader = new FileReader("D:/devops/gradle-ex1/src/resources/emp.json")) {
            // Read JSON file
            Object obj = parser.parse(reader);
            JSONObject jsonObject = (JSONObject) obj;

            // Display JSON content
            System.out.println(jsonObject.toJSONString());

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
}

