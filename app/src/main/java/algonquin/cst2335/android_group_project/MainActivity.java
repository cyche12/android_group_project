package algonquin.cst2335.android_group_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText searchEditText;
    private Button searchButton;
    private RecyclerView definitionsRecyclerView;
    private DefinitionsAdapter definitionsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchEditText = findViewById(R.id.searchEditText);
        searchButton = findViewById(R.id.searchButton);
        definitionsRecyclerView = findViewById(R.id.definitionsRecyclerView);

        // Initialize RecyclerView
        definitionsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        definitionsAdapter = new DefinitionsAdapter(new ArrayList<>());
        definitionsRecyclerView.setAdapter(definitionsAdapter);

        DictionaryApiRequest dictionaryApiRequest = new DictionaryApiRequest(this);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                        Toast.makeText(MainActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
}