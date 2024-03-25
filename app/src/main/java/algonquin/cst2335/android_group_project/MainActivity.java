//Student: Jake Elliott//
//Student #040732505//
//Class: CST2335//
//Group Members: Jake Elliott, Gabriel Hubert, Shilpi Sarkar, Piyalee Mangaraj//
//Project: Final Group Project//
//App: Sunrise/Sunset App//

package algonquin.cst2335.android_group_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import algonquin.cst2335.android_group_project.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater()); //Binding View to MainActivity//
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        EditText latitudeEditText = findViewById(R.id.y_coordinate); //Finding Latitude EditText Input//
        EditText longitudeEditText = findViewById(R.id.x_coordinate); //Finding Longitude EditText Input//

        Button searchButton = findViewById(R.id.search_button); //Finding Search button//
        searchButton.setOnClickListener( click -> { //Setting OnClick listener for the search button//
            String latitudeInputText = latitudeEditText.getText().toString().trim(); //ToString for input text//
            String longitudeInputText = longitudeEditText.getText().toString().trim(); //ToString for input text//

            if (!latitudeInputText.isEmpty() && !longitudeInputText.isEmpty()) { //Correct Search Parameters//
                Toast.makeText(MainActivity.this, "Searching these coordinates", Toast.LENGTH_SHORT).show();
                String websiteURL = ("https://api.sunrisesunset.io/json?lat=" + latitudeInputText + "&lng=" + longitudeInputText + "&timezone=UTC&date=today");
                Intent searchIntent = new Intent(MainActivity.this, SecondActivity.class); //Intent to move to page two//
                searchIntent.putExtra("websiteURL", websiteURL);
                startActivity(searchIntent); //Start move to page two//
            }
            else if (!latitudeInputText.isEmpty()) { //Incorrect Latitude Search Parameter//
                Toast.makeText(MainActivity.this, "Invalid Latitude", Toast.LENGTH_SHORT).show();

            } else if (!longitudeInputText.isEmpty()) { //Incorrect Longitude Search Parameter//
                Toast.makeText(MainActivity.this, "Invalid Longitude", Toast.LENGTH_SHORT).show();
            }

            else { //Incorrect Search Parameters//
                Toast.makeText(MainActivity.this, "Invalid Search", Toast.LENGTH_SHORT).show();
            }
        });


    }
}