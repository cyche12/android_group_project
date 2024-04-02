package algonquin.cst2335.android_group_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import algonquin.cst2335.android_group_project.databinding.SunriseSearchBinding;

public class Sunrise_Search extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SunriseSearchBinding binding = SunriseSearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        EditText latitudeEditText = binding.xCoordinate;
        EditText longitudeEditText = binding.yCoordinate;
        Button searchButton = binding.searchButton;

        searchButton.setOnClickListener(view -> {
            String latitude = latitudeEditText.getText().toString().trim();
            String longitude = longitudeEditText.getText().toString().trim();


            if (!latitude.isEmpty() && !longitude.isEmpty()) {
                Intent nextIntent = new Intent(Sunrise_Search.this, Sunrise_Results.class);
                nextIntent.putExtra("latitude", latitude);
                nextIntent.putExtra("longitude", longitude);
                startActivity(nextIntent);
            } else {
                Snackbar.make(view, "Please enter latitude and longitude", Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.sun_help) {
            // Handle the action for the help item
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}
