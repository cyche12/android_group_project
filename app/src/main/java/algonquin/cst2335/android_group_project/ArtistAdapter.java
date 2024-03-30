package algonquin.cst2335.android_group_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ArtistAdapter extends RecyclerView.Adapter<ArtistAdapter.ArtistViewHolder> {

    private Context context;
    private List<Artist> artistList;

    public ArtistAdapter(Context context, List<Artist> artistList) {
        this.context = context;
        this.artistList = artistList;
    }

    @NonNull
    @Override
    public ArtistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_artist, parent, false);
        return new ArtistViewHolder(view);
    }

    // Method to update the whole dataset
    public void updateArtists(List<Artist> newArtists) {
        artistList.clear();
        artistList.addAll(newArtists);
        notifyDataSetChanged(); // Notifies the adapter that the entire data set has changed
    }

    // Method to add a single artist
    public void addArtist(Artist artist) {
        artistList.add(artist);
        notifyItemInserted(artistList.size() - 1); // Notifies the adapter of the new item added
    }



    @Override
    public void onBindViewHolder(@NonNull ArtistViewHolder holder, int position) {
        Artist artist = artistList.get(position);
        holder.artistName.setText(artist.getName());
        // If using an image loading library like Glide or Picasso:
        // Glide.with(context).load(artist.getImageUrl()).into(holder.artistImage);
    }

    @Override
    public int getItemCount() {
        return artistList.size();
    }

    static class ArtistViewHolder extends RecyclerView.ViewHolder {
        TextView artistName;
        ImageView artistImage;

        public ArtistViewHolder(@NonNull View itemView) {
            super(itemView);
            artistName = itemView.findViewById(R.id.artistName);
            artistImage = itemView.findViewById(R.id.artistImage); // If you have an ImageView in your item layout
        }
    }
}
