package com.example.donation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class DonateActivity extends AppCompatActivity {
    TextView textView;
    Button donateButton;
    RadioGroup paymentMethod;
    NumberPicker amountPicker;
    ProgressBar progressBar;
    EditText amountText;

    private int totalDonated = 0;
    private boolean targetAchieved = false;

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_donate, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuReport:
                Toast toast = Toast.makeText(this, "Report Selected", Toast.LENGTH_SHORT);
                toast.show();
                break;
        }

        return super.onOptionsItemSelected(item);
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