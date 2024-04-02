package algonquin.cst2335.android_group_project;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SunHistoryAdapter extends RecyclerView.Adapter<SunHistoryAdapter.HistoryViewHolder> {

    private final List<String> historyData;

    public SunHistoryAdapter(List<String> historyData) {
        this.historyData = historyData;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        holder.textView.setText(historyData.get(position));
    }

    @Override
    public int getItemCount() {
        return historyData.size();
    }

    public void addPastResults(List<String> pastResults) {
        // Add past search results to the existing historyData list
        historyData.addAll(pastResults);
        notifyDataSetChanged(); // Notify the adapter of changes
    }

    static class HistoryViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(android.R.id.text1);
        }
    }
}
