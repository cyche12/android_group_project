package algonquin.cst2335.android_group_project;

import static algonquin.cst2335.android_group_project.R.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import algonquin.cst2335.android_group_project.databinding.ActivityRecipeBinding;
import algonquin.cst2335.android_group_project.databinding.ActivitySavedRecipesBinding;

public class SavedRecipes extends AppCompatActivity {
    private RecyclerView recyclerView;
    private SavedRecipeAdapter adapter;
    ActivitySavedRecipesBinding binding;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.recipe_toolbar, menu);

        return true;
    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem menuItem = menu.findItem(R.id.savedRecipeList);
        menuItem.setVisible(false); // Hide the menu item
        return super.onPrepareOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.savedRecipeList) {
            Intent intent = new Intent(SavedRecipes.this, SavedRecipes.class);
            startActivity(intent);
            return true;
        }
        if (item.getItemId() == R.id.recipeHelp) {
            AlertDialog.Builder builder = new AlertDialog.Builder(SavedRecipes.this);
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
        setContentView(layout.activity_saved_recipes);

        binding = ActivitySavedRecipesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.recipeToolbar);
        getSupportActionBar().setTitle("Gabriel's Fantastic Recipes");

        recyclerView = findViewById(R.id.savedRecycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SavedRecipeAdapter();
        recyclerView.setAdapter(adapter);



        RecipeDatabase.getInstance(this).savedRecipeDao().getAllSavedRecipes().observe(this, new Observer<List<SavedRecipeReturn>>() {
            @Override
            public void onChanged(List<SavedRecipeReturn> savedRecipes) {
                adapter.setSavedRecipes(savedRecipes);
            }
        });
    }


    private class SavedRecipeAdapter extends RecyclerView.Adapter<SavedRecipeAdapter.ViewHolder> {
        private List<SavedRecipeReturn> savedRecipes = new ArrayList<>();

        public void setSavedRecipes(List<SavedRecipeReturn> savedRecipes) {
            this.savedRecipes = savedRecipes;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(layout.saved_recipes, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            SavedRecipeReturn savedRecipe = savedRecipes.get(position);
            holder.bind(savedRecipe, position);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int clickedPosition = holder.getAdapterPosition();
                    if (clickedPosition != RecyclerView.NO_POSITION) {
                        String recipeId = savedRecipes.get(clickedPosition).getRecipeId();
                        Intent intent = new Intent(holder.itemView.getContext(), SavedRecipeDetails.class);
                        intent.putExtra("recipeId", recipeId);
                        intent.putExtra("position", clickedPosition);
                        holder.itemView.getContext().startActivity(intent);
                    }
                }
            });

        }

        @Override
        public int getItemCount() {
            return savedRecipes.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView titleText;
            TextView idText;
            ImageView recipeImage;
            Context context;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                titleText = itemView.findViewById(R.id.savedTitle);
                recipeImage = itemView.findViewById(R.id.savedImage);
                idText = itemView.findViewById(id.savedID);
                context = itemView.getContext(); // Get the context of the item view
            }

            public void bind(SavedRecipeReturn savedRecipe, int position) {
                titleText.setText(savedRecipe.getTitle());
                Picasso.get().load(savedRecipe.getImageUrl()).into(recipeImage);
                idText.setText(savedRecipe.getRecipeId());
            }
        }
    }
}