package utils;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class TestDataUtil {
    
    public static JSONObject readTestData(String filePath) {
        JSONParser parser = new JSONParser();
        try (InputStream is = TestDataUtil.class.getClassLoader().getResourceAsStream(filePath)) {
            if (is == null) {
                throw new RuntimeException("Test data file not found: " + filePath);
            }
            String jsonTxt = IOUtils.toString(is, StandardCharsets.UTF_8);
            return (JSONObject) parser.parse(jsonTxt);
        } catch (Exception e) {
            throw new RuntimeException("Failed to read test data file: " + filePath, e);
        }
    }
}
