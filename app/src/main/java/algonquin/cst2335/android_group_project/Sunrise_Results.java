package algonquin.cst2335.android_group_project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import algonquin.cst2335.android_group_project.databinding.SunriseResultBinding;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Sunrise_Results extends AppCompatActivity {

    private SunResultsAdapter resultsAdapter;
    private SunHistoryAdapter historyAdapter;
    private String latitude;
    private String longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SunriseResultBinding binding = SunriseResultBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        latitude = intent.getStringExtra("latitude");
        longitude = intent.getStringExtra("longitude");

        Button backButton = binding.backButton;
        backButton.setOnClickListener(click -> finish());

        RecyclerView resultsRecyclerView = binding.sunriseRecyclerView;
        resultsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        resultsAdapter = new SunResultsAdapter();
        resultsRecyclerView.setAdapter(resultsAdapter);

        Button saveButton = binding.saveButton;
        saveButton.setOnClickListener(click -> saveSearchResult());

        Button historyButton = binding.historyButton;
        historyButton.setOnClickListener(click -> loadAndDisplayPastSearchResults());

        RecyclerView historyRecyclerView = binding.resultsRecyclerView; // Corrected to use resultsRecyclerView
        historyRecyclerView.setLayoutManager(new LinearLayoutManager(this)); // Use separate layout manager
        historyAdapter = new SunHistoryAdapter(new ArrayList<>());
        historyRecyclerView.setAdapter(historyAdapter);

        // Fetch sunrise and sunset data
        fetchSunriseSunsetData(latitude, longitude);
    }

    private void fetchSunriseSunsetData(String latitude, String longitude) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.sunrise-sunset.org/json?lat=" + latitude + "&lng=" + longitude + "&date=today")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    try {
                        assert response.body() != null;
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        JSONObject results = jsonObject.getJSONObject("results");
                        String sunriseTime = results.getString("sunrise");
                        String sunsetTime = results.getString("sunset");

                        // Update UI with sunrise and sunset data
                        List<String> newData = new ArrayList<>();
                        newData.add("Latitude: " + latitude + ", Longitude: " + longitude);
                        newData.add("Sunrise: " + sunriseTime);
                        newData.add("Sunset: " + sunsetTime);

                        runOnUiThread(() -> {
                            resultsAdapter.addNewResults(newData);
                            saveToRoomDatabase(latitude, longitude, sunriseTime, sunsetTime);
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void loadAndDisplayPastSearchResults() {
        // Load past search results from Room Database
        ExecutorService databaseExecutor = Executors.newSingleThreadExecutor();
        databaseExecutor.execute(() -> {
            try {
                List<Sunrise_Data> pastSearchResults = SunriseApplication.getDatabase().sunDao().getSunriseData();

                // Create a list of strings for results data
                List<String> resultsData = new ArrayList<>();
                for (Sunrise_Data data : pastSearchResults) {
                    resultsData.add("Latitude: " + data.getLatitude() + ", Longitude: " + data.getLongitude() +
                            "\nSunrise: " + data.getSunriseTime() + "\nSunset: " + data.getSunsetTime());
                }

                // Update the results RecyclerView adapter with the loaded data
                runOnUiThread(() -> resultsAdapter.addNewResults(resultsData));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void saveSearchResult() {
        String currentSearchResult = "Latitude: " + latitude + ", Longitude: " + longitude;
        historyAdapter.addPastResults(Collections.singletonList(currentSearchResult)); // Add the current search result to historyAdapter
    }

    private void saveToRoomDatabase(String latitude, String longitude, String sunriseTime, String sunsetTime) {
        ExecutorService databaseExecutor = Executors.newSingleThreadExecutor();
        databaseExecutor.execute(() -> {
            try {
                Sunrise_Data newDataRoom = new Sunrise_Data();
                newDataRoom.setLatitude(latitude);
                newDataRoom.setLongitude(longitude);
                newDataRoom.setSunriseTime(sunriseTime);
                newDataRoom.setSunsetTime(sunsetTime);
                SunriseApplication.getDatabase().sunDao().insert(newDataRoom);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
