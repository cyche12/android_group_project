package algonquin.cst2335.android_group_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import algonquin.cst2335.android_group_project.databinding.ActivityMainBinding;
import algonquin.cst2335.android_group_project.databinding.ActivitySecondBinding;

public class SecondActivity extends AppCompatActivity {

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