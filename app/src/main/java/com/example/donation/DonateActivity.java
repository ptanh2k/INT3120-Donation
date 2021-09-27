package com.example.donation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DonateActivity extends AppCompatActivity {
    TextView textView;
    Button donateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);
        Intent intent = getIntent();
        String name = intent.getStringExtra("Name");
        textView = (TextView) findViewById(R.id.welcome);
        textView.setText(String.format("Welcome, %s", name));

        donateButton = (Button) findViewById(R.id.donateButton);
        if (donateButton != null) {
            Log.v("Donate", "Really got the donate button");
        }

        donateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                donateButtonPressed();
            }
        });
    }

    public void donateButtonPressed() {
        Log.v("Donate", "Donate Button Pressed");
    }
}