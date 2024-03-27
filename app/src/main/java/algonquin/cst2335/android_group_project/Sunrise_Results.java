package algonquin.cst2335.android_group_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import algonquin.cst2335.android_group_project.databinding.SunriseResultBinding;

public class Sunrise_Results extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SunriseResultBinding binding = SunriseResultBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent homeIntent = new Intent(Sunrise_Results.this, MainActivity.class);
        String websiteURL = getIntent().getStringExtra("websiteURL");
        Button backButton = binding.backButton;
        backButton.setOnClickListener( click -> startActivity(homeIntent));
    }
}
