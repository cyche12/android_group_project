//Student: Jake Elliott//
//Student #040732505//
//Class: CST2335//
//Creation Date: 25/3/24//
//Group Members: Jake Elliott, Gabriel Hubert, Shilpi Sarkar, Piyalee Mangaraj//
//Project: Final Group Project//
//App: Sunrise/Sunset App//

package algonquin.cst2335.android_group_project;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/**
 * The SunriseHistory class displays the history of past sunrise and sunset searches.
 * This class uses a RecyclerView to list the search results.
 * It fetches the search data from the application's Room database and updates the display.
 */
public class SunriseHistory extends AppCompatActivity {

    /**
     * Adapter for the RecyclerView to display the history of searches.
     */
    private SunHistoryAdapter historyAdapter;

    /**
     * Called when the activity is starting where most initialization happens:
     * inflating the layout, setting up the RecyclerView, and loading the past search results.
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sun_history_view);

        RecyclerView historyRecyclerView = findViewById(R.id.historyRecyclerView);
        historyRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        historyAdapter = new SunHistoryAdapter(new ArrayList<>());
        historyRecyclerView.setAdapter(historyAdapter);

        loadAndDisplayPastSearchResults();
    }

    /**
     * Fetches past search results from the Room database and displays them in the RecyclerView using results from the RoomDatabase.
     */
    private void loadAndDisplayPastSearchResults() {
        ExecutorService databaseExecutor = Executors.newSingleThreadExecutor();
        databaseExecutor.execute(() -> {
            List<Sunrise_Data> pastSearchResults = SunriseApplication.getDatabase().sunDao().getSunriseData();
            List<String> resultsData = new ArrayList<>();
            for (Sunrise_Data data : pastSearchResults) {
                String displayText = "Latitude: " + data.getLatitude() + ", Longitude: " + data.getLongitude() +
                        "\nSunrise: " + data.getSunriseTime() + "\nSunset: " + data.getSunsetTime();
                resultsData.add(displayText);
            }
            runOnUiThread(() -> historyAdapter.addPastResults(resultsData));
        });
    }
}