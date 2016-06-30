package com.example.mcmac.kompistippning;

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
    }



    private class MyItemSelectedListener implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            ArrayList<Bet> gameBets;
            int participantWin=0;
            String gameResult="";
            String betAmount="";

            Competition tempCompetition = (Competition) competitionSpinner.getSelectedItem();
            competitionGames=BettingDB.getInstance().getEventGames(tempCompetition.getEventName());
            competitionParticipants=BettingDB.getInstance().getEventParticipants(tempCompetition.getEventName());
            ArrayList<PersonScore> personScore=null;
            for (Participant part: competitionParticipants) {
                personScore.add(new PersonScore(part.getPerson(), 0, 0, 0, 0, 0));
            }

            for (Game game: competitionGames){
                betAmount = game.getBetAmount();
                gameResult = game.getEventResult();
                for (Participant part2: competitionParticipants) {
                    if( BettingDB.getInstance().getGameBet(game.getEventName(),game.rowId, part2.getPerson()).get(0).getBet()==gameResult) {
                        ArrayList<Bet> correctBets = BettingDB.getInstance().getGameBets(game.rowId, gameResult);
                        if (correctBets.size() > 1){
                            personScore.get(competitionParticipants.indexOf(part2)).addWinPoints((Integer.getInteger(betAmount)));
                            personScore.get(competitionParticipants.indexOf(part2)).addWins(1);
                        }
                        else{
                            personScore.get(competitionParticipants.indexOf(part2)).addSharedPoints(Integer.getInteger(betAmount)/correctBets.size());
                            personScore.get(competitionParticipants.indexOf(part2)).addSharedWins(1);
                        }
                    }
                }
            }


            //gameBets=BettingDB.getInstance().getGameBet(tempCompetition.getEventName(),competiti
            //David,  Antal ensam vinnare, Poäng ensam vinnare, Antal delad vinnare, poäng delad vinnare, Total poäng
        }
        public void onNothingSelected(AdapterView<?> parent) {
        }};
}
