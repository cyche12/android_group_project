package algonquin.cst2335.android_group_project;

import android.content.Intent;
import android.os.Bundle;
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
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import algonquin.cst2335.android_group_project.databinding.SunriseResultBinding;

public class Sunrise_Results extends AppCompatActivity {

    private ResultsAdapter resultsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SunriseResultBinding binding = SunriseResultBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        Intent intent = getIntent();
        String websiteURL = intent.getStringExtra("websiteURL");
        String latitude = intent.getStringExtra("latitude");
        String longitude = intent.getStringExtra("longitude");


        RecyclerView resultsRecyclerView = findViewById(R.id.results_recycler_view);
        resultsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        resultsAdapter = new ResultsAdapter();
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

                        // Construct list with sunrise and sunset data
                        List<String> newData = new ArrayList<>();
                        newData.add("Sunrise: " + sunrise);
                        newData.add("Sunset: " + sunset);

                        // Update ResultsAdapter on the UI thread
                        runOnUiThread(() -> resultsAdapter.updateResults(newData));
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
