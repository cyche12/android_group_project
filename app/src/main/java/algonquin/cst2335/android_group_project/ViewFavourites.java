package algonquin.cst2335.android_group_project;

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
    private ActivityViewFavouritesBinding binding;
    private RecyclerView.Adapter myAdapter;

    SongReturnDAO mDAO;
    ArrayList<SongReturn> music_favourite_song_list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewFavouritesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Database db = Room.databaseBuilder(this.getApplicationContext(), Database.class, "Song_DB").build();
        mDAO = db.cmDAO();


        ImageView back = findViewById(R.id.musicBackButton2);
        back.setOnClickListener(v -> finish());
        // Initialize your RecyclerView and its adapter
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(myAdapter = new RecyclerView.Adapter<MyRowHolder>() {
            @NonNull
            @Override
            public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                View view = inflater.inflate(R.layout.favourite_song_list, parent, false);
                return new MyRowHolder(view);
            }

            @Override
            public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {
                SongReturn obj = music_favourite_song_list.get(position);
                holder.SongTitles.setText(obj.getMusicSongName());
            }

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

    private void displayToast() {
        Toast.makeText(this, "Song deleted from favourites", Toast.LENGTH_SHORT).show();
    }
}