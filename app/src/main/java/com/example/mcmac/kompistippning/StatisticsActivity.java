package com.example.mcmac.kompistippning;

import android.graphics.Typeface;
import android.provider.Telephony;
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

public class StatisticsActivity extends AppCompatActivity {

    Spinner competitionSpinner;
    ArrayAdapter competitionAdapter;
    TextView statTextView;

    ArrayList<Game> competitionGames;
    ArrayList<Participant> competitionParticipants;
    ArrayList<Bet> competitionBets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        setupActivity();

    }

    public void setupActivity() {

        try {
            BettingDB.getInstance().open(true, this);
        }
        catch (SQLException e) {
            Log.e("MyTag", e.getMessage(), e);
        }

        MyItemSelectedListener myItemSelectedListener= new MyItemSelectedListener();

        competitionSpinner = (Spinner) findViewById(R.id.competitionStatSpinner);
        competitionAdapter = new ArrayAdapter<Competition>(this, android.R.layout.simple_spinner_item, android.R.id.text1, BettingDB.getInstance().getAllCompetitions());
        competitionSpinner.setAdapter(competitionAdapter);
        competitionSpinner.setOnItemSelectedListener(myItemSelectedListener);

        statTextView=(TextView)findViewById(R.id.statTextView);
        statTextView.setTypeface(Typeface.MONOSPACE);

    }


        private class MyItemSelectedListener implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            ArrayList<Bet> gameBets;
            int participantWin = 0;
            String gameResult = "";
            int betAmount = 0;
            int correctBetCount = 0;
            String tableString;

            Competition tempCompetition = (Competition) competitionSpinner.getSelectedItem();
            competitionGames = BettingDB.getInstance().getEventGames(tempCompetition.getEventName());
            competitionParticipants = BettingDB.getInstance().getEventParticipants(tempCompetition.getEventName());

            ArrayList<PersonScore> personScore = new ArrayList<PersonScore>();

            for (Participant part : competitionParticipants) {
                personScore.add(new PersonScore(part.getPerson(), 0, 0, 0, 0, 0));
            }


            for (Game game : competitionGames) {

                System.out.println(game.getTeamA() + "-" + game.teamB + ": " + game.getEventResult());

                betAmount = Integer.parseInt(game.getBetAmount());
                gameResult = game.getEventResult();

                ArrayList<Bet> allBets = BettingDB.getInstance().getAllGameBets(game.getRowId());
                correctBetCount = 0;

                for (Bet bet : allBets) {
                    if (bet.getBet().equals(gameResult))
                        correctBetCount +=1;
                }

                for (Participant part2 : competitionParticipants) {

                    System.out.println(part2.getPerson());

                    ArrayList<Bet> partBets = BettingDB.getInstance().getGameBet(game.getEventName(), game.rowId, part2.getPerson());

                    for (Bet tempBet : partBets) {
                        System.out.println(tempBet.getBet());
                    }

                    if (partBets.size() != 0 && partBets.get(0).getBet().equals(gameResult)) {
                        System.out.println(part2.getPerson() + " guessed right in this game");

                        System.out.println("Number of correct bets: " + correctBetCount);

                        if (correctBetCount == 1) {
                            System.out.println("Only 1 correct bet");
                            personScore.get(competitionParticipants.indexOf(part2)).addWinPoints((betAmount) * competitionParticipants.size());
                            personScore.get(competitionParticipants.indexOf(part2)).addWins(1);
                        }
                        else {
                            System.out.println("Several correct bets");
                            personScore.get(competitionParticipants.indexOf(part2)).addSharedPoints( (double) (betAmount) * (double) (competitionParticipants.size()) / (double) (correctBetCount));
                            personScore.get(competitionParticipants.indexOf(part2)).addSharedWins(1);
                        }
                    }
                    else {
                        System.out.println("Participant was wrong");
                        personScore.get(competitionParticipants.indexOf(part2)).addLoss(1);
                    }
                }

            }
            if(competitionGames.size()>0) {
                tableString = String.format("%-10s %-3s %-3s %-3s %s", "Namn", "UV", "DV", "F", "TP");

                for (PersonScore pScore : personScore) {
                    tableString += String.format("\n%-10s %-3d %-3d %-3d %.2f", pScore.getName(), pScore.getWins(), pScore.getSharedWins(), pScore.getLosses(), pScore.getSharedPoints() + pScore.getWinPoints());
                }
            }
            else
                tableString="Turneringen har inga matcher";

            statTextView.setText(tableString);
        }
        public void onNothingSelected(AdapterView<?> parent) {
        }};

    @Override
    protected void onPause(){
        super.onPause();
        BettingDB.getInstance().close();
    }

    @Override
    protected void onResume(){
        super.onResume();

        setupActivity();
    }

}
