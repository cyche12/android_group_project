package algonquin.cst2335.android_group_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import algonquin.cst2335.android_group_project.databinding.ActivityRecipeDetailsBinding;
import algonquin.cst2335.android_group_project.databinding.ActivitySavedRecipeDetailsBinding;

public class SavedRecipeDetails extends AppCompatActivity {
    String summary;
    String recipeUrl;
    String imageUrl;
    String title;
    String recipeID;
    ActivitySavedRecipeDetailsBinding binding;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.recipe_toolbar, menu);
        return true;
    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem menuItem = menu.findItem(R.id.savedRecipeList);
        menuItem.setVisible(false);
        return super.onPrepareOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.savedRecipeList) {
            Intent intent = new Intent(SavedRecipeDetails.this, SavedRecipes.class);
            startActivity(intent);
            return true;
        }
        if (item.getItemId() == R.id.recipeHelp) {
            AlertDialog.Builder builder = new AlertDialog.Builder(SavedRecipeDetails.this);
            builder.setMessage("Enter a recipe in the search bar and press the search button to " +
                    "search for recipes.\n" +
                    "To view recipe details, click on the recipe you wish to view.\n" +
                    "To save a recipe, click on the Save button in the recipe details.\n" +
                    "To view saved recipes, click on the star on the toolbar.\n" +
                    "To delete a recipe from your saved recipe, click on the recipe you wish to " +
                    "delete and press on the delete button.").setTitle("Help").show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RecipeDatabase db = Room.databaseBuilder(getApplicationContext(), RecipeDatabase.class, "recipe_database").build();
        SavedRecipeDao savedRecipeDao = db.savedRecipeDao();

        binding = ActivitySavedRecipeDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.recipeToolbar);
        getSupportActionBar().setTitle("Gabriel's Fantastic Recipes");

        String recipeId = getIntent().getStringExtra("recipeId");
        int position = getIntent().getIntExtra("position", -1);

        String url = "https://api.spoonacular.com/recipes/"+ recipeId +"/information?apiKey=a91cbc1da183408a8b42d047304e7bf4";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            JSONObject jsonObject = response;
                            summary = jsonObject.getString("summary");
                            recipeUrl = jsonObject.getString("spoonacularSourceUrl");
                            imageUrl = jsonObject.getString("image");
                            title = jsonObject.getString("title");
                            recipeID = String.valueOf(jsonObject.getInt("id"));

                            ImageView recipeImage = findViewById(R.id.savedImageDetails);
                            TextView recipeSum = findViewById(R.id.savedRecipeSum);
                            TextView recipeURL = findViewById(R.id.recipeURLDetails);

                            Picasso.get().load(imageUrl).into(recipeImage);
                            recipeSum.setText(Html.fromHtml(summary));
                            recipeURL.setText(recipeUrl);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                    }
                });
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);

        binding.deleteButton.setOnClickListener(click->{
            Executor executor = Executors.newSingleThreadExecutor();
            executor.execute(() -> {
                savedRecipeDao.deleteById(recipeID);
                runOnUiThread(() -> {
                    Toast.makeText(SavedRecipeDetails.this, "Recipe deleted", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK);
                });
            });
            Intent intent = new Intent(SavedRecipeDetails.this, SavedRecipes.class);
            startActivity(intent);
        });
    }
}