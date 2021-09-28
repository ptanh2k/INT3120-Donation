package com.example.donation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;

public class DonateActivity extends AppCompatActivity {
    TextView textView;
    Button donateButton;
    RadioGroup paymentMethod;
    NumberPicker amountPicker;
    ProgressBar progressBar;

    private int totalDonated = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);
        Intent intent = getIntent();
        String name = intent.getStringExtra("Name");
        textView = (TextView) findViewById(R.id.welcome);
        textView.setText(String.format("Welcome, %s", name));

        paymentMethod = (RadioGroup) findViewById(R.id.paymentMethod);
        amountPicker = (NumberPicker) findViewById(R.id.amountPicker);

        amountPicker.setMinValue(0);
        amountPicker.setMaxValue(1000);

        donateButton = (Button) findViewById(R.id.donateButton);
        if (donateButton != null) {
            Log.v("Donate", "Really got the donate button");
        }

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setMax(10000);

        donateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                donateButtonPressed();
            }
        });
    }

    public void donateButtonPressed() {
        int amount = amountPicker.getValue();
        int radioId = paymentMethod.getCheckedRadioButtonId();
        String method = radioId == R.id.PayPal ? "PayPal" : "Direct";
        totalDonated = totalDonated + amount;
        progressBar.setProgress(totalDonated);
        Log.v("Donate", "Donate Button Pressed with amount " + amount);
        Log.v("Donate", "Using method: " + method);
        Log.v("Donate", "Current total: " + totalDonated);
    }
}