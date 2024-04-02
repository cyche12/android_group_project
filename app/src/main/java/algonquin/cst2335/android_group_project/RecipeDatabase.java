package algonquin.cst2335.android_group_project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Database;
import androidx.room.RoomDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import algonquin.cst2335.android_group_project.databinding.SunriseResultBinding;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Database(entities = {SavedRecipe.class}, version = 1)
public abstract class RecipeDatabase extends RoomDatabase {
    public abstract SavedRecipeDao savedRecipeDao();

    public static class Sunrise_Results extends AppCompatActivity {

        private SunResultsAdapter resultsAdapter;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            SunriseResultBinding binding = SunriseResultBinding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());

            Intent intent = getIntent();
            String websiteURL = intent.getStringExtra("websiteURL");
            String latitude = intent.getStringExtra("latitude");
            String longitude = intent.getStringExtra("longitude");
            Button backButton = findViewById(R.id.back_button);
            Intent backIntent = new Intent(Sunrise_Results.this, Sunrise_Search.class);
            backButton.setOnClickListener(click -> startActivity(backIntent));

            RecyclerView resultsRecyclerView = findViewById(R.id.results_recycler_view);
            resultsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            resultsAdapter = new SunResultsAdapter();
            resultsRecyclerView.setAdapter(resultsAdapter);
            fetchSunriseSunsetData(websiteURL);
        }

        private void fetchSunriseSunsetData(String websiteURL) {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(websiteURL)
                    .build();

            client.newCall(request).enqueue(new okhttp3.Callback() {
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

                            // Construct list with sunrise and sunset data
                            List<String> newData = new ArrayList<>();
                            newData.add("Sunrise: " + sunrise);
                            newData.add("Sunset: " + sunset);
                            SharedPreferences dataStorage = getSharedPreferences("dataEntered", MODE_PRIVATE);
                            SharedPreferences.Editor editor = dataStorage.edit();
                            String latitude = dataStorage.getString("latitude", "");
                            String longitude = dataStorage.getString("longitude", "");

                            editor.putString("latitude", latitude);
                            editor.putString("longitude", longitude);
                            editor.apply();

                            // Update ResultsAdapter on the UI thread
                            runOnUiThread(() -> resultsAdapter.addPastResults(newData));

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
    }
}
