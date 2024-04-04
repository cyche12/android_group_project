//Student: Jake Elliott//
//Student #040732505//
//Class: CST2335//
//Creation Date: 25/3/24//
//Group Members: Jake Elliott, Gabriel Hubert, Shilpi Sarkar, Piyalee Mangaraj//
//Project: Final Group Project//
//App: Sunrise/Sunset App//

package algonquin.cst2335.android_group_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.snackbar.Snackbar;

import algonquin.cst2335.android_group_project.databinding.SunriseSearchBinding;

public class Sunrise_Search extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SunriseSearchBinding binding = SunriseSearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = binding.sunToolbar;
        setSupportActionBar(toolbar);

        Button searchButton = binding.searchButton;
        searchButton.setOnClickListener(click -> {
            String latitude = binding.xCoordinate.getText().toString().trim();
            String longitude = binding.yCoordinate.getText().toString().trim();

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
        getMenuInflater().inflate(R.menu.sun_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.sun_favorite) {
            Intent historyIntent = new Intent(this, SunriseHistory.class);
            startActivity(historyIntent);
            return true;
        } else if (item.getItemId() == R.id.sun_help) {
            showHelpDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showHelpDialog() {
        new AlertDialog.Builder(this)
                .setTitle("How to Use")
                .setMessage("Enter your latitude and longitude into the fields press the lookup button to find the sunrise and sunset time of those coordinates.\n\nTo save your past search, click on the Save button after a search.\n\nTo view saved search results, click on the star on the toolbar.\n\nTo delete a saved search, click on the search you wish to delete and press on the delete button.")
                .setPositiveButton("OK", null)
                .show();
    }
}
