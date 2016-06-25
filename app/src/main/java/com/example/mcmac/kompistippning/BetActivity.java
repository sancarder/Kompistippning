package com.example.mcmac.kompistippning;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

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

        MyItemSelectedListener myItemSelectedListener= new MyItemSelectedListener();

        competitionSpinner = (Spinner) findViewById(R.id.competitionSpinner);
        competitionAdapter = new ArrayAdapter<Competition>(this, android.R.layout.simple_spinner_item, android.R.id.text1, BettingDB.getInstance().getAllCompetitions());
        competitionSpinner.setAdapter(competitionAdapter);
        competitionSpinner.setOnItemSelectedListener(myItemSelectedListener);

        gameSpinner = (Spinner) findViewById(R.id.gameSpinner);
        gameAdapter = new ArrayAdapter<Game>(this, android.R.layout.simple_spinner_item, android.R.id.text1, BettingDB.getInstance().getAllGames());
        gameSpinner.setAdapter(gameAdapter);
        gameSpinner.setOnItemSelectedListener(myItemSelectedListener);

        participantSpinner = (Spinner) findViewById(R.id.participantSpinner);
        participantAdapter = new ArrayAdapter<Participant>(this, android.R.layout.simple_spinner_item, android.R.id.text1, BettingDB.getInstance().getAllParticipants());
        participantSpinner.setAdapter(participantAdapter);
        participantSpinner.setOnItemSelectedListener(myItemSelectedListener);



    }

    private class MyItemSelectedListener implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            /*
            Municip municip = (Municip)municipSpinner.getSelectedItem();
            emailShowText.setText(municip.getEmail());
            System.out.println(municip);
            event_choice = (String)eventSpinner.getSelectedItem();
            System.out.println(event_choice);
            grade_choice = (String)gradeSpinner.getSelectedItem();
            System.out.println(grade_choice);
            */
            TextView teamATextView =(TextView)findViewById(R.id.teamATextView);
            Game gameA = (Game)gameSpinner.getSelectedItem();
            teamATextView.setText(gameA.getTeamA());

            TextView teamBTextView =(TextView)findViewById(R.id.teamBTextView);
            Game gameB = (Game)gameSpinner.getSelectedItem();
            teamBTextView.setText(gameB.getTeamB());


        }
        public void onNothingSelected(AdapterView<?> parent) {
        }};
}
