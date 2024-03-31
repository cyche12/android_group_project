package algonquin.cst2335.android_group_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class RecipeDetails extends AppCompatActivity {
    String summary;
    String recipeUrl;

    String imageUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
        String recipeId = getIntent().getStringExtra("recipeId");

        String url = "https://api.spoonacular.com/recipes/"+ recipeId +"/information?apiKey=3eede3908bc14abfa1619421c6fcefea";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            JSONObject jsonObject = response;
                            summary = jsonObject.getString("summary");
                            recipeUrl = jsonObject.getString("spoonacularSourceUrl");
                            imageUrl = jsonObject.getString("image");

                            ImageView recipeImage = findViewById(R.id.imageDetails);
                            TextView recipeSum = findViewById(R.id.recipeSum);
                            TextView recipeURL = findViewById(R.id.recipeURL);

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


    }
}