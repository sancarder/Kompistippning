package com.example.mcmac.kompistippning;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;

public class ReportResultActivity extends AppCompatActivity {

    Spinner reportCompetitionSpinner;
    Spinner reportGameSpinner;

    ArrayAdapter competitionAdapter;
    ArrayAdapter gameAdapter;

    NumberPicker teamAnp;
    NumberPicker teamBnp;

    Button reportButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_result);

        setupActivity();

    }

    public void setupActivity() {

        try {
            BettingDB.getInstance().open(true, this);
        }
        catch (SQLException e) {
            Log.e("MyTag", e.getMessage(), e);
        }

        reportCompetitionSpinner = (Spinner)findViewById(R.id.reportCompetitionsSpinner);
        reportGameSpinner = (Spinner) findViewById(R.id.reportGameSpinner);

        competitionAdapter = new ArrayAdapter<Competition>(this, android.R.layout.simple_spinner_item, android.R.id.text1, BettingDB.getInstance().getAllCompetitions());
        reportCompetitionSpinner.setAdapter(competitionAdapter);
        Competition gameEvent = (Competition)reportCompetitionSpinner.getSelectedItem();
        gameAdapter = new ArrayAdapter<Game>(this, android.R.layout.simple_spinner_item, android.R.id.text1, BettingDB.getInstance().getEventGames(gameEvent.getEventName()));
        reportGameSpinner.setAdapter(gameAdapter);

        teamAnp = (NumberPicker)findViewById(R.id.reportTeamAGoal);
        teamBnp = (NumberPicker)findViewById(R.id.reportTeamBGoal);

        teamAnp.setMinValue(0);
        teamAnp.setMaxValue(9);
        teamAnp.setWrapSelectorWheel(true);

        teamBnp.setMinValue(0);
        teamBnp.setMaxValue(9);
        teamBnp.setWrapSelectorWheel(true);

        reportButton = (Button) findViewById(R.id.reportUpdateGameButton);

        MyItemSelectedListener myItemSelectedListener= new MyItemSelectedListener();

        reportCompetitionSpinner.setOnItemSelectedListener(myItemSelectedListener);
        reportGameSpinner.setOnItemSelectedListener(myItemSelectedListener);

    }


        public void updateGame(View view) {
        Game game = (Game) reportGameSpinner.getSelectedItem();
        game.setEventResult(teamAnp.getValue() + "-" + teamBnp.getValue());
        BettingDB.getInstance().updateGame(game);
        reportButton.setText(getString(R.string.reportResultUpdateButton));
    }

    private class MyItemSelectedListener implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {


            if (parent.getId()==R.id.reportCompetitionsSpinner){
                Competition selectedCompetition = (Competition) reportCompetitionSpinner.getSelectedItem();
                gameAdapter.clear();
                gameAdapter.addAll(BettingDB.getInstance().getEventGames(selectedCompetition.getEventName()));
                gameAdapter.notifyDataSetChanged();
                System.out.println("game");
            }
            else if (parent.getId()==R.id.reportGameSpinner){
                Game selectedGame = (Game) reportGameSpinner.getSelectedItem();

                if (selectedGame.getEventResult().equals("-")) {
                    teamAnp.setValue(0);
                    teamBnp.setValue(0);
                    reportButton.setText(getString(R.string.reportResultButton));
                }
                else{
                    String[] resultString =selectedGame.getEventResult().split("-");
                    teamAnp.setValue(Integer.parseInt(resultString[0]));
                    teamBnp.setValue(Integer.parseInt(resultString[1]));
                    reportButton.setText(getString(R.string.reportResultUpdateButton));

                }

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
