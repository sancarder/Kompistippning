package com.example.mcmac.kompistippning;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;

public class AddActivity extends AppCompatActivity {

    TextView competitionField;
    String newCompetition;

    Spinner competitionsSpinner1;
    Spinner competitionsSpinner2;

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

    Spinner competitionSpinner1;
    Spinner competitionSpinner2;
    ArrayAdapter competitionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        try {
            BettingDB.getInstance().open(true, this);
        }
        catch (SQLException e) {
            Log.e("MyTag", e.getMessage(), e);
        }

        competitionsSpinner1 = (Spinner) findViewById(R.id.competitionsSpinner);

        competitionSpinner2 = (Spinner) findViewById(R.id.competitionsSpinner2);
        competitionAdapter = new ArrayAdapter<Competition>(this, android.R.layout.simple_spinner_item, android.R.id.text1, BettingDB.getInstance().getAllCompetitions());
        competitionSpinner2.setAdapter(competitionAdapter);
        competitionsSpinner1.setAdapter(competitionAdapter);
    }

    public void addCompetition(View view) {

        competitionField =(TextView)findViewById(R.id.competitionField);
        newCompetition = competitionField.getText().toString();

        Competition competitionDBreturns = BettingDB.getInstance().insertCompetition(newCompetition);

        if (competitionDBreturns.getRowId()!=-1) {
            competitionField.setText("");
            Toast.makeText(this, newCompetition + " inlagd i databasen", Toast.LENGTH_SHORT);
        }
        else
            Toast.makeText(this, "Det gick inte att lägga till tävlingen i databasen. Den kanske finns?", Toast.LENGTH_SHORT);
    }

    public void addGame(View view) {

        competitionsSpinner1 = (Spinner) findViewById(R.id.competitionsSpinner);
        Competition betEvent = (Competition) competitionsSpinner1.getSelectedItem();

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
        }
        else
            Toast.makeText(this, "Det gick inte att lägga till matchen i databasen. Den kanske finns?", Toast.LENGTH_SHORT);
    }

    public void addParticipant(View view) {

        competitionsSpinner2 = (Spinner) findViewById(R.id.competitionsSpinner2);
        Competition betEvent = (Competition) competitionsSpinner2.getSelectedItem();

        participantField =(TextView)findViewById(R.id.participantField);
        newParticipant = participantField.getText().toString();

        Participant participantDBreturn = BettingDB.getInstance().insertParticipant(betEvent.getEventName(), newParticipant);


        if (participantDBreturn.getRowId()!=-1) {
            participantField.setText("");
            Toast.makeText(this, newParticipant + " inlagd i databasen", Toast.LENGTH_SHORT);
        }
        else
            Toast.makeText(this, "Det gick inte att lägga till deltagaren i databasen. Den kanske finns?", Toast.LENGTH_SHORT);
    }

}
