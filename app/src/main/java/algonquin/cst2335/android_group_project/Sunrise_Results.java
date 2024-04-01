package algonquin.cst2335.android_group_project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import algonquin.cst2335.android_group_project.databinding.SunriseResultBinding;

public class Sunrise_Results extends AppCompatActivity {

    private SunResultsAdapter resultsAdapter;
    private String latitude;
    private String longitude;
    private TextView searchResultTextView;

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

        RecyclerView resultsRecyclerView = binding.resultsRecyclerView;
        resultsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        resultsAdapter = new SunResultsAdapter();
        resultsRecyclerView.setAdapter(resultsAdapter);

        searchResultTextView = binding.searchResultTextView;

        Button saveButton = binding.saveButton;
        saveButton.setOnClickListener(view -> saveSearchResult());

        Button historyButton = binding.historyButton;
        historyButton.setOnClickListener(view -> loadAndDisplayPastSearchResults());

        fetchSunriseSunsetData(latitude, longitude);
    }

    private void fetchSunriseSunsetData(String latitude, String longitude) {
        // Simulate fetching sunrise and sunset data from API
        String sunriseTime = "6:30 AM";
        String sunsetTime = "6:30 PM";

        // Update UI with sunrise and sunset data
        List<String> newData = new ArrayList<>();
        newData.add("Latitude: " + latitude + ", Longitude: " + longitude);
        newData.add("Sunrise: " + sunriseTime);
        newData.add("Sunset: " + sunsetTime);

        resultsAdapter.addNewResults(newData);

        // Save new search results to Room database
        saveToRoomDatabase(latitude, longitude, sunriseTime, sunsetTime);
    }

    private void loadAndDisplayPastSearchResults() {
        // Load past search results from Room Database
        ExecutorService databaseExecutor = Executors.newSingleThreadExecutor();
        databaseExecutor.execute(() -> {
            try {
                List<Sunrise_Data> pastSearchResults = SunriseApplication.getDatabase().sunDao().getSunriseData();

                // Create a StringBuilder to concatenate all past search results
                StringBuilder pastSearchResultText = new StringBuilder();
                for (Sunrise_Data data : pastSearchResults) {
                    pastSearchResultText.append("Latitude: ").append(data.getLatitude()).append(", Longitude: ").append(data.getLongitude()).append("\n");
                }

                // Update the TextView with the concatenated search results
                runOnUiThread(() -> searchResultTextView.setText(pastSearchResultText.toString()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void saveSearchResult() {
        String currentSearchResult = "Latitude: " + latitude + ", Longitude: " + longitude;
        searchResultTextView.setText(currentSearchResult);
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
