package algonquin.cst2335.android_group_project;

import static androidx.recyclerview.widget.RecyclerView.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;

import algonquin.cst2335.android_group_project.databinding.ActivityRecipeBinding;
import algonquin.cst2335.android_group_project.databinding.RecipeSearchBinding;
/**
 * Purpose:
 * Creation Date:
 *@author Gabriel Hubert
 *@version 1.0
 */
public class RecipeSearch extends AppCompatActivity {
    /** Allows to interact with views. */
    ActivityRecipeBinding binding;
    /** Sets an adapter for the Recycle View. */
    private RecyclerView.Adapter myAdapter;
    /** Array to hold recipes. */
    ArrayList<RecipeReturn> recipes;
    /** View Model for the recipes. */
    RecipeViewModel recipeModel;

    /** Clears the Recycle View. */
    private void clearRecyclerView() {
        recipes.clear();
        myAdapter.notifyDataSetChanged();
    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem menuItem = menu.findItem(R.id.savedRecipeList);
        menuItem.setVisible(true);
        return super.onPrepareOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.savedRecipeList) {
            Intent intent = new Intent(RecipeSearch.this, SavedRecipes.class);
            startActivity(intent);
            return true;
        }
        if (item.getItemId() == R.id.recipeHelp) {
            AlertDialog.Builder builder = new AlertDialog.Builder(RecipeSearch.this);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.recipe_toolbar, menu);
        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recipeModel = new ViewModelProvider(this).get(RecipeViewModel.class);
        recipes = recipeModel.recipes.getValue();
        if(recipes == null)
        {
            recipes = new ArrayList<>();
            recipeModel.recipes.postValue(recipes);
        }

        binding = ActivityRecipeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.recipeToolbar);
        getSupportActionBar().setTitle("Gabriel's Fantastic Recipes");

        SharedPreferences prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        String sharedPref = prefs.getString("Search", "");
        EditText searchBar = findViewById(R.id.searchBar);
        searchBar.setText(sharedPref);


        binding.searchButton.setOnClickListener(click ->{
            clearRecyclerView();
            String searchText = binding.searchBar.getText().toString().trim();
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("Search",  searchBar.getText().toString());
            editor.apply();
            if (!searchText.isEmpty()) {
                String url = "https://api.spoonacular.com/recipes/complexSearch?query=" + searchText + "&apiKey=3eede3908bc14abfa1619421c6fcefea";


                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                        (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                try{
                                    JSONArray jsonArray = response.getJSONArray("results");
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                                        String title = jsonObject.getString("title");
                                        String id = String.valueOf(jsonObject.getInt("id"));
                                        String imageUrl = jsonObject.getString("image");
                                        RecipeReturn recipe = new RecipeReturn(title, id, imageUrl);
                                        recipes.add(recipe);
                                        myAdapter.notifyItemInserted(recipes.size()-1);
                                    }
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

            }
        });

        binding.recipeRecycleView.setAdapter(myAdapter = new RecyclerView.Adapter<MyRowHolder>() {
            @NonNull
            @Override
            public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                RecipeSearchBinding binding = RecipeSearchBinding.inflate(getLayoutInflater());
                return new MyRowHolder(binding.getRoot());
            }

            @Override
            public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {
                RecipeReturn obj = recipes.get(position);
                holder.titleText.setText(obj.getTitle());
                holder.idText.setText(obj.getId());
                Picasso.get().load(obj.getImage()).into(holder.recipeImage);
            }

            @Override
            public int getItemCount() {
                return recipes.size();
            }
            @Override
            public int getItemViewType(int position){
                return 0;
            }
        });
        binding.recipeRecycleView.setLayoutManager(new LinearLayoutManager(this));

        recipeModel.recipes.observe(this, newRecipes -> {
            myAdapter.notifyDataSetChanged();
        });
    }

    public class MyRowHolder extends RecyclerView.ViewHolder {
        /** This holds the text for the recipe title. */
        TextView titleText;
        /** This holds the text for the recipe id. */
        TextView idText;
        /** This holds the image for the recipe image. */
        ImageView recipeImage;
        /** This sets the context. */
        Context context;

        /**
         * This function adds the set content to the recycler view.
         * @param itemView The item in the recycler view.
         */
        public MyRowHolder(@NonNull View itemView) {
            super(itemView);
            titleText = itemView.findViewById(R.id.recipeTitle);
            idText = itemView.findViewById(R.id.recipeID);
            recipeImage = itemView.findViewById(R.id.imageView);
            context = itemView.getContext(); // Get the context of the item view
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String recipeId = idText.getText().toString();

                    Intent intent = new Intent(context, RecipeDetails.class);
                    intent.putExtra("recipeId", recipeId);
                    context.startActivity(intent);
                }
            });
        }
    }

    }