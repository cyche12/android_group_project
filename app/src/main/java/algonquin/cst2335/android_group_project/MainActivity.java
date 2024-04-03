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
    import androidx.appcompat.app.AppCompatActivity;
    import androidx.appcompat.widget.Toolbar;

    import algonquin.cst2335.android_group_project.databinding.ActivityMainBinding;


    public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) { //onCreate function for MainActivity
    super.onCreate(savedInstanceState);

    ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater()); //Creating view binding for main activity
    setContentView(binding.getRoot()); // Setting Content view to main activity binding


    setSupportActionBar(binding.myToolbar); //Binding toolbar to ActivityMain view
    Toolbar myToolbar = findViewById(R.id.myToolbar); //Finding toolbar view
    Button btn1 = findViewById(R.id.button1);   // button1 connects to Sunrise/Sunset API
    Button btn2 = findViewById(R.id.button2);   // button2 connects to Recipe API
    Button btn3 = findViewById(R.id.button3);   // button3 is for Dictionary API
    Button btn4 = findViewById(R.id.button4);   // button4 connects to Song API
        Intent SunIntent = new Intent(MainActivity.this, Sunrise_Search.class);
        Intent RecipeIntent = new Intent(MainActivity.this, RecipeSearch.class);
        Intent Dictionaryintent = new Intent(MainActivity.this, DictionaryMainActivity.class);
        Intent Songintent = new Intent(MainActivity.this, SongAPI.class);


        //Button 1 OnClickListener moves to Sunrise Class//
        btn1.setOnClickListener(click -> startActivity(SunIntent));

        //Button 2 OnClickListener moves to Recipe Class//
        btn2.setOnClickListener(click -> startActivity(RecipeIntent));


        //Button 3 OnClickListener moves to Dictionary Class//
        btn3.setOnClickListener(click -> startActivity(Dictionaryintent));
        //Button 4 OnClickListener moves to Song Class//
        btn4.setOnClickListener(click -> startActivity(Songintent));
    }

    public boolean onCreateOptionsMenu(Menu menu){ //Menu Inflater//
        getMenuInflater().inflate(R.menu.my_menu,menu);
            return true;
    }

    //Toolbar Icon Selector//
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.item1) {
            startActivity(new Intent(MainActivity.this, Sunrise_Search.class)); //Sunrise/Sunset Search API class with item1
            return true;
        } else if (id == R.id.item2) {
            startActivity(new Intent(MainActivity.this, RecipeSearch.class)); //Recipe Search API class with item2
            return true;
        } else if (id == R.id.item3) {
            startActivity(new Intent(MainActivity.this, DictionaryMainActivity.class)); // Dictionary API class with item3
            return true;
        } else if (id == R.id.item4) {
            startActivity(new Intent(MainActivity.this, SongAPI.class)); //Song API class with item4
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
    }