package algonquin.cst2335.android_group_project;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class ResultsAdapter extends RecyclerView.Adapter<ResultsAdapter.ResultViewHolder> {

    private final List<Sunrise_Data> data = new ArrayList<>();
    private final OnItemClickListener listener;

    public ResultsAdapter(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void updateResults(List<Sunrise_Data> newData) {
        data.clear(); // Clear existing data
        data.addAll(newData);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new ResultViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultViewHolder holder, int position) {
        holder.textView.setText("Sunrise: " + data.get(position).getSunriseTime() + ", Sunset: " + data.get(position).getSunsetTime());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class ResultViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public ResultViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            textView = itemView.findViewById(android.R.id.text1);
            itemView.setOnClickListener(v -> listener.onItemClick(data.get(getAdapterPosition())));
        }
    }

    public interface OnItemClickListener {
        void onItemClick(SunriseSunsetData item);
    }
}
