package test_utils;
import main_utils.ConfigManager;
import main_utils.LoggerUtil;
import main_utils.TestDataUtil;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;

public class BaseTest {

    protected static String authToken;
    protected static String baseUrl;
    // Read the test data from the JSON file
    protected static JSONObject requestParams = TestDataUtil.readTestData("test-data/test-info.json");

    @BeforeClass
    public static void setup() {
        // Generate authentication token
        baseUrl = ConfigManager.getProperty("base_url");
        CommonMethods.LogStep("Generating Auth token");
        authToken = CommonMethods.GenerateAuthToken();;
        LoggerUtil.info("Setup complete.");
    }
}
