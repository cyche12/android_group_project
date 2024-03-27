package algonquin.cst2335.android_group_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import algonquin.cst2335.android_group_project.databinding.ActivityMainBinding;
import algonquin.cst2335.android_group_project.databinding.ActivitySecondBinding;

public class ThirdActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySecondBinding binding = ActivitySecondBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        String websiteURL = getIntent().getStringExtra("websiteURL");
        Button backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener( click -> {
            finish();
        });

    }
}

//Student: Jake Elliott//
//Student #040732505//
//Class: CST2335//
//Group Members: Jake Elliott, Gabriel Hubert, Shilpi Sarkar, Piyalee Mangaraj//
//Project: Final Group Project//
//App: Sunrise/Sunset App//
