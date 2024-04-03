package algonquin.cst2335.android_group_project;
/**
 * Purpose:ViewFavourites class is returning favorite song from the songlist and delete song  from the database
 * Author: Shilpi Sarkar
 * Lab section:012
 * Date created: March 31, 2024
 *
 */
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.ArrayList;
import java.util.List;

import algonquin.cst2335.android_group_project.databinding.ActivityViewFavouritesBinding;

public class ViewFavourites extends AppCompatActivity {
    /**
     * The binding instance for the  ActivityViewFavourites layout. This field is part of
     * the View Binding feature that facilitates safe interaction with views.
     */
    private ActivityViewFavouritesBinding binding;
    /**
     * The adapter for managing the data and views of a  RecyclerView within the application.
     */
    private RecyclerView.Adapter myAdapter;
    /**
     * An instance of  SongReturnDAO providing access to the database operations
     */

    SongReturnDAO mDAO;
    /**
     * A collection of SongReturn objects representing the user's favorite songs.
     */
    ArrayList<SongReturn> music_favourite_song_list = new ArrayList<>();

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
        binding = ActivityViewFavouritesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        SongDatabase db = Room.databaseBuilder(this.getApplicationContext(), SongDatabase.class, "Song_DB").build();
        mDAO = db.cmDAO();


        ImageView back = findViewById(R.id.musicBackButton2);
        back.setOnClickListener(v -> finish());
        // Initialize your RecyclerView and its adapter
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(myAdapter = new RecyclerView.Adapter<MyRowHolder>() {
            /**
             *
             * @param parent The ViewGroup into which the new View will be added after it is bound to
             *               an adapter position.
             * @param viewType The view type of the new View.
             *
             * @return
             */
            @NonNull
            @Override
            public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                View view = inflater.inflate(R.layout.favourite_song_list, parent, false);
                return new MyRowHolder(view);
            }

            /**
             *
             * @param holder The ViewHolder which should be updated to represent the contents of the
             *        item at the given position in the data set.
             * @param position The position of the item within the adapter's data set.
             */

            @Override
            public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {
                SongReturn obj = music_favourite_song_list.get(position);
                holder.SongTitles.setText(obj.getMusicSongName());
            }

            /**
             *
             * @return returning favourite song from list
             */

            @Override
            public int getItemCount() {
                return music_favourite_song_list.size();
            }

        });

        // Retrieve data from the database and populate the music_favourite_song_list
        // Assuming you have a method in your DAO to get all saved songs
        new Thread(() -> {
            List<SongReturn> savedSongs = mDAO.getAllSongs();
            runOnUiThread(() -> {
                music_favourite_song_list.clear();
                music_favourite_song_list.addAll(savedSongs);
                myAdapter.notifyDataSetChanged();
            });
        }).start();


    }


    class MyRowHolder extends RecyclerView.ViewHolder {
        TextView SongTitles;

        /**
         *
         * @param itemView The View for the single item row in the RecyclerView
         */

        public MyRowHolder(@NonNull View itemView) {
            super(itemView);
            SongTitles = itemView.findViewById(R.id.song_title_text_view);
            ImageView delete = itemView.findViewById(R.id.deleteSong);

            delete.setOnClickListener(clk -> {
                int position = getAdapterPosition();
                SongReturn songToDelete = music_favourite_song_list.get(position);

                // Delete the song from the list
                music_favourite_song_list.remove(position);

                // Notify the adapter about the change
                myAdapter.notifyItemRemoved(position);

                // Delete the song from the database
                new Thread(() -> {
                    mDAO.deleteSong(songToDelete);
                }).start();
                displayToast();
            });


            itemView.setOnClickListener(clk -> {
                int position = getAdapterPosition();
                SongReturn objDisplay = music_favourite_song_list.get(position);
                Intent music_details = new Intent(ViewFavourites.this, SongDetails.class);
                music_details.putExtra("SongNames", objDisplay.getMusicSongName());
                music_details.putExtra("AlbumNames", objDisplay.getMusicAlbumName());
                music_details.putExtra("Cover", objDisplay.getMusicPicture());
                music_details.putExtra("Duration", objDisplay.getMusicDuration());
                music_details.putExtra("id", objDisplay.getMusicId());
                startActivity(music_details);
            });
        }
    }

    /**
     * Displays a toast notification to the user indicating that a song has been successfully
     * deleted from their favourites.
     */

    private void displayToast() {
        Toast.makeText(this, "Song deleted from favourites", Toast.LENGTH_SHORT).show();
    }
}