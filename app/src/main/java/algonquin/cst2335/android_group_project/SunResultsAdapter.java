//Student: Jake Elliott//
//Student #040732505//
//Class: CST2335//
//Creation Date: 25/3/24//
//Group Members: Jake Elliott, Gabriel Hubert, Shilpi Sarkar, Piyalee Mangaraj//
//Project: Final Group Project//
//App: Sunrise/Sunset App//

package algonquin.cst2335.android_group_project;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

/**
 * Adapter class for managing and displaying new and past results in a RecyclerView.
 * This adapter handles the data set that consists of both new and past search results
 * for sunrise and sunset times. It updates the RecyclerView with the list of results.
 */
public class SunResultsAdapter extends RecyclerView.Adapter<SunResultsAdapter.ResultViewHolder> {

    /**
     * List to hold new search results for sunrise and sunset times.
     */
    private final List<String> newData = new ArrayList<>();

    /**
     * List to hold past search results for sunrise and sunset times.
     */
    private final List<String> pastData = new ArrayList<>();

    /**
     * Adds new search results to the adapter and notifies the RecyclerView to update.
     * @param newData The new search results to be added.
     */
    public void addNewResults(List<String> newData) {
        this.newData.addAll(newData);
        notifyDataSetChanged();
    }

    /**
     * Adds past search results to the adapter and notifies the RecyclerView to update.
     * @param pastData The past search results to be added.
     */
    public void addPastResults(List<String> pastData) {
        this.pastData.addAll(pastData);
        notifyDataSetChanged();
    }

    /**This is where the ViewHolder gets created and returned.
     * @param parent The ViewGroup into which the new View will be added after it is bound to the adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View.
     */
    @NonNull
    @Override
    public ResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new ResultViewHolder(view);
    }

    /**
     * Called by RecyclerView to display the data at each position.
     * This method updates the contents of the resultsViewHolder to display the item at each position.
     * @param holder The ViewHolder which updates to represent the contents of the item at each given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull ResultViewHolder holder, int position) {
        if (position < newData.size()) {
            holder.textView.setText(newData.get(position));
        } else {
            int pastPosition = position - newData.size();
            holder.textView.setText(pastData.get(pastPosition));
        }
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return newData.size() + pastData.size();
    }

    /**
     * Creating the view holder for the results of the search to be added to the RecyclerView
     */
    static class ResultViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        /**
         * Constructor for the ResultViewHolder.
         * @param itemView The view to connect the results to the TextView.
         */
        public ResultViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(android.R.id.text1);
        }
    }
}
