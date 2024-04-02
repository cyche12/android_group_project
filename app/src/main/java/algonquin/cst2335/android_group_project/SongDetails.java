package algonquin.cst2335.android_group_project;
/**
 * Purpose:SongDetails class is the process for the application
 * Author: Shilpi Sarkar
 * Lab section:012
 * Date created: March 31, 2024
 *
 */
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;


import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import algonquin.cst2335.android_group_project.databinding.ActivitySongDetailsBinding;


public class SongDetails extends AppCompatActivity {
    /**
     * The binding instance for the ActivitySongDetails layout.
     */
    private ActivitySongDetailsBinding binding;
    /**
     * An instance of SongReturnDAO providing access to database operations for the
     *  entities.
     */
    SongReturnDAO mDAO;
    /**
     * A list holding integer values, commonly used to store unique identifiers or other
     * numeric data
     */
    List<Integer> allIds = new ArrayList<>();

    /**
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySongDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Database db = Room.databaseBuilder(this.getApplicationContext(), Database.class, "Song_DB").build();
        mDAO = db.cmDAO();


        ImageView back = findViewById(R.id.musicBackButton);
        back.setOnClickListener(v -> finish());

        Intent fromPrevious = getIntent();
        String Cover = fromPrevious.getStringExtra("Cover");
        String SongName = fromPrevious.getStringExtra("SongNames");
        String AlbumName = fromPrevious.getStringExtra("AlbumNames");
        String Duration = fromPrevious.getStringExtra("Duration");
        int id = fromPrevious.getIntExtra("id", 0);

        Log.d("SongAPI", "SongName: " + SongName);
        Log.d("SongAPI", "AlbumName1: " + AlbumName);

        TextView songTitleTextView = findViewById(R.id.musicTextViewTitle);
        TextView albumNameTextView = findViewById(R.id.textViewAlbumName);


        TextView durationTextView = findViewById(R.id.musicTextViewDuration);
        ImageView albumCoverImageView = findViewById(R.id.musicImageViewAlbumCover);


        SongReturn songReturn = new SongReturn(id, Cover, AlbumName, SongName, Duration);

        // Set text values to TextViews
        songTitleTextView.setText("Song Name: " + SongName);
        Log.d("SongAPI", "AlbumName2: " + AlbumName);
        albumNameTextView.setText("Album Name: " + AlbumName);
        Log.d("SongAPI", "AlbumName3: " + albumNameTextView.getText());
        durationTextView.setText("Duration: " + Duration);

        // Load image using Picasso library
        Picasso.get().load(Cover).into(albumCoverImageView);
        Log.d("SongAPI", "MusicReturn1: " + songReturn);

        new Thread(() -> {
            allIds = mDAO.getAllIds();
        }).start();
        ;
        binding.musicButtonSave.setOnClickListener(clk -> {

            Log.d("SongAPI", "MusicReturn2: " + songReturn);
            if (allIds.contains(songReturn.getMusicId())) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("This song already exists in the database.")
                        .setPositiveButton("OK", (dialog, which) -> {
                            // Do nothing or handle as needed
                        })
                        .show();
            } else {
                new Thread(() -> mDAO.insertSong(songReturn)).start();
                Snackbar.make(binding.getRoot(), "Song Added to Favorites", Snackbar.LENGTH_SHORT).show();
            }


        });

    }


}