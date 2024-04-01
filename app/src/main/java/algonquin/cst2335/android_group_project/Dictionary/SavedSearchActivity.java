package algonquin.cst2335.android_group_project.Dictionary;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import algonquin.cst2335.android_group_project.DAO.DictionaryDao;
import algonquin.cst2335.android_group_project.DictionaryDatabase;
import algonquin.cst2335.android_group_project.R;
/**
 * Activity for displaying saved search terms.
 * This activity sets up a RecyclerView to list saved search terms and initiates navigation
 * to the DefinitionsActivity when a term is selected, passing the selected search term ID
 * to the DefinitionsActivity for displaying its definitions.
 * Purpose of the file: To display a list of saved search terms and navigate to definitions upon selection.
 * Author: Piyalee Mangaraj
 * Lab Section: CST2335 012
 * Creation Date: 1st April 2024
 */
public class SavedSearchActivity extends AppCompatActivity {

    private RecyclerView savedSearchRecyclerView;
    private SavedSearchAdapter adapter;
    private DictionaryViewModel viewModel;
    DictionaryDatabase db;
    DictionaryDao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_search);

        savedSearchRecyclerView = findViewById(R.id.savedSearchRecyclerView);
        savedSearchRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = Room.databaseBuilder(getApplicationContext(),
                DictionaryDatabase.class, "dictionary-database").allowMainThreadQueries().build();
        DictionaryDao dao = db.dictionaryDao();
        DictionaryViewModelFactory factory = new DictionaryViewModelFactory(dao);


        // Initialize the ViewModel
        viewModel = new ViewModelProvider(this,factory).get(DictionaryViewModel.class);

        viewModel.getAllSearchTerms().observe(this, searchTerms -> {
            adapter = new SavedSearchAdapter(searchTerms, this::displayDefinitionsForTerm);
            savedSearchRecyclerView.setAdapter(adapter);
        });
    }

    private void displayDefinitionsForTerm(int searchTermId) {
        Intent intent = new Intent(SavedSearchActivity.this, DefinitionsActivity.class);
        intent.putExtra("searchTermId", searchTermId); // Pass the searchTermId to DefinitionsActivity
        startActivity(intent);
    }
}

