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
        Intent backIntent = new Intent(Sunrise_Results.this, Sunrise_Search.class);
        backButton.setOnClickListener(click -> startActivity(backIntent));

        RecyclerView resultsRecyclerView = binding.resultsRecyclerView;
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

                        saveToSharedPreferences(latitude, longitude, sunrise, sunset);
                        runOnUiThread(() -> resultsAdapter.updateResults(newData));

                        saveToRoomDatabase(latitude, longitude);

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
            newDataRoom.latitude = latitude;
            newDataRoom.longitude = longitude;
            SunriseApplication.getDatabase().sunriseDao().insert(newDataRoom);
        });
    }
}
