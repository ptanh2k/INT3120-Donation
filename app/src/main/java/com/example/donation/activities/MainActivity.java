package com.example.donation.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.donation.R;
import com.example.donation.activities.DonateActivity;

public class MainActivity extends AppCompatActivity {
    Button btn;
    EditText nameInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = (Button) findViewById(R.id.login_btn);
        nameInput = (EditText) findViewById(R.id.editTextTextPersonName);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDonateActivity();
            }
        });
    }

    protected void openDonateActivity() {
        Intent intent = new Intent(this, DonateActivity.class);
        intent.putExtra("Name", nameInput.getText().toString());
        startActivity(intent);
    }
}