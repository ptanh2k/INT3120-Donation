package com.example.donation;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;

import models.Donation;

public class DonateActivity extends Base {
    TextView welcomeText;
    Button donateButton;
    RadioGroup paymentMethod;
    NumberPicker amountPicker;
    ProgressBar progressBar;
    EditText amountText;
    TextView amountTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);
        Intent intent = getIntent();
        String name = intent.getStringExtra("Name");
        welcomeText = (TextView) findViewById(R.id.welcome);
        welcomeText.setTypeface(null, Typeface.BOLD);
        welcomeText.setText(String.format("Welcome, %s", name));

        paymentMethod = (RadioGroup) findViewById(R.id.paymentMethod);
        amountPicker = (NumberPicker) findViewById(R.id.amountPicker);
        amountTotal = (TextView) findViewById(R.id.totalSoFar);

        amountPicker.setMinValue(0);
        amountPicker.setMaxValue(1000);
        amountTotal.setText("$0");

        donateButton = (Button) findViewById(R.id.donateButton);

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
        int donatedAmount = amountPicker.getValue();
        int radioId = paymentMethod.getCheckedRadioButtonId();
        String method = radioId == R.id.PayPal ? "PayPal" : "Direct";
       if (donatedAmount == 0) {
           String text = amountText.getText().toString();
           if (!text.equals("")) {
               donatedAmount = Integer.parseInt(text);
           }
       }
       if (donatedAmount > 0) {
           newDonation(new Donation(donatedAmount, method));
           progressBar.setProgress(totalDonated);
           String totalDonatedStr = "$" + totalDonated;
           amountTotal.setText(totalDonatedStr);
       }

    }
}