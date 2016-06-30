package com.example.mcmac.kompistippning;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.sql.SQLException;
import java.util.ArrayList;

public class CompetitionActivity extends AppCompatActivity {

    Spinner eventSpinner;
    TextView games;
    TextView results;
    ArrayAdapter eventAdapter;
    String chosenEvent = null;
    ArrayList<Game> gamesList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_competition);

        try {
            BettingDB.getInstance().open(true, this);
        }
        catch (SQLException e) {
            Log.e("MyTag", e.getMessage(), e);
        }

        games = (TextView) findViewById(R.id.gamesTextView);
        games.setTypeface(Typeface.MONOSPACE);
//        results = (TextView) findViewById(R.id.resultsTextView);

//        results.setText('\t');

        MyItemSelectedListener myItemSelectedListener= new MyItemSelectedListener();

        eventSpinner = (Spinner) findViewById(R.id.eventSpinner);
        eventAdapter = new ArrayAdapter<Competition>(this, android.R.layout.simple_spinner_item, android.R.id.text1, BettingDB.getInstance().getAllCompetitions());
        eventSpinner.setAdapter(eventAdapter);
        eventSpinner.setOnItemSelectedListener(myItemSelectedListener);

        Competition chosen_event = (Competition) eventSpinner.getSelectedItem();
        chosenEvent = chosen_event.getEventName();

        gamesList = BettingDB.getInstance().getEventGames(chosenEvent);


    }

    private class MyItemSelectedListener implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

            Competition tempEvent = (Competition) eventSpinner.getSelectedItem();

            if (tempEvent.getEventName() != chosenEvent) {
                chosenEvent = tempEvent.getEventName();
                gamesList.clear();
                gamesList.addAll(BettingDB.getInstance().getEventGames(chosenEvent));
            }

            if (gamesList.size() > 0) {

                for (int i=0;i<gamesList.size();i++) {
                    String gamesContent = String.valueOf(games.getText());
//                    games.setText(gamesContent +  '\n' +  String.format("%-50s %s", gamesList.get(i), gamesList.get(i).getEventResult()));

//                    int columnA = 40+gamesList.get(i).getTeamA().length();

                    games.setText(gamesContent + String.format("%-15s%-15s%s\n", gamesList.get(i).getTeamA(), gamesList.get(i).getTeamB(), gamesList.get(i).getEventResult()));
                }
            }
        }
        public void onNothingSelected(AdapterView<?> parent) {
        }};



}
