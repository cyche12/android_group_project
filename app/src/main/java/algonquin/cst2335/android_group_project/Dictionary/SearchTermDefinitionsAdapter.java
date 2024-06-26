package algonquin.cst2335.android_group_project.Dictionary;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.util.Consumer;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import algonquin.cst2335.android_group_project.Entity.Definition;

/**
 * An adapter for RecyclerView to display a list of term definitions.
 * This adapter is designed for use in a context where each definition can be clicked,
 * typically to trigger a delete action or another form of interaction.
 * Purpose of the file: To provide an interface between a list of definition data and
 * a RecyclerView that displays them, handling the layout inflation and binding of data
 * to individual views within the RecyclerView.
 * Author: Piyalee Mangaraj
 * Lab Section: CST2335 012
 * Creation Date: 1st April 2024
 */

public class SearchTermDefinitionsAdapter extends RecyclerView.Adapter<SearchTermDefinitionsAdapter.ViewHolder> {
    private List<Definition> definitions;
    private final Consumer<Definition> onDeleteClicked;

    public SearchTermDefinitionsAdapter(List<Definition> definitions, Consumer<Definition> onDeleteClicked) {
        this.definitions = definitions;
        this.onDeleteClicked = onDeleteClicked;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Definition definition = definitions.get(position);
        holder.definitionTextView.setText(definition.definition);
        holder.itemView.setOnClickListener(v -> onDeleteClicked.accept(definition)); // Handle delete click
    }


    @Override
    public int getItemCount() {
        return definitions != null ? definitions.size() : 0;
    }

    // Update the list of definitions and notify the adapter of data set changes
    public void setDefinitions(List<Definition> newDefinitions) {
        this.definitions = newDefinitions;
        notifyDataSetChanged();
    }

    // ViewHolder class to hold definition items
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView definitionTextView;

        ViewHolder(View view) {
            super(view);
            // Assuming the layout used is android.R.layout.simple_list_item_1
            definitionTextView = view.findViewById(android.R.id.text1);
        }
    }
}

