package algonquin.cst2335.android_group_project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
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
    private String latitude;
    private String longitude;
    private TextView searchResultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SunriseResultBinding binding = SunriseResultBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        String websiteURL = intent.getStringExtra("websiteURL");
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
        fetchSunriseSunsetData(websiteURL);
        loadAndDisplayPastSearchResults();
    }

    private void fetchSunriseSunsetData(String websiteURL) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(websiteURL)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(Sunrise_Results.this, "Failed to fetch data", Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful() && response.body() != null) {
                    String jsonResponse = response.body().string();
                    try {
                        JSONObject jsonObject = new JSONObject(jsonResponse);
                        JSONObject resultsObject = jsonObject.getJSONObject("results");
                        String sunrise = resultsObject.getString("sunrise");
                        String sunset = resultsObject.getString("sunset");

                        List<String> newData = new ArrayList<>();
                        newData.add("Sunrise: " + sunrise);
                        newData.add("Sunset: " + sunset);

                        runOnUiThread(() -> {
                            // Add new search results to the adapter
                            resultsAdapter.addNewResults(newData);
                            // Save new search results to SharedPreferences and Room Database
                            saveToSharedPreferences(latitude, longitude, sunrise, sunset);
                            saveToRoomDatabase(latitude, longitude);
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                        runOnUiThread(() -> Toast.makeText(Sunrise_Results.this, "Failed to parse JSON", Toast.LENGTH_SHORT).show());
                    }
                } else {
                    runOnUiThread(() -> Toast.makeText(Sunrise_Results.this, "Response not successful", Toast.LENGTH_SHORT).show());
                }
            }
        });
    }

    private void loadAndDisplayPastSearchResults() {
        // Load past search results from Room Database
        ExecutorService databaseExecutor = Executors.newSingleThreadExecutor();
        databaseExecutor.execute(() -> {
            List<Sunrise_Data> pastSearchResults = SunriseApplication.getDatabase().sunriseDao().getSunriseData();

            // Convert past search results to a format suitable for display
            List<String> pastSearchResultStrings = new ArrayList<>();
            for (Sunrise_Data data : pastSearchResults) {
                pastSearchResultStrings.add("Latitude: " + data.x_coordinate + ", Longitude: " + data.y_coordinate);
            }

            runOnUiThread(() -> {
                // Add past search results to the adapter
                resultsAdapter.addPastResults(pastSearchResultStrings);
            });
        });
    }

    private void saveSearchResult() {
        // Save the current latitude and longitude to the TextView below the RecyclerView
        String currentSearchResult = "Latitude: " + latitude + ", Longitude: " + longitude;
        searchResultTextView.setText(currentSearchResult);
    }

    private void saveToSharedPreferences(String latitude, String longitude, String sunrise, String sunset) {
        SharedPreferences dataStorage = getSharedPreferences("dataEntered", MODE_PRIVATE);
        SharedPreferences.Editor editor = dataStorage.edit();
        editor.putString("latitude", latitude);
        editor.putString("longitude", longitude);
        editor.putString("sunrise", sunrise);
        editor.putString("sunset", sunset);
        editor.apply();
    }

    private void saveToRoomDatabase(String latitude, String longitude) {
        ExecutorService databaseExecutor = Executors.newSingleThreadExecutor();
        databaseExecutor.execute(() -> {
            Sunrise_Data newDataRoom = new Sunrise_Data();
            newDataRoom.x_coordinate = latitude;
            newDataRoom.y_coordinate = longitude;
            SunriseApplication.getDatabase().sunriseDao().insert(newDataRoom);
        });
    }
}
