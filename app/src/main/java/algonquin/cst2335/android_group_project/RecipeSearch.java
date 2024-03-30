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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
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

public class RecipeSearch extends AppCompatActivity {

    ActivityRecipeBinding binding;
    private RecyclerView.Adapter myAdapter;
    ArrayList<RecipeReturn> recipes;
    RecipeViewModel recipeModel;
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

        //QUEUE
        RequestQueue queue = MySingleton.getInstance(this.getApplicationContext()).getRequestQueue();

        binding.searchButton.setOnClickListener(click ->{
            String searchText = binding.searchBar.getText().toString().trim();
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
            // Update the UI with the new list of recipes
            myAdapter.notifyDataSetChanged();
        });
    }
    public class MyRowHolder extends RecyclerView.ViewHolder {

        TextView titleText;
        TextView idText;
        ImageView recipeImage;

        public MyRowHolder(@NonNull View itemView) {
            super(itemView);
            titleText = itemView.findViewById(R.id.recipeTitle);
            idText = itemView.findViewById(R.id.recipeID);
            recipeImage = itemView.findViewById(R.id.imageView);
        }
    }

    }