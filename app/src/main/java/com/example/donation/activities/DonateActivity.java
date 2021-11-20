package com.example.donation.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.donation.R;
import com.example.donation.api.DonationApi;
import com.example.donation.models.Donation;

import java.util.List;

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
        amountText = (EditText) findViewById(R.id.paymentAmount);

        amountPicker.setMinValue(0);
        amountPicker.setMaxValue(1000);
        amountTotal.setText("$0");

        donateButton = (Button) findViewById(R.id.donateButton);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setMax(10000);

        progressBar.setProgress(app.totalDonated);
        amountTotal.setText("$" +  app.totalDonated);

        donateButton.setOnClickListener(view -> donateButtonPressed());
    }

    public void donateButtonPressed() {
        int donatedAmount = amountPicker.getValue();
        String method = paymentMethod.getCheckedRadioButtonId() == R.id.PayPal ? "PayPal" : "Direct";
       if (donatedAmount == 0) {
           String text = amountText.getText().toString();
           if (!text.equals("")) {
               donatedAmount = Integer.parseInt(text);
           }
       }
       if (donatedAmount > 0) {
           Donation new_donation = new Donation(donatedAmount, method, 0);
           app.newDonation(new_donation);
           new InsetTask(this).execute("postdonation", new_donation);
           progressBar.setProgress(app.totalDonated);
           String totalDonatedStr = "$" + app.totalDonated;
           amountTotal.setText(totalDonatedStr);
       }
    }

    @Override
    public void reset(MenuItem item) {
        app.donations.clear();
        app.totalDonated = 0;
        String totalDonatedStr = "$" + app.totalDonated;
        amountTotal.setText(totalDonatedStr);
    }

    private class GetAllTask extends AsyncTask<String, Void, List<Donation>> {
        protected ProgressDialog dialog;
        protected Context context;

        public GetAllTask(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            this.dialog = new ProgressDialog(context, 1);
            this.dialog.setMessage("Retrieving Donation List");
            this.dialog.show();
        }

        @Override
        protected List<Donation> doInBackground(String... params) {
            try {
                Log.v("donate", "Donation App Getting All Donations");
                return (List<Donation>) DonationApi.getAll((String) params[0]);
            } catch (Exception e) {
                Log.v("donate", "ERROR: " + e);
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<Donation> result) {
            super.onPostExecute(result);

            // use result to calculate the totalDonated amount here
            app.totalDonated = 0;
            app.donations = result;

            for (int i = 0; i < result.size(); i++) {
                app.totalDonated = app.totalDonated + result.get(i).amount;
            }

            progressBar.setProgress(app.totalDonated);
            amountTotal.setText("$" + app.totalDonated);

            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        new GetAllTask(this).execute("getalldonation");
    }

    private class InsetTask extends AsyncTask<Object, Void, String> {
        protected ProgressDialog dialog;
        protected Context context;

        public InsetTask(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            this.dialog = new ProgressDialog(context, 1);
            this.dialog.setMessage("Saving Donation.....");
            this.dialog.show();
        }

        @Override
        protected String doInBackground(Object... params) {
            String res = null;
            try {
                Log.v("donate", "Donation App Inserting");
                return (String) DonationApi.insert((String) params[0], (Donation) params[1]);
            } catch (Exception e) {
                Log.v("donate", "ERROR: " + e);
                e.printStackTrace();
            }
            return res;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
        }
    }

    private class ResetTask extends AsyncTask<Object,  Void, String> {
        protected ProgressDialog dialog;
        protected Context context;

        public ResetTask(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(Object... params) {
            String res = null;
            try {
                res = DonationApi.deleteAll((String) params[0]);
            } catch (Exception e) {
                Log.v("donate", "RESET ERROR: " + e);
                e.printStackTrace();
            }
            return res;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            app.totalDonated = 0;
            progressBar.setProgress(app.totalDonated);
            amountTotal.setText("$" + app.totalDonated);

            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }
    }
}