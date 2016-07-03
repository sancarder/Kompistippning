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
import android.widget.Toast;

import java.sql.SQLException;
import java.util.ArrayList;

public class AddActivity extends AppCompatActivity {

    TextView competitionField;
    String newCompetition;

    TextView participantField;
    String newParticipant;

    TextView teamAField;
    String newTeamA;
    TextView teamBField;
    String newTeamB;
    TextView dateField;
    String newDate;
    TextView gameTypeField;
    String newGameType;
    TextView sumField;
    String newSum;

    Spinner competitionRemoveSpinner;
    Spinner gameCompetitionSpinner;
    Spinner gameRemoveSpinner;
    Spinner participantCompetitionSpinner;
    Spinner participantRemoveSpinner;

    ArrayAdapter competitionAdapter;
    ArrayAdapter gameAdapter;
    ArrayAdapter participantAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        setupActivity();

    }

    public void setupActivity() {

        try {
            BettingDB.getInstance().open(true, this);
        }
        catch (SQLException e) {
            Log.e("MyTag", e.getMessage(), e);
        }

        competitionRemoveSpinner = (Spinner)findViewById(R.id.competitionRemoveSpinner);
        gameCompetitionSpinner = (Spinner) findViewById(R.id.gameCompetitionsSpinner);
        gameRemoveSpinner = (Spinner) findViewById(R.id.gameRemoveSpinner);
        participantCompetitionSpinner = (Spinner) findViewById(R.id.participantCompetitionSpinner);
        participantRemoveSpinner = (Spinner) findViewById(R.id.participantRemoveSpinner);

        competitionAdapter = new ArrayAdapter<Competition>(this, android.R.layout.simple_spinner_item, android.R.id.text1, BettingDB.getInstance().getAllCompetitions());
        gameCompetitionSpinner.setAdapter(competitionAdapter);
        Competition gameEvent = (Competition)gameCompetitionSpinner.getSelectedItem();
        gameAdapter = new ArrayAdapter<Game>(this, android.R.layout.simple_spinner_item, android.R.id.text1, BettingDB.getInstance().getEventGames(gameEvent.getEventName()));

        participantCompetitionSpinner.setAdapter(competitionAdapter);
        Competition participantEvent = (Competition)participantCompetitionSpinner.getSelectedItem();
        participantAdapter = new ArrayAdapter<Participant>(this, android.R.layout.simple_spinner_item, android.R.id.text1, BettingDB.getInstance().getEventParticipants(participantEvent.getEventName()));

        competitionRemoveSpinner.setAdapter(competitionAdapter);

        gameRemoveSpinner.setAdapter(gameAdapter);
        participantRemoveSpinner.setAdapter(participantAdapter);

        MyItemSelectedListener myItemSelectedListener= new MyItemSelectedListener();

        gameCompetitionSpinner.setOnItemSelectedListener(myItemSelectedListener);
        participantCompetitionSpinner.setOnItemSelectedListener(myItemSelectedListener);

    }

        public void addCompetition(View view) {

        competitionField =(TextView)findViewById(R.id.competitionField);
        newCompetition = competitionField.getText().toString();

        Competition competitionDBreturns = BettingDB.getInstance().insertCompetition(newCompetition);

        if (competitionDBreturns.getRowId()!=-1) {
            competitionField.setText("");
            Toast.makeText(this, newCompetition + " inlagd i databasen", Toast.LENGTH_SHORT);
            competitionAdapter.clear();
            competitionAdapter.addAll(BettingDB.getInstance().getAllCompetitions());
            competitionAdapter.notifyDataSetChanged();
        }
        else
            Toast.makeText(this, "Det gick inte att lägga till tävlingen i databasen. Den kanske finns?", Toast.LENGTH_SHORT);
    }

    public void removeCompetition(View view) {
        Competition removeCompetition = (Competition) competitionRemoveSpinner.getSelectedItem();
        BettingDB.getInstance().deleteCompetition(removeCompetition);

        competitionAdapter.clear();
        competitionAdapter.addAll(BettingDB.getInstance().getAllCompetitions());
        competitionAdapter.notifyDataSetChanged();
    }

    public void addGame(View view) {

        //competitionsSpinner1 = (Spinner) findViewById(R.id.gameCompetitionsSpinner);
        Competition betEvent = (Competition) gameCompetitionSpinner.getSelectedItem();

        teamAField =(TextView)findViewById(R.id.teamAField);
        newTeamA = teamAField.getText().toString();

        teamBField =(TextView)findViewById(R.id.teamBField);
        newTeamB = teamBField.getText().toString();

        dateField =(TextView)findViewById(R.id.dateField);
        newDate = dateField.getText().toString();

        gameTypeField =(TextView)findViewById(R.id.gameTypeField);
        newGameType = gameTypeField.getText().toString();

        sumField =(TextView)findViewById(R.id.sumField);
        newSum = sumField.getText().toString();

        Game gameDBreturn = BettingDB.getInstance().insertGame(betEvent.getEventName(), newDate, newTeamA, newTeamB, newSum, newGameType, "-");

        if (gameDBreturn.getRowId()!=-1) {

            teamAField.setText("");
            teamBField.setText("");
            dateField.setText("");
            gameTypeField.setText("");
            sumField.setText("");
            Toast.makeText(this, newTeamA + "-" + newTeamB + " inlagd i databasen", Toast.LENGTH_SHORT);
            gameAdapter.clear();
            gameAdapter.addAll(BettingDB.getInstance().getEventGames(betEvent.getEventName()));
            gameAdapter.notifyDataSetChanged();
        }
        else
            Toast.makeText(this, "Det gick inte att lägga till matchen i databasen. Den kanske finns?", Toast.LENGTH_SHORT);
    }

    public void deleteGame(View view) {
        Game removeGame = (Game) gameRemoveSpinner.getSelectedItem();
        BettingDB.getInstance().deleteGame(removeGame);

        gameAdapter.clear();
        gameAdapter.addAll(BettingDB.getInstance().getEventGames(removeGame.getEventName()));
        gameAdapter.notifyDataSetChanged();
    }

    public void addParticipant(View view) {

        //competitionsSpinner2 = (Spinner) findViewById(R.id.participantCompetitionSpinner);
        Competition betEvent = (Competition) participantCompetitionSpinner.getSelectedItem();

        participantField =(TextView)findViewById(R.id.participantField);
        newParticipant = participantField.getText().toString();

        Participant participantDBreturn = BettingDB.getInstance().insertParticipant(betEvent.getEventName(), newParticipant);


        if (participantDBreturn.getRowId()!=-1) {
            participantField.setText("");
            Toast.makeText(this, newParticipant + " inlagd i databasen", Toast.LENGTH_SHORT);
            participantAdapter.clear();
            participantAdapter.addAll(BettingDB.getInstance().getEventParticipants(betEvent.getEventName()));
            participantAdapter.notifyDataSetChanged();
        }
        else
            Toast.makeText(this, "Det gick inte att lägga till deltagaren i databasen. Den kanske finns?", Toast.LENGTH_SHORT);
    }

    public void deleteParticipant(View view) {
        Participant removeParticipant = (Participant) participantRemoveSpinner.getSelectedItem();
        BettingDB.getInstance().deleteParticipant(removeParticipant);

        participantAdapter.clear();
        participantAdapter.addAll(BettingDB.getInstance().getEventParticipants(removeParticipant.getEventName()));
        participantAdapter.notifyDataSetChanged();
    }


    private class MyItemSelectedListener implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {


            if (parent.getId()==R.id.gameCompetitionsSpinner){
                Competition tempCompetition = (Competition) gameCompetitionSpinner.getSelectedItem();
                gameAdapter.clear();
                gameAdapter.addAll(BettingDB.getInstance().getEventGames(tempCompetition.getEventName()));
                gameAdapter.notifyDataSetChanged();
                System.out.println("game");
            }
            else if (parent.getId()==R.id.participantCompetitionSpinner){
                Competition tempCompetition = (Competition) participantCompetitionSpinner.getSelectedItem();
                participantAdapter.clear();
                participantAdapter.addAll(BettingDB.getInstance().getEventParticipants(tempCompetition.getEventName()));
                participantAdapter.notifyDataSetChanged();
                System.out.println("participant");
            }
            System.out.println(R.id.gameCompetitionsSpinner + "  " + view.getId());

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
