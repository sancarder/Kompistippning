package com.example.mcmac.kompistippning;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;

import java.sql.SQLException;
import java.util.ArrayList;

public class BetActivity extends AppCompatActivity {

    Spinner competitionSpinner;
    ArrayAdapter competitionAdapter;

    Spinner gameSpinner;
    ArrayAdapter gameAdapter;

    Spinner participantSpinner;
    ArrayAdapter participantAdapter;

    ArrayList<Game> gamesList;
    ArrayList<Participant> participantsList;
    ArrayList<Bet> betsList;

    NumberPicker teamAnp;
    NumberPicker teamBnp;

    String currentEvent = null;
    String currentGame = null;
    String currentParticipant = null;

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

        Competition current_event = (Competition) competitionSpinner.getSelectedItem();
        currentEvent = current_event.getEventName();

        gamesList = BettingDB.getInstance().getEventGames(currentEvent);
        participantsList = BettingDB.getInstance().getEventParticipants(currentEvent);

        betsList = BettingDB.getInstance().getAllBets();

        gameSpinner = (Spinner) findViewById(R.id.gameSpinner);
        gameAdapter = new ArrayAdapter<Game>(this, android.R.layout.simple_spinner_item, android.R.id.text1, gamesList);
        gameSpinner.setAdapter(gameAdapter);
        gameSpinner.setOnItemSelectedListener(myItemSelectedListener);

        participantSpinner = (Spinner) findViewById(R.id.competitionsSpinner2);
//        participantAdapter = new ArrayAdapter<Participant>(this, android.R.layout.simple_spinner_item, android.R.id.text1, participantsList);

        participantAdapter = new ArrayAdapter<Bet>(this, android.R.layout.simple_spinner_item, android.R.id.text1, betsList);

        participantSpinner.setAdapter(participantAdapter);
        participantSpinner.setOnItemSelectedListener(myItemSelectedListener);

        teamAnp = (NumberPicker)findViewById(R.id.teamAGoalPicker);
        teamBnp = (NumberPicker)findViewById(R.id.teamBGoalPicker);

        teamAnp.setMinValue(0);
        teamAnp.setMaxValue(9);
        teamAnp.setWrapSelectorWheel(true);

        teamBnp.setMinValue(0);
        teamBnp.setMaxValue(9);
        teamBnp.setWrapSelectorWheel(true);

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

            Competition tempCompetition = (Competition) competitionSpinner.getSelectedItem();

            if (tempCompetition.getEventName() != currentEvent) {
                currentEvent = tempCompetition.getEventName();
                gamesList.clear();
                gamesList.addAll(BettingDB.getInstance().getEventGames(currentEvent));
                gameAdapter.notifyDataSetChanged();
                gameSpinner.postInvalidate();

                participantsList.clear();
                participantsList.addAll(BettingDB.getInstance().getEventParticipants(currentEvent));
                participantAdapter.notifyDataSetChanged();
                participantSpinner.postInvalidate();
            }

            TextView teamATextView = (TextView) findViewById(R.id.teamATextView);
            Game gameA = (Game) gameSpinner.getSelectedItem();
            TextView teamBTextView = (TextView) findViewById(R.id.teamBTextView);
            Game gameB = (Game) gameSpinner.getSelectedItem();

            if (gamesList.size() > 0) {
                teamATextView.setText(gameA.getTeamA());
                teamBTextView.setText(gameB.getTeamB());
            } else {
                teamATextView.setText("Lag 1");
                teamBTextView.setText("Lag 2");
            }

//            TextView currentBetInfo = (TextView) findViewById(R.id.currentBetTextView);
//            Participant currentParticipant = (Participant) participantSpinner.getSelectedItem();

//            if (participantsList.size() > 0 && gamesList.size() > 0) {

//                Game currentGame = ((Game) gameSpinner.getSelectedItem());
//                ArrayList<Bet> participantBet = BettingDB.getInstance().getGameBet(currentEvent, currentGame.getRowId(), currentParticipant.getPerson());
//                ArrayList<Bet> participantBet = BettingDB.getInstance().getAllBets();

/*

                if (participantBet.size() > 0) {
                    currentBetInfo.setText(currentParticipant.getPerson() + " har tippat " + participantBet.get(0).getBet());
                }
                else {
                    currentBetInfo.setText(currentParticipant.getPerson() + " har ännu inte tippat");
                }
            }
            else {
                currentBetInfo.setText("Tävlingen saknar deltagare");
            }
*/

//            }
        }
        public void onNothingSelected(AdapterView<?> parent) {
        }};

    public void onClick(View view) {

        Competition betEvent = (Competition) competitionSpinner.getSelectedItem();
        Game betGame = (Game) gameSpinner.getSelectedItem();
        Participant betParticipant = (Participant) participantSpinner.getSelectedItem();

        int betTeamA = teamAnp.getValue();
        int betTeamB = teamBnp.getValue();

        BettingDB.getInstance().insertBet(betEvent.getEventName(), betParticipant.getPerson(), String.valueOf(betGame.getRowId()), betTeamA+"-"+betTeamB);

    }
}
