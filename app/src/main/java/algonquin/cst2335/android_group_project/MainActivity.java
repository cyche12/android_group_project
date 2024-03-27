//Student: Jake Elliott//
//Student #040732505//
//Class: CST2335//
//Group Members: Jake Elliott, Gabriel Hubert, Shilpi Sarkar, Piyalee Mangaraj//
//Project: Final Group Project//
//App: Sunrise/Sunset App//

package algonquin.cst2335.android_group_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import algonquin.cst2335.android_group_project.databinding.ActivityMainBinding;
    @Override

        public class MainActivity extends AppCompatActivity {

            /** This holds the text at the centre of the screen*/
            ActivityMainBinding binding;

            /**
             * Entry point of the application.
             *
             * @param savedInstanceState The saved instance state.
             */
            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);

                binding = ActivityMainBinding.inflate(getLayoutInflater());
                setContentView(binding.getRoot());

                //Toolbar toolbar = findViewById(R.id.myToolbar);
                setSupportActionBar(binding.myToolbar);

                Button btn1 = findViewById(R.id.button1);   // button1 connects to Sunrise/Sunset API
                Button btn2 = findViewById(R.id.button2);   // button2 connects to Recipe API
                Button btn3 = findViewById(R.id.button3);   // button3 is for Dictionary API
                Button btn4 = findViewById(R.id.button4);   // button4 connects to deezer song API
                btn1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(MainActivity.this, Sunrise.class);
                        startActivity(intent);
                    }
                });

                btn2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(MainActivity.this, Recipe.class);
                        startActivity(intent);
                    }
                });

                /* Dictionary API component onclick button code*/
                btn3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(MainActivity.this, Dictionary.class);
                        startActivity(intent);
                    }
                });
                btn4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(MainActivity.this, Song.class);
                        startActivity(intent);
                    }
                });
            }
            @Override
            public boolean onCreateOptionsMenu(Menu menu){
                getMenuInflater().inflate(R.menu.my_menu,menu);
                return true;
            }

            /* Toolbar Icon selection code for each component*/
            @Override
            public boolean onOptionsItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.item1) {
                    startActivity(new Intent(MainActivity.this, Sunrise.class));
                    return true;
                } else if (id == R.id.item2) {
                    startActivity(new Intent(MainActivity.this, Recipe.class));
                    return true;
                } else if (id == R.id.item3) {
                    startActivity(new Intent(MainActivity.this, Dictionary.class)); // Dictionary API class with item3
                    return true;
                } else if (id == R.id.item4) {
                    startActivity(new Intent(MainActivity.this, Song.class));
                    return true;
                } else {
                    return super.onOptionsItemSelected(item);
                }
            }

        }


    }
}