package com.example.mcmac.kompistippning;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.sql.SQLException;

public class BetActivity extends AppCompatActivity {

    Spinner competitionSpinner;
    ArrayAdapter competitionAdapter;

    Spinner gameSpinner;
    ArrayAdapter gameAdapter;

    Spinner participantSpinner;
    ArrayAdapter participantAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bet);

        try {
            BettingDB.getInstance().open(true, this);
        }
        catch (SQLException e) {
            Log.e("MyTag", e.getMessage(), e);
        }

        competitionSpinner = (Spinner) findViewById(R.id.competitionSpinner);
        competitionAdapter = new ArrayAdapter<Competition>(this, android.R.layout.simple_spinner_item, android.R.id.text1, BettingDB.getInstance().getAllCompetitions());
        competitionSpinner.setAdapter(competitionAdapter);

        gameSpinner = (Spinner) findViewById(R.id.gameSpinner);
        gameAdapter = new ArrayAdapter<Game>(this, android.R.layout.simple_spinner_item, android.R.id.text1, BettingDB.getInstance().getAllGames());
        gameSpinner.setAdapter(gameAdapter);

        participantSpinner = (Spinner) findViewById(R.id.gameSpinner);
        participantAdapter = new ArrayAdapter<Participant>(this, android.R.layout.simple_spinner_item, android.R.id.text1, BettingDB.getInstance().getAllParticipants());
        gameSpinner.setAdapter(participantAdapter);

    }
}
