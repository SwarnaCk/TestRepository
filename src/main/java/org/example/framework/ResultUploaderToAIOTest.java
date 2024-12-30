package org.example.framework;
import okhttp3.*;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class ResultUploaderToAIOTest {
    private static final String API_URL = "https://tcms.aiojiraapps.com/aio-tcms/api/v1/project/{projectKey}/testcycle/{testCycleKey}/import/results?type=Cucumber";
    private static final String PROJECT_KEY = "LT";
    private static final String TEST_CYCLE_KEY = "LT-CY-6";
    private static final String AUTH_TOKEN = "AioAuth ZDNlNTA1YTMtZTA2My0zZmFkLTlmNGMtZTcwNWY5YWQ4M2YyLmY5MmFmMGE2LWRhYzctNGUwNC04ZjI5LWVhYzk3NWQyYjZiMA==";

    public void uploadTestResults(File cucumberJsonFile) {
        String apiUrl = API_URL
                .replace("{projectKey}", PROJECT_KEY)
                .replace("{testCycleKey}", TEST_CYCLE_KEY);

        OkHttpClient client = createHttpClient();

        // Create form data with all required parameters
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", cucumberJsonFile.getName(),
                        RequestBody.create(cucumberJsonFile, MediaType.parse("application/json")))
                .addFormDataPart("createNewRun", "true")
                .addFormDataPart("bddForceUpdateCase", "true")
                .addFormDataPart("updateDatasets", "true")
                .addFormDataPart("type", "Cucumber");

        Request request = new Request.Builder()
                .url(apiUrl)
                .addHeader("Authorization", AUTH_TOKEN)
                .post(builder.build())
                .build();

        try (Response response = client.newCall(request).execute()) {
            String responseBody = response.body() != null ? response.body().string() : "No response body";

            if (response.isSuccessful()) {
                System.out.println("Test results uploaded successfully!");
                System.out.println("Response Body: " + responseBody);
            } else {
                System.err.println("Failed to upload test results. Response Code: " + response.code());
                System.err.println("Response Body: " + responseBody);

                // Add detailed error logging
                System.err.println("Request URL: " + apiUrl);
                System.err.println("File exists: " + cucumberJsonFile.exists());
                System.err.println("File size: " + cucumberJsonFile.length());
            }
        } catch (IOException e) {
            System.err.println("Error uploading test results: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private OkHttpClient createHttpClient() {
        return new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();
    }
}
