package com.example.mcmac.kompistippning;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

    NumberPicker teamAnp;
    NumberPicker teamBnp;

    Button actionButton;

    String currentEvent = null;
    String currentGame = null;
    String currentParticipant = null;

    TextView currentBetInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bet);

        actionButton = (Button) findViewById(R.id.tipButton);

        try {
            BettingDB.getInstance().open(true, this);
        }
        catch (SQLException e) {
            Log.e("MyTag", e.getMessage(), e);
        }

        currentBetInfo = (TextView) findViewById(R.id.currentBetTextView);

        MyItemSelectedListener myItemSelectedListener= new MyItemSelectedListener();

        competitionSpinner = (Spinner) findViewById(R.id.competitionSpinner);
        competitionAdapter = new ArrayAdapter<Competition>(this, android.R.layout.simple_spinner_item, android.R.id.text1, BettingDB.getInstance().getAllCompetitions());
        competitionSpinner.setAdapter(competitionAdapter);
        competitionSpinner.setOnItemSelectedListener(myItemSelectedListener);

        Competition current_event = (Competition) competitionSpinner.getSelectedItem();
        currentEvent = current_event.getEventName();

        gamesList = BettingDB.getInstance().getEventGames(currentEvent);
        participantsList = BettingDB.getInstance().getEventParticipants(currentEvent);

        gameSpinner = (Spinner) findViewById(R.id.gameSpinner);
        gameAdapter = new ArrayAdapter<Game>(this, android.R.layout.simple_spinner_item, android.R.id.text1, gamesList);
        gameSpinner.setAdapter(gameAdapter);
        gameSpinner.setOnItemSelectedListener(myItemSelectedListener);

        participantSpinner = (Spinner) findViewById(R.id.competitionsSpinner2);
        participantAdapter = new ArrayAdapter<Participant>(this, android.R.layout.simple_spinner_item, android.R.id.text1, participantsList);

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

            Participant currentParticipant = (Participant) participantSpinner.getSelectedItem();

            if (participantsList.size() > 0 && gamesList.size() > 0) {

                Game currentGame = ((Game) gameSpinner.getSelectedItem());
                ArrayList<Bet> participantBet = BettingDB.getInstance().getGameBet(currentEvent, currentGame.getRowId(), currentParticipant.getPerson());
//                ArrayList<Bet> participantBet = BettingDB.getInstance().getAllBets();



                if (participantBet.size() > 0) {
                    currentBetInfo.setText(currentParticipant.getPerson() + " har tippat " + participantBet.get(0).getBet());
                    actionButton.setText("Uppdatera");
                }
                else {
                    currentBetInfo.setText(currentParticipant.getPerson() + " har ännu inte tippat");
                    actionButton.setText("Tippa");
                }
            }
            else {
                currentBetInfo.setText("Tävlingen saknar deltagare");
            }

        }
        public void onNothingSelected(AdapterView<?> parent) {
        }};

    public void onClick(View view) {

        Competition betEvent = (Competition) competitionSpinner.getSelectedItem();
        Game betGame = (Game) gameSpinner.getSelectedItem();
        Participant betParticipant = (Participant) participantSpinner.getSelectedItem();

        int betTeamA = teamAnp.getValue();
        int betTeamB = teamBnp.getValue();

        //BettingDB.getInstance().insertBet(betEvent.getEventName(), betParticipant.getPerson(), String.valueOf(betGame.getRowId()), String.valueOf(betTeamA+"-"+betTeamB));

        if (actionButton.getText() == "Tippa")
            BettingDB.getInstance().insertBet(betEvent.getEventName(), betParticipant.getPerson(), String.valueOf(betGame.getRowId()), String.valueOf(betTeamA+"-"+betTeamB));
        else if (actionButton.getText() == "Uppdatera") {
            Bet currentBet = BettingDB.getInstance().getGameBet(betEvent.getEventName(), betGame.getRowId(), betParticipant.getPerson()).get(0);
            currentBet.setBet(String.valueOf(betTeamA+"-"+betTeamB));
            currentBetInfo.setText(currentBet.getPerson() + " har tippat " + currentBet.getBet());
            System.out.println(currentBet.getBet());
            System.out.println(BettingDB.getInstance().updateBet(currentBet));
        }
    }
}
