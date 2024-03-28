package algonquin.cst2335.android_group_project;

import static androidx.recyclerview.widget.RecyclerView.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import algonquin.cst2335.android_group_project.databinding.ActivityRecipeBinding;
import algonquin.cst2335.android_group_project.databinding.RecipeSearchBinding;

public class RecipeSearch extends AppCompatActivity {

    ActivityRecipeBinding binding;
    private RecyclerView.Adapter myAdapter;
    ArrayList<String> messages = new ArrayList<>();
    RecipeViewModel recipeModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recipeModel = new ViewModelProvider(this).get(RecipeViewModel.class);
        messages = recipeModel.messages.getValue();
        if(messages == null)
        {
            recipeModel.messages.postValue( messages = new ArrayList<String>());
        }

        binding = ActivityRecipeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //QUEUE
        RequestQueue queue = MySingleton.getInstance(this.getApplicationContext()).getRequestQueue();

        binding.searchButton.setOnClickListener(click ->{
            messages.add(binding.searchBar.getText().toString());
            myAdapter.notifyItemInserted(messages.size()-1);
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
                String obj = messages.get(position);
                holder.titleText.setText(obj);
                holder.idText.setText(obj);
            }

            @Override
            public int getItemCount() {
                return messages.size();
            }
            @Override
            public int getItemViewType(int position){
                return 0;
            }
        });
        binding.recipeRecycleView.setLayoutManager(new LinearLayoutManager(this));
    }
    public class MyRowHolder extends RecyclerView.ViewHolder {

        TextView titleText;
        TextView idText;
        public MyRowHolder(@NonNull View itemView) {
                super(itemView);
                titleText = itemView.findViewById(R.id.recipeTitle);
                idText = itemView.findViewById(R.id.recipeID);
            }
        }
    }