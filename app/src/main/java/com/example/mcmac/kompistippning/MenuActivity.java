package com.example.mcmac.kompistippning;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.sql.SQLException;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void onClickBet(View button) {

        Intent intent = new Intent(this, BetActivity.class);
        startActivity(intent);
    }

    public void onClickAdd(View button) {

        Intent intent = new Intent(this, AddActivity.class);
        startActivity(intent);
    }

    public void onClickReport(View button) {

        Intent intent = new Intent(this, ReportResultActivity.class);
        startActivity(intent);
    }

    public void onClickCompetitions(View button) {

        Intent intent = new Intent(this, CompetitionActivity.class);
        startActivity(intent);
    }

    public void onClickStatistics(View button) {

        Intent intent = new Intent(this, StatisticsActivity.class);
        startActivity(intent);
    }

}
