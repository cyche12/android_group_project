package algonquin.cst2335.android_group_project.Dictionary;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.ArrayList;

import algonquin.cst2335.android_group_project.DAO.DictionaryDao;
import algonquin.cst2335.android_group_project.DictionaryDatabase;
import algonquin.cst2335.android_group_project.Entity.Definition;
import algonquin.cst2335.android_group_project.Entity.SearchTerm;
import algonquin.cst2335.android_group_project.R;
/**
 * Activity for displaying definitions of a particular search term.
 * This activity sets up a RecyclerView to list definitions, allows users to delete definitions,
 * and displays the search term in the title.
 *
 * Author: Piyalee Mangaraj
 * Lab Section: CST2335 012
 * Creation Date: 1st April 2024
 */
public class DefinitionsActivity extends AppCompatActivity {
    private RecyclerView definitionsRecyclerView;
    private SearchTermDefinitionsAdapter definitionsAdapter;
    private DictionaryViewModel viewModel;
    private int searchTermId;
    private TextView titleTextView;
    DictionaryDatabase db;
    DictionaryDao dao;

    /**
     * Initializes the activity, sets up the RecyclerView, adapter, and ViewModel.
     * Fetches the definitions for the given search term and updates the UI accordingly.
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *                           previously being shut down then this Bundle contains the data it most
     *                           recently supplied in onSaveInstanceState(Bundle). Note: Otherwise it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_definitions);

        definitionsRecyclerView = findViewById(R.id.definitionsRecyclerView);
        titleTextView = findViewById(R.id.titleTextView);
        definitionsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        definitionsAdapter = new SearchTermDefinitionsAdapter(new ArrayList<>(), this::showDeleteConfirmationDialog);
        definitionsRecyclerView.setAdapter(definitionsAdapter);

        searchTermId = getIntent().getIntExtra("searchTermId", -1);
        db = Room.databaseBuilder(getApplicationContext(),
                DictionaryDatabase.class, "dictionary-database").allowMainThreadQueries().build();
        DictionaryDao dao = db.dictionaryDao();
        DictionaryViewModelFactory factory = new DictionaryViewModelFactory(dao);
        viewModel = new ViewModelProvider(this, factory).get(DictionaryViewModel.class);

        viewModel.getDefinitionsForSearchTerm(searchTermId).observe(this, definitions -> definitionsAdapter.setDefinitions(definitions));
        // Fetch the searchTerm string by searchTermId and set the title
        new Thread(() -> {
            // Assuming you have a method getSearchTermById in your DAO
            SearchTerm searchTerm = dao.getSearchTermById(searchTermId);
            runOnUiThread(() -> {
                // Set the dynamic title including the search term
                titleTextView.setText("Definition for Term: " + searchTerm.word);
            });
        }).start();
    }
    /**
     * Shows a confirmation dialog to the user when they attempt to delete a definition.
     * If the user confirms, the definition is deleted from the database.
     *
     * @param definition The definition that the user has chosen to delete.
     */
    private void showDeleteConfirmationDialog(Definition definition) {
        new AlertDialog.Builder(this)
                .setTitle("Delete Definition")
                .setMessage("Are you sure you want to delete this definition?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    viewModel.deleteDefinition(definition);
                    // Optionally, refresh the definitions list
                    //viewModel.getDefinitionsForSearchTerm(searchTermId).observe(this, definitions -> definitionsAdapter.setDefinitions(definitions));
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}

