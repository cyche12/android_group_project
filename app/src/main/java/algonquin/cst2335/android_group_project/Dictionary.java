package algonquin.cst2335.android_group_project;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import algonquin.cst2335.android_group_project.databinding.DictionaryBinding;

public class Dictionary extends AppCompatActivity {

    private EditText searchEditText;
    private RecyclerView definitionsRecyclerView;
    private DefinitionsAdapter definitionsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DictionaryBinding binding = DictionaryBinding.inflate(getLayoutInflater()); //Binding View to MainActivity//

        setContentView(binding.getRoot());

        searchEditText = findViewById(R.id.searchEditText);
        Button searchButton = findViewById(R.id.searchButton);
        definitionsRecyclerView = findViewById(R.id.definitionsRecyclerView);

        // Initialize RecyclerView
        definitionsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        definitionsAdapter = new DefinitionsAdapter(new ArrayList<>());
        definitionsRecyclerView.setAdapter(definitionsAdapter);

        DictionaryApiRequest dictionaryApiRequest = new DictionaryApiRequest(this);

        searchButton.setOnClickListener(v -> {
            String word = searchEditText.getText().toString();
            dictionaryApiRequest.getDefinitions(word, new DictionaryApiRequest.ResponseListener() {
                @Override
                public void onResponse(List<String> definitions) {
                    // Update RecyclerView with definitions
                    definitionsAdapter = new DefinitionsAdapter(definitions);
                    definitionsRecyclerView.setAdapter(definitionsAdapter);
                }

                @Override
                public void onError(String message) {
                    // Handle error, e.g., show a Toast
                    Toast.makeText(Dictionary.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                }
            });
        });

    }
}