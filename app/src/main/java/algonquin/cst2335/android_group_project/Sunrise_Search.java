package algonquin.cst2335.android_group_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import algonquin.cst2335.android_group_project.databinding.SunHistoryViewBinding;
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sun_menu, menu); // Inflate the main menu layout
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.sun_help) {
            showHelpDialog();
            return true;
        } else if (id == R.id.sun_favorite) {
            Intent historyIntent = new Intent(Sunrise_Search.this, SunHistoryAdapter.class);
            startActivity(historyIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void showHelpDialog() {
        // Display the help dialog with instructions
        new AlertDialog.Builder(this)
                .setTitle("How to Use")
                .setMessage("How to use the app:\n\n- Enter your longitude and latitude in the fields below, then click 'Lookup' when ready.\n- Save your searches with the Save button.\n- View saved searches with the History button.\n-")
                .setPositiveButton("OK", null)
                .show();
    }
}
