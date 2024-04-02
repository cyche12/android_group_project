package algonquin.cst2335.android_group_project;

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

public class SongRoom extends AppCompatActivity {

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

        if (item.getItemId() == R.id.music_item_2) {
            Intent intent = new Intent(SongRoom.this, ViewFavourites.class);
            startActivity(intent);
            return true;
        }


        if (item.getItemId() == R.id.music_item_1) {
            // Show an AlertDialog with instructions on how to use the app
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("How to Use the App")
                    .setMessage("Here are instructions on how to use the app:\n1. Type the artist name you wish to search up in the text area \n2. Click on search button to have the list of their songs appear\n3. Click on any song to have more info appear about it\n4. Click the star at the top to view your favourited songs")
                    .setPositiveButton("OK", (dialog, which) -> {
                        // Do nothing, just dismiss the dialog
                    })
                    .show();
            return true;
        }

        return super.onOptionsItemSelected(item);


    }

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
                                Log.d("MusicRoom", "Response: " + response.toString()); // Log the response
                                try {
                                    JSONArray data = response.getJSONArray("data");
                                    for (int i = 0; i < data.length(); i++) {
                                        JSONObject artist = data.getJSONObject(i);
                                        tracklist[0] = artist.getString("tracklist");
                                        Log.e("MusicRoom", "tracklist: " + tracklist[0]); // Log errors
                                        break;
                                    }

                                    Log.d("MusicRoom", "tracklist[0]: " + tracklist[0]); // Log the tracklist[0]
                                    // Make the second request only after the first request has completed successfully
                                    if (tracklist[0] != null) {
                                        String url2 = tracklist[0];
                                        Log.d("MusicRoom", "URL for second request: " + url2); // Log the URL
                                        JsonObjectRequest jsonObjectRequest2 = new JsonObjectRequest(Request.Method.GET, url2, null,
                                                new Response.Listener<JSONObject>() {
                                                    @Override
                                                    public void onResponse(JSONObject response) {
                                                        Log.d("MusicRoom", "Response: " + response.toString()); // Log the response
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
                                                                Log.d("MusicRoom", "SongTitle: " + songTitle); // Log the response
                                                                Log.d("MusicRoom", "AlbumName: " + AlbumName); // Log the response
                                                                Log.d("MusicRoom", "Cover: " + artistImage); // Log the response
                                                                Log.d("MusicRoom", "duration: " + songDuration); // Log the response

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
                                        MySingleton.getInstance(SongRoom.this).addToRequestQueue(jsonObjectRequest2);
                                    } else {
                                        Log.e("MusicRoom", "No tracklist URL found or it's empty");
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
                MySingleton.getInstance(SongRoom.this).addToRequestQueue(jsonObjectRequest);
            }
        });
        binding.recyclerView.setAdapter(myAdapter = new RecyclerView.Adapter<MyRowHolder>() {


            @NonNull
            @Override
            public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                View view = inflater.inflate(R.layout.song_list, parent, false);
                return new MyRowHolder(view);
            }


            @Override
            public int getItemCount() {
                return music_song_list.size();
            }

            @Override
            public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {

                SongReturn obj = music_song_list.get(position);
                Log.d("MusicRoom", "objCover: " + obj.getMusicPicture());
                Log.d("MusicRoom", "objSongName: " + obj.getMusicSongName());
                Log.d("MusicRoom", "objAlbumName: " + obj.getMusicAlbumName());
                Log.d("MusicRoom", "objDuration: " + obj.getMusicDuration());// Log the response
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
                Log.d("MusicRoom", "objDisplaySongName: " + objDisplay.getMusicSongName());
                Log.d("MusicRoom", "objDisplaySongName: " + objDisplay.getMusicAlbumName());
                Intent music_details = new Intent(SongRoom.this, SongDetails.class);
                music_details.putExtra("SongNames", objDisplay.getMusicSongName());
                music_details.putExtra("AlbumNames", objDisplay.getMusicAlbumName());
                music_details.putExtra("Cover", objDisplay.getMusicPicture());
                music_details.putExtra("Duration", objDisplay.getMusicDuration());
                music_details.putExtra("id", objDisplay.getMusicId());
                startActivity(music_details);

            });
            SongTitles = itemView.findViewById(R.id.song_title_text_view);
            //  AlbumTitle = itemView.findViewById(R.id.textViewAlbumName);
            //  duration = itemView.findViewById(R.id.musicTextViewDuration);
            //  AlbumImage = itemView.findViewById(R.id.musicImageViewAlbumCover);
        }
    }


}

