//Student: Jake Elliott//
//Student #040732505//
//Class: CST2335//
//Creation Date: 25/3/24//
//Group Members: Jake Elliott, Gabriel Hubert, Shilpi Sarkar, Piyalee Mangaraj//
//Project: Final Group Project//
//App: Sunrise/Sunset App//

package algonquin.cst2335.android_group_project;

import android.content.Intent;
import android.content.SharedPreferences;
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
import androidx.lifecycle.ViewModelProvider;

/**
 * Activity class for initiating a search for sunrise and sunset times based on user input latitude and longitude.
 * It uses the SunriseViewModel for persisting user input across configuration changes and also uses
 * SharedPreferences for longer term persistence. Provides a user interface for input, search initiation,
 * and navigation to other parts of the application.
 */
public class Sunrise_Search extends AppCompatActivity {

    /**
     * ViewModel instance for managing UI data.
     */
    private SunriseViewModel viewModel;

    /**
     * Initializes the activity, setting up the user interface and binding actions to buttons.
     * Retrieves and restores any previously entered latitude and longitude values from SharedPreferences.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SunriseSearchBinding binding = SunriseSearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(SunriseViewModel.class);

        Toolbar toolbar = binding.sunToolbar;
        setSupportActionBar(toolbar);

        SharedPreferences preferences = getSharedPreferences("SunriseAppPreferences", MODE_PRIVATE);
        String savedLatitude = preferences.getString("latitude", "");
        String savedLongitude = preferences.getString("longitude", "");
        viewModel.latitudeText.setValue(savedLatitude);
        viewModel.longitudeText.setValue(savedLongitude);

        binding.xCoordinate.setText(savedLatitude);
        binding.yCoordinate.setText(savedLongitude);

        Button backButton = binding.homeButton;
        backButton.setOnClickListener(click -> startActivity(new Intent(this, MainActivity.class)));

        Button searchButton = binding.searchButton;
        searchButton.setOnClickListener(click -> {
            String latitude = binding.xCoordinate.getText().toString().trim();
            String longitude = binding.yCoordinate.getText().toString().trim();

            viewModel.latitudeText.setValue(latitude);
            viewModel.longitudeText.setValue(longitude);
            preferences.edit().putString("latitude", latitude).putString("longitude", longitude).apply();

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

    /**
     * Initialize the contents of the Sunrise classes options menu.
     * @param menu Menu to be inflated.
     * @return True for the menu to be displayed.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sun_menu, menu);
        return true;
    }

    /**
     * Method for declaring what the menu items do.
     * Option one is the favorites menu.
     * Option two is the help icon.
     * @param item The menu item that was clicked.
     * @return False to allow normal menu processing to proceed, true to consume it here.
     */
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


    /**
     * Shows a help dialog providing guidance on how to use the application when the help button is clicked on the toolbar.
     */
    private void showHelpDialog() {
        new AlertDialog.Builder(this)
                .setTitle("How to Use")
                .setMessage("Enter your latitude and longitude into the fields and press the lookup button to find the sunrise and sunset time of those coordinates.\n\nTo save your past search, click on the Save button after a search.\n\nTo view saved search results, click on the star on the toolbar.\n\nTo delete a saved search, click on the search you wish to delete and press on the delete button.")
                .setPositiveButton("OK", null)
                .show();
    }
}
