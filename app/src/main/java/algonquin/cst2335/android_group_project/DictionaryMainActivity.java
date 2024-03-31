package algonquin.cst2335.android_group_project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.ArrayList;
import java.util.List;

import algonquin.cst2335.android_group_project.DAO.DictionaryDao;
import algonquin.cst2335.android_group_project.Dictionary.DefinitionsAdapter;
import algonquin.cst2335.android_group_project.Dictionary.DictionaryApiRequest;
import algonquin.cst2335.android_group_project.Dictionary.SavedSearchActivity;
import algonquin.cst2335.android_group_project.Entity.Definition;
import algonquin.cst2335.android_group_project.Entity.SearchTerm;

public class DictionaryMainActivity extends AppCompatActivity {

    private EditText searchEditText;
    private Button searchButton;
    private RecyclerView definitionsRecyclerView;
    private DefinitionsAdapter definitionsAdapter;

    private String currentSearchTerm;
    private List<String> currentDefinitions = new ArrayList<>();

    private Button saveButton;

    private Button viewButton;

    DictionaryDatabase  db;
    DictionaryDao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dictionary_activity_main);

        searchEditText = findViewById(R.id.searchEditText);
        searchButton = findViewById(R.id.searchButton);
        definitionsRecyclerView = findViewById(R.id.definitionsRecyclerView);
        saveButton = findViewById(R.id.saveButton);
        viewButton = findViewById(R.id.viewButton);

        // Initialize RecyclerView
        definitionsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        definitionsAdapter = new DefinitionsAdapter(new ArrayList<>());
        definitionsRecyclerView.setAdapter(definitionsAdapter);

        DictionaryApiRequest dictionaryApiRequest = new DictionaryApiRequest(this);
        db = Room.databaseBuilder(getApplicationContext(),
                DictionaryDatabase.class, "dictionary-database").build();
        dao = db.dictionaryDao();

        // Load the saved search term when the activity starts
        loadSavedSearchTerm();

        searchButton.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                String word = searchEditText.getText().toString();
                                                if (!word.isEmpty()) {
                                                    currentSearchTerm = word;
                                                    saveSearchTerm(currentSearchTerm);
                                                }
                                                dictionaryApiRequest.getDefinitions(word, new DictionaryApiRequest.ResponseListener() {
                                                    @Override
                                                    public void onResponse(List<String> definitions) {
                                                        // Update RecyclerView with definitions
                                                        definitionsAdapter = new DefinitionsAdapter(definitions);
                                                        definitionsRecyclerView.setAdapter(definitionsAdapter);
                                                        currentDefinitions = definitions;
                                                    }

                                                    @Override
                                                    public void onError(String message) {
                                                        // Handle error, e.g., show a Toast
                                                        Toast.makeText(DictionaryMainActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                            }
                                        }
        );

        // Set up save button click listener
        saveButton.setOnClickListener(v -> {
            if (currentSearchTerm != null && !currentDefinitions.isEmpty()) {
                saveSearchTermWithDefinitions(currentSearchTerm, currentDefinitions);
            } else {
                Toast.makeText(DictionaryMainActivity.this, "No search term or definitions to save.", Toast.LENGTH_SHORT).show();
            }
        });

        viewButton.setOnClickListener(v -> {
            Intent intent = new Intent(DictionaryMainActivity.this, SavedSearchActivity.class);
            startActivity(intent);
        });


    }

    private void saveSearchTerm(String searchText) {
        SharedPreferences sharedPreferences = getSharedPreferences("DictionaryPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("lastSearchTerm", searchText);
        editor.apply();
    }

    private void loadSavedSearchTerm() {
        SharedPreferences sharedPreferences = getSharedPreferences("DictionaryPreferences", MODE_PRIVATE);
        String lastSearchTerm = sharedPreferences.getString("lastSearchTerm", "");
        searchEditText.setText(lastSearchTerm);
    }

    private void saveSearchTermWithDefinitions(String searchTerm, List<String> definitions) {
        new Thread(() -> {
            // Insert the search term and get its autogenerated ID
            SearchTerm st = new SearchTerm();
            st.word=searchTerm;
            long searchTermId = dao.insertSearchTerm(st);

            // For each definition, create a Definition object and insert it into the database
            for (String definition : definitions) {
                Definition def = new Definition();
                def.searchTermId = (int) searchTermId; // Cast to int; ensure your ID types match
                def.definition = definition;
                dao.insertDefinition(def);
            }

            // Show a toast message on successful save
            runOnUiThread(() -> {
                currentDefinitions.clear();
                definitionsAdapter.setDefinitions(currentDefinitions); // Make sure your adapter has a method to set definitions
                definitionsAdapter.notifyDataSetChanged();
                Toast.makeText(DictionaryMainActivity.this, "Definitions saved successfully", Toast.LENGTH_SHORT).show();
            });
        }).start();
    }

}
