package algonquin.cst2335.android_group_project;
/**
 * Purpose:SongAPI class is the main class for the application
 * Author: Shilpi Sarkar
 * Lab section:012
 * Date created: March 26, 2024
 *
 */
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import algonquin.cst2335.android_group_project.databinding.ActivitySongRoomBinding;

public class SongAPI extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivitySongRoomBinding binding;
    private RequestQueue musicRequestQueue;
    private EditText musicSearchEditText;
    private Button musicSearchButton;
    private RecyclerView.Adapter myAdapter;
    EditText MusicArtistName;
    ArrayList<SongReturn> music_song_list = new ArrayList<>();


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        TextView messageText;
/**
 *  @param item  parameter is used to identify the selected menu item by its ID.
 *  @return boolean Returns  to indicate that the menu item selection event has been
 *
 * */
        if (item.getItemId() == R.id.music_item_2) {
            Intent intent = new Intent(SongAPI.this, ViewFavourites.class);
            startActivity(intent);
            return true;
        }


        if (item.getItemId() == R.id.music_item_1) {
            // Show an AlertDialog with instructions on how to use the app
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("How to Use the App")
                    .setMessage("There are instructions how to use the app:\n1. Type the artist name you want to search up in the text area \n2. Click on search button to see the list of songs appear\n3. Click on any song for more details about song\n4. Click the star at the top to view your favourite songs")
                    .setPositiveButton("OK", (dialog, which) -> {
                        // Do nothing, just dismiss the dialog
                    })
                    .show();
            return true;
        }

        return super.onOptionsItemSelected(item);


    }

/**
 *@param menu The options menu in which the items are placed.
 *@return boolean Returns for the menu to be displayed
 * */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.song_menu, menu);

        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySongRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        MusicArtistName = findViewById(R.id.MusicSearch);
        SharedPreferences prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        String ArtistSearch = prefs.getString("Artist Name", "");
        MusicArtistName.setText(ArtistSearch);

        musicSearchEditText = binding.MusicSearch;
        binding.musicSearchButton.setOnClickListener(click -> {
            String query = musicSearchEditText.getText().toString().trim();
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("Artist Name", MusicArtistName.getText().toString());
            editor.apply();
            Log.d("SearchArtists", "Query: " + query);
            if (!query.isEmpty()) {
                String url = "https://api.deezer.com/search/artist/?q=" + query;
                Log.d("SearchArtists", "URL: " + url); // Add this line for debugging

                final String[] tracklist = {null};

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d("SongAPI", "Response: " + response.toString()); // Log the response
                                try {
                                    JSONArray data = response.getJSONArray("data");
                                    for (int i = 0; i < data.length(); i++) {
                                        JSONObject artist = data.getJSONObject(i);
                                        tracklist[0] = artist.getString("tracklist");
                                        Log.e("SongAPI", "tracklist: " + tracklist[0]); // Log errors
                                        break;
                                    }

                                    Log.d("SongAPI", "tracklist[0]: " + tracklist[0]); // Log the tracklist[0]
                                    // Make the second request only after the first request has completed successfully
                                    if (tracklist[0] != null) {
                                        String url2 = tracklist[0];
                                        Log.d("SongAPI", "URL for second request: " + url2); // Log the URL
                                        JsonObjectRequest jsonObjectRequest2 = new JsonObjectRequest(Request.Method.GET, url2, null,
                                                new Response.Listener<JSONObject>() {
                                                    @Override
                                                    public void onResponse(JSONObject response) {
                                                        Log.d("SongAPI", "Response: " + response.toString()); // Log the response
                                                        try {
                                                            JSONArray data = response.getJSONArray("data");
                                                            for (int i = 0; i < data.length(); i++) {
                                                                JSONObject track = data.getJSONObject(i);
                                                                String songTitle = track.getString("title");
                                                                String songDuration = track.getString("duration");
                                                                int songId = track.getInt("id");

                                                                JSONObject album = track.getJSONObject("album");
                                                                String AlbumName = album.getString("title");
                                                                String artistImage = album.getString("cover");
                                                                Log.d("SongAPI", "SongTitle: " + songTitle); // Log the response
                                                                Log.d("SongAPI", "AlbumName: " + AlbumName); // Log the response
                                                                Log.d("SongAPI", "Cover: " + artistImage); // Log the response
                                                                Log.d("SongAPI", "duration: " + songDuration); // Log the response

                                                                SongReturn songs = new SongReturn(songId, artistImage, AlbumName, songTitle, songDuration);
                                                                music_song_list.add(songs);
                                                                myAdapter.notifyItemInserted(music_song_list.size() - 1);
                                                            }
                                                        } catch (JSONException e) {
                                                            e.printStackTrace();
                                                        }
                                                    }
                                                },
                                                new Response.ErrorListener() {
                                                    @Override
                                                    public void onErrorResponse(VolleyError error) {
                                                        // Handle errors
                                                        Log.e("testError", "Error: " + error.getMessage());
                                                    }
                                                });
                                        MySingleton.getInstance(SongAPI.this).addToRequestQueue(jsonObjectRequest2);
                                    } else {
                                        Log.e("SongAPI", "No tracklist URL found or it's empty");
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // Handle errors
                                Log.e("testError", "Error: " + error.getMessage());
                            }
                        });
                MySingleton.getInstance(SongAPI.this).addToRequestQueue(jsonObjectRequest);
            }
        });
        binding.recyclerView.setAdapter(myAdapter = new RecyclerView.Adapter<MyRowHolder>() {

/**
 * @param parent   The ViewGroup into which the new View will be added
 * @param viewType The view type of the new View. T
 * @return MyRowHolder that holds a View of the given view type.
 * */
            @NonNull
            @Override
            public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                View view = inflater.inflate(R.layout.song_list, parent, false);
                return new MyRowHolder(view);
            }

/**
 * @return The total number of music song items in this adapter's data set.
 * */
            @Override
            public int getItemCount() {
                return music_song_list.size();
            }

            @Override
            public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {

                SongReturn obj = music_song_list.get(position);
                Log.d("SongAPI", "objCover: " + obj.getMusicPicture());
                Log.d("SongAPI", "objSongName: " + obj.getMusicSongName());
                Log.d("SongAPI", "objAlbumName: " + obj.getMusicAlbumName());
                Log.d("SongAPI", "objDuration: " + obj.getMusicDuration());// Log the response
                holder.SongTitles.setText(obj.getMusicSongName());
            }


        });


        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        setSupportActionBar(binding.myToolbar);


    }

    class MyRowHolder extends RecyclerView.ViewHolder {
        TextView SongTitles;

        public MyRowHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(clk -> {
                int position = getAdapterPosition();
                SongReturn objDisplay = music_song_list.get(position);
                Log.d("SongAPI", "objDisplaySongName: " + objDisplay.getMusicSongName());
                Log.d("SongAPI", "objDisplaySongName: " + objDisplay.getMusicAlbumName());
                Intent music_details = new Intent(SongAPI.this, SongDetails.class);
                music_details.putExtra("SongNames", objDisplay.getMusicSongName());
                music_details.putExtra("AlbumNames", objDisplay.getMusicAlbumName());
                music_details.putExtra("Cover", objDisplay.getMusicPicture());
                music_details.putExtra("Duration", objDisplay.getMusicDuration());
                music_details.putExtra("id", objDisplay.getMusicId());
                startActivity(music_details);

            });
            SongTitles = itemView.findViewById(R.id.song_title_text_view);

        }
    }


}

