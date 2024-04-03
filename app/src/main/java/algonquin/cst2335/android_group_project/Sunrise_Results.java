//Student: Jake Elliott//
//Student #040732505//
//Class: CST2335//
//Creation Date: 25/3/24//
//Group Members: Jake Elliott, Gabriel Hubert, Shilpi Sarkar, Piyalee Mangaraj//
//Project: Final Group Project//
//App: Sunrise/Sunset App//

package algonquin.cst2335.android_group_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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
    private String currentSunrise, currentSunset;
    private String latitude, longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SunriseResultBinding binding = SunriseResultBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = binding.sunToolbar;
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        latitude = intent.getStringExtra("latitude");
        longitude = intent.getStringExtra("longitude");

        RecyclerView resultsRecyclerView = binding.sunriseRecyclerView;
        resultsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        resultsAdapter = new SunResultsAdapter();
        resultsRecyclerView.setAdapter(resultsAdapter);

        Button backButton = binding.backButton;
        backButton.setOnClickListener(click -> finish());

        Button saveButton = binding.saveButton;
        saveButton.setOnClickListener(click -> {
            if (currentSunrise != null && currentSunset != null) {
                saveToRoomDatabase(latitude, longitude, currentSunrise, currentSunset);
            } else {
                Toast.makeText(this, "No data to save. Please fetch the sunrise and sunset times first.", Toast.LENGTH_LONG).show();
            }
        });

        Button historyButton = binding.historyButton;
        historyButton.setOnClickListener(click -> {
            Intent historyIntent = new Intent(this, SunriseHistory.class);
            startActivity(historyIntent);
        });

        fetchSunriseSunsetData(latitude, longitude);
    }

    private void fetchSunriseSunsetData(String latitude, String longitude) {
        OkHttpClient client = new OkHttpClient();
        String url = "https://api.sunrise-sunset.org/json?lat=" + latitude + "&lng=" + longitude + "&date=today";
        Request request = new Request.Builder().url(url).build();

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
                        currentSunrise = results.getString("sunrise");
                        currentSunset = results.getString("sunset");

                        List<String> newData = new ArrayList<>();
                        newData.add("Latitude: " + latitude + ", Longitude: " + longitude);
                        newData.add("Sunrise: " + currentSunrise);
                        newData.add("Sunset: " + currentSunset);

                        runOnUiThread(() -> resultsAdapter.addNewResults(newData));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void saveToRoomDatabase(String latitude, String longitude, String sunriseTime, String sunsetTime) {
        ExecutorService databaseExecutor = Executors.newSingleThreadExecutor();
        Sunrise_Data newData = new Sunrise_Data();
        newData.setLatitude(latitude);
        newData.setLongitude(longitude);
        newData.setSunriseTime(sunriseTime);
        newData.setSunsetTime(sunsetTime);
        databaseExecutor.execute(() -> {
            try {
                SunriseApplication.getDatabase().sunDao().insert(newData);

                // Display Toast message on UI thread
                runOnUiThread(() -> Toast.makeText(Sunrise_Results.this, "Data saved successfully.", Toast.LENGTH_SHORT).show());
            } catch (Exception e) {
                e.printStackTrace();
                // Display error Toast message on UI thread
                runOnUiThread(() -> Toast.makeText(Sunrise_Results.this, "Error occurred while saving data.", Toast.LENGTH_SHORT).show());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sun_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.sun_help) {
            showHelpDialog();
            return true;
        } else if (id == R.id.sun_favorite) {
            Intent historyIntent = new Intent(this, SunriseHistory.class);
            startActivity(historyIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showHelpDialog() {
        new AlertDialog.Builder(this)
                .setTitle("How to Use")
                .setMessage("Enter your longitude and latitude in the fields below, then click 'Lookup' when ready. Save your searches with the Save button. View saved searches with the History button.")
                .setPositiveButton("OK", null)
                .show();
    }
}
