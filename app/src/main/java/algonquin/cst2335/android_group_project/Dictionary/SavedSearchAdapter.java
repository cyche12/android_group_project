package algonquin.cst2335.android_group_project.Dictionary;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.function.Consumer;

import algonquin.cst2335.android_group_project.Entity.SearchTerm;

public class SavedSearchAdapter extends RecyclerView.Adapter<SavedSearchAdapter.ViewHolder> {

    private final List<SearchTerm> searchTerms;
    private final Consumer<Integer> onTermClicked;

    public SavedSearchAdapter(List<SearchTerm> searchTerms, Consumer<Integer> onTermClicked) {
        this.searchTerms = searchTerms;
        this.onTermClicked = onTermClicked;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.termTextView.setText(searchTerms.get(position).word);
        holder.itemView.setOnClickListener(v -> onTermClicked.accept(searchTerms.get(position).id));
    }

    @Override
    public int getItemCount() {
        return searchTerms.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView termTextView;

        ViewHolder(View view) {
            super(view);
            termTextView = view.findViewById(android.R.id.text1);
        }
    }
}

