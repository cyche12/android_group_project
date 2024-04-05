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
import java.util.List;

/**
 * Adapter for a RecyclerView that displays the history of sunrise and sunset searches.
 * This class is responsible for converting the list of history data strings into view items that will be displayed in the RecyclerView.
 */
public class SunHistoryAdapter extends RecyclerView.Adapter<SunHistoryAdapter.HistoryViewHolder> {

    /**
     * The list of history data strings that the adapter uses to bind views.
     */
    private final List<String> historyData;

    /**
     * Constructs a SunHistoryAdapter with the specified list of history data.
     * @param historyData The data to be displayed by the adapter.
     */
    public SunHistoryAdapter(List<String> historyData) {
        this.historyData = historyData;
    }

    /**
     * @param parent The view will be added after it is bound to an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     */
    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new HistoryViewHolder(view);
    }

    /**onBindViewHolder to bind the history data to the textview.
     * @param holder The ViewHolder which should be updated to represent the contents of the item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        holder.textView.setText(historyData.get(position));
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return historyData.size();
    }

    /**
     * Adds a list of past search results to the adapter's data set.
     * @param pastResults The list of past search results to add.
     */
    public void addPastResults(List<String> pastResults) {
        historyData.addAll(pastResults);
        notifyDataSetChanged();
    }

    /**
     * A HistoryViewHolder places itself within the RecyclerView.
     */
    static class HistoryViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        /**
         * Constructs a new HistoryViewHolder instance.
         * @param itemView The view that will display the data.
         */
        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(android.R.id.text1);
        }
    }
}
