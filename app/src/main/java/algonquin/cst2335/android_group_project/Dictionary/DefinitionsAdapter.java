package algonquin.cst2335.android_group_project.Dictionary;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
/**
 * Adapter class for RecyclerView that displays a list of definitions.
 * This adapter is responsible for creating ViewHolders that contain TextViews,
 * and binding those TextViews to the definitions data provided to the adapter.
 *
 * Purpose of the file: To provide an adapter for RecyclerView that handles the display
 * of dictionary definitions in a list format.
 *  Author: Piyalee Mangaraj
 *  Lab Section: CST2335 012
 *  Creation Date: 1st April 2024
 */
public class DefinitionsAdapter extends RecyclerView.Adapter<DefinitionsAdapter.ViewHolder> {

    private List<String> definitions;

    public DefinitionsAdapter(List<String> definitions) {
        this.definitions = definitions;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.definitionTextView.setText(definitions.get(position));
    }

    @Override
    public int getItemCount() {
        return definitions.size();
    }

    public void setDefinitions(List<String> currentDefinitions) {
        this.definitions = currentDefinitions;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView definitionTextView;

        public ViewHolder(View view) {
            super(view);
            definitionTextView = view.findViewById(android.R.id.text1);
        }
    }
}

