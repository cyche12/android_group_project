package algonquin.cst2335.android_group_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import algonquin.cst2335.android_group_project.databinding.SunriseSearchBinding;

public class Sunrise_Search extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SunriseSearchBinding binding = SunriseSearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent homeIntent = new Intent(Sunrise_Search.this, MainActivity.class);
        EditText latitudeEditText = binding.xCoordinate;
        EditText longitudeEditText = binding.yCoordinate;
        Button searchButton = binding.searchButton;
        Button homeButton = binding.homeButton;

        homeButton.setOnClickListener(click -> startActivity(homeIntent));


        searchButton.setOnClickListener(click -> {
            String latitude = latitudeEditText.getText().toString().trim();
            String longitude = longitudeEditText.getText().toString().trim();


            if (!latitude.isEmpty() && !longitude.isEmpty()) {
                Intent nextIntent = new Intent(Sunrise_Search.this, Sunrise_Results.class);
                nextIntent.putExtra("latitude", latitude);
                nextIntent.putExtra("longitude", longitude);
                startActivity(nextIntent);
            } else {
                Snackbar.make(binding.getRoot(), "Please enter latitude and longitude", Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.sun_help) {
            showSunriseHelpDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showSunriseHelpDialog() {
        new AlertDialog.Builder(this)
                .setTitle("How to Use")
                .setMessage("Here are the instructions on how to use the sunrise feature:\n\n- Enter the latitude and longitude.\n- Click the search button to find sunrise information.")
                .setPositiveButton("OK", null)
                .show();
    }
}
