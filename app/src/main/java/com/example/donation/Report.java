package com.example.donation;


import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import adapters.DonationAdapter;

public class Report extends Base {
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        listView = (ListView) findViewById(R.id.reportList);

        // REPORT title becomes bold
        TextView reportTitle = (TextView) findViewById(R.id.reportTitle);
        reportTitle.setTypeface(null, Typeface.BOLD);

        //The Adapter acts as a bridge between the UI Component and the Data Source. It converts data from the data sources into view items that can be displayed into the UI Component.
        //Data Source can be Arrays, HashMap, Database, etc. and UI Components can be ListView, GridView, Spinner, etc
        DonationAdapter adapter = new DonationAdapter(this, donations);
        listView.setAdapter(adapter);
    }
}