package algonquin.cst2335.android_group_project;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class SunResultsAdapter extends RecyclerView.Adapter<SunResultsAdapter.ResultViewHolder> {

    private final List<String> newData = new ArrayList<>();
    private final List<String> pastData = new ArrayList<>();

    public void addNewResults(List<String> newData) {
        this.newData.addAll(newData);
        notifyDataSetChanged();
    }

    public void addPastResults(List<String> pastData) {
        this.pastData.addAll(pastData);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new ResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultViewHolder holder, int position) {
        if (position < newData.size()) {
            holder.textView.setText(newData.get(position));
        } else {
            int pastPosition = position - newData.size();
            holder.textView.setText(pastData.get(pastPosition));
        }
    }

    @Override
    public int getItemCount() {
        return newData.size() + pastData.size();
    }

    static class ResultViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public ResultViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(android.R.id.text1);
        }
    }
}
