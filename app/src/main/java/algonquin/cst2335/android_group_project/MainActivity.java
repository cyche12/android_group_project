package algonquin.cst2335.android_group_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText searchInput;
    private Button searchButton;
    private RecyclerView recyclerView;
    private List<Artist> artistList = new ArrayList<>();
    private ArtistAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchInput = findViewById(R.id.etSearchInput);
        searchButton = findViewById(R.id.btnSearch);
        recyclerView = findViewById(R.id.rvSongs);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ArtistAdapter(this, artistList);
        recyclerView.setAdapter(adapter);

        searchButton.setOnClickListener(view -> performSearch(searchInput.getText().toString()));
    }

    private void performSearch(String query) {
        String url = "https://api.deezer.com/search/artist/?q=" + query;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, response -> {
                    try {
                        JSONArray artists = response.getJSONArray("data");
                        for (int i = 0; i < artists.length(); i++) {
                            JSONObject artist = artists.getJSONObject(i);
                            String name = artist.getString("name");
                            String imageUrl = artist.getJSONObject("picture_medium").getString("url");
                            String tracklistUrl = artist.getString("tracklist");
                            artistList.add(new Artist(name, imageUrl, tracklistUrl));
                        }
                        adapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {
                    // Handle error
                });

        Volley.newRequestQueue(this).add(jsonObjectRequest);
    }
}
