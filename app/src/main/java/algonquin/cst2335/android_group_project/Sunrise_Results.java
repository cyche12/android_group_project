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

/**
 * Activity for displaying search results of sunrise and sunset times based on the user's entered latitude and longitude.
 * This class handles making an API call to fetch the sunrise and sunset times, displays those results and provides an option to save them into a local database.
 */
public class Sunrise_Results extends AppCompatActivity {

    private SunResultsAdapter resultsAdapter;
    private String currentSunrise, currentSunset;
    private String latitude, longitude;

    /**
     * Initializes the activity's UI, sets up the toolbar, and binds listeners to buttons.
     * It also triggers fetching of sunrise and sunset data based on latitude and longitude received from the intent.
     */
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
        backButton.setOnClickListener(click -> startActivity(new Intent(this, Sunrise_Search.class)));

        Button saveButton = binding.saveButton;
        saveButton.setOnClickListener(click -> {
            if (currentSunrise != null && currentSunset != null) {
                saveToRoomDatabase(latitude, longitude, currentSunrise, currentSunset);
            } else {
                Toast.makeText(this, "No data to save. Please fetch the sunrise and sunset times first.", Toast.LENGTH_LONG).show();
            }
        });

        Button historyButton = binding.historyButton;
        historyButton.setOnClickListener(click -> startActivity(new Intent(this, SunriseHistory.class)));

        fetchSunriseSunsetData(latitude, longitude);
    }

    /**
     * Fetches sunrise and sunset data from an external API and updates the display with the results.
     * Makes a network call, parses the JSON response, and displays sunrise and sunset times.
     * @param latitude Latitude for which to fetch sunrise and sunset times.
     * @param longitude Longitude for which to fetch sunrise and sunset times.
     */
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

    /**
     * Saves the fetched sunrise and sunset times along with latitude and longitude to the local Room database.
     * @param latitude Latitude of the location.
     * @param longitude Longitude of the location.
     * @param sunriseTime Sunrise time to be saved.
     * @param sunsetTime Sunset time to be saved.
     */
    private void saveToRoomDatabase(String latitude, String longitude, String sunriseTime, String sunsetTime) {
        ExecutorService databaseExecutor = Executors.newSingleThreadExecutor();
        Sunrise_Data newData = new Sunrise_Data();
        newData.setLatitude(latitude);
        newData.setLongitude(longitude);
        newData.setSunriseTime(sunriseTime);
        newData.setSunsetTime(sunsetTime);

        databaseExecutor.execute(() -> {
            SunriseApplication.getDatabase().sunDao().insert(newData);
            runOnUiThread(() -> Toast.makeText(Sunrise_Results.this, "Data saved successfully.", Toast.LENGTH_SHORT).show());
        });
    }

    /**
     * Initialize the contents of the Sunrise classes options menu.
     * @param menu Menu to be inflated.
     * @return True for the menu to be displayed.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sun_menu, menu);
        return true;
    }

    /**
     * Method for declaring what the menu items do.
     * Option one is the favorites menu.
     * Option two is the help icon.
     * @param item The menu item that was clicked.
     * @return False to allow normal menu processing to proceed, true to consume it here.
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.sun_help) {
            showHelpDialog();
            return true;
        } else if (id == R.id.sun_favorite) {
            startActivity(new Intent(this, SunriseHistory.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Shows a help dialog providing guidance on how to use the application when the help button is clicked on the toolbar.
     */
    private void showHelpDialog() {
        new AlertDialog.Builder(this)
                .setTitle("How to Use")
                .setMessage("Enter your longitude and latitude in the fields below, then click 'Lookup' when ready. Save your searches with the Save button. View saved searches with the History button.")
                .setPositiveButton("OK", null)
                .show();
    }
}