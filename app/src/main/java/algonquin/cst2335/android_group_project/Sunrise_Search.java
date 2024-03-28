package algonquin.cst2335.android_group_project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import algonquin.cst2335.android_group_project.databinding.SunriseSearchBinding;

public class Sunrise_Search extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SunriseSearchBinding binding = SunriseSearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        EditText latitudeEditText = findViewById(R.id.y_coordinate);
        EditText longitudeEditText = findViewById(R.id.x_coordinate);
        Button searchButton = findViewById(R.id.search_button);

        searchButton.setOnClickListener(click -> {
            String latitudeInputText = latitudeEditText.getText().toString().trim();
            String longitudeInputText = longitudeEditText.getText().toString().trim();

            if (!latitudeInputText.isEmpty() && !longitudeInputText.isEmpty()) {
                // Construct URL and pass latitude and longitude to Sunrise_Results activity
                String websiteURL = "https://api.sunrisesunset.io/json?lat=" + latitudeInputText + "&lng=" + longitudeInputText + "&timezone=UTC&date=today";
                Intent intent = new Intent(Sunrise_Search.this, Sunrise_Results.class);
                intent.putExtra("websiteURL", websiteURL);
                intent.putExtra("latitude", latitudeInputText);
                intent.putExtra("longitude", longitudeInputText);
                startActivity(intent);
            } else {
                Toast.makeText(Sunrise_Search.this, "Please enter both latitude and longitude", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
