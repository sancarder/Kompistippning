package com.example.mcmac.kompistippning;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;

/**
 * Created by Mattias on 2016-06-24.
 */

public class BettingDB extends AbstractBettingDB{

    private static final BettingDB theOnlyDB = new BettingDB(); //Singleton

    private BettingDB() {  //Private due to Singleton pattern

    }

    public static BettingDB getInstance(){ //Call this to get the DB object where ever in the app
        return theOnlyDB;
    }

    public Competition insertCompetition (String event_name){
        ContentValues values = new ContentValues();
        values.put(KEY_EVENT_NAME, event_name);
        long rowId = database.insert(DB_TABLE_COMPETITIONS, null, values);
        return new Competition(rowId, event_name);
    }

    public Game insertGame(String event_name, String date, String team_A, String team_B, String bet_amounts, String game_type, String eventResult ){
        ContentValues values = new ContentValues();
        values.put(KEY_EVENT_NAME, event_name);
        values.put(KEY_DATE, date);
        values.put(KEY_TEAM_A, team_A);
        values.put(KEY_TEAM_B, team_B);
//        values.put(KEY_GAME_ID, gameId);
        values.put(KEY_BET_AMOUNT, bet_amounts);
        values.put(KEY_GAME_TYPE, game_type);
        values.put(KEY_EVENT_RESULT, eventResult);
        long rowId = database.insert(DB_TABLE_GAMES, null, values);
        return new Game(rowId, event_name, date, team_A, team_B, bet_amounts, game_type, eventResult );
    }

    public Participant insertParticipant(String event_name, String person){
        ContentValues values = new ContentValues();
        values.put(KEY_EVENT_NAME, event_name);
        values.put(KEY_PERSON, person);
        long rowId = database.insert(DB_TABLE_PARTICIPANTS, null, values);
        return new Participant(rowId, event_name, person);
    }


    public Bet insertBet(String event_name, String person, String game_id, String bet){
        ContentValues values = new ContentValues();
        values.put(KEY_EVENT_NAME, event_name);
        values.put(KEY_PERSON, person);
        values.put(KEY_GAME_ID, game_id);
        values.put(KEY_BET, bet);
        long rowId = database.insert(DB_TABLE_BETS, null, values);
        return new Bet(rowId, event_name, person, game_id, bet);
    }

    public boolean updateGame(Game game){
        ContentValues values = new ContentValues();
        values.put(KEY_EVENT_NAME, game.getEventName());
        values.put(KEY_DATE, game.getDate());
        values.put(KEY_TEAM_A, game.getTeamA());
        values.put(KEY_TEAM_B, game.getTeamB());
        values.put(KEY_BET_AMOUNT, game.getBetAmount());
        values.put(KEY_GAME_TYPE, game.getGameType());
        values.put(KEY_EVENT_RESULT, game.getEventResult());
        return database.update(DB_TABLE_GAMES, values, KEY_ROW_ID + "=" + game.getRowId(), null) >0;
    }
    /* Detta ska implementeras senare för KompisBetting
    public boolean updateMunicip(Municip municip){
        ContentValues values = new ContentValues();
        values.put(KEY_MUNICIP_NAME, municip.getMunicip());
        values.put(KEY_EMAIL, municip.getEmail());
        values.put(KEY_PHONE, municip.getPhone());
        Log.v("MyTag", "updates");
        return database.update(DB_TABLE_MUNICIP, values, KEY_MUNICIP_ID + "=" + municip.getId(), null) > 0;
    }

    public boolean updateEventPoint(EventPoint event){
        ContentValues values = new ContentValues();
        values.put(KEY_EVENT_NAME, event.getEvent());
        values.put(KEY_MUNICIP_NAME, event.getMunicip());
        values.put(KEY_EVENT_GRADE, event.getGrade());
        values.put(KEY_EVENT_PLACE, event.getPlace());
        values.put(KEY_EVENT_DESCRIPTION, event.getDescription());
        values.put(KEY_EVENT_STATUS, event.getStatus());
        values.put(KEY_EVENT_DATE, event.getDate());
        return database.update(DB_TABLE_EVENT, values, KEY_EVENT_ID + "=" + event.getId(), null) > 0;
    }*/


    public boolean deleteCompetition(Competition competition){
        return database.delete(DB_TABLE_COMPETITIONS, KEY_ROW_ID + "=" +competition.getRowId(), null)>0;
    }

    public boolean deleteGame(Game game){
        return database.delete(DB_TABLE_GAMES, KEY_ROW_ID + "=" +game.getRowId(), null)>0;
    }
    public boolean deleteParticipant(Participant participant){
        return database.delete(DB_TABLE_PARTICIPANTS, KEY_ROW_ID + "=" +participant.getRowId(), null)>0;
    }
    public boolean deleteBet(Bet bet){
        return database.delete(DB_TABLE_BETS, KEY_ROW_ID + "=" +bet.getRowId(), null)>0;
    }
    /*Detta ska implementeras senare för KompisBetting
    public boolean deleteMunicip(Municip municip){
        return database.delete(DB_TABLE_MUNICIP, KEY_MUNICIP_ID + "=" + municip.getId(), null) > 0;
    }
    public boolean deleteEventPoint(EventPoint event){
        return database.delete(DB_TABLE_EVENT, KEY_EVENT_ID + "=" + event.getId(), null) > 0;
    }
*/

    public ArrayList<Competition> getAllCompetitions(){
        Cursor cr = getAllCompetitionsCursor();
        return makeCompetitionListFromCursor(cr);
    }
    public ArrayList<Game> getAllGames(){
        Cursor cr = getAllGamesCursor();
        return makeGameListFromCursor(cr);
    }

    public ArrayList<Game> getEventGames(String event_name){
        Cursor cr = getEventGamesCursor(event_name);
        return makeGameListFromCursor(cr);
    }

    public ArrayList<Bet> getGameBet(String event_name, long gameId, String participant) {
        Cursor cr = getGameBetCursor(event_name, gameId, participant);
        return makeBetListFromCursor(cr);
    }

    public ArrayList<Participant> getAllParticipants(){
        Cursor cr = getAllParticipantsCursor();
        return makeParticipantListFromCursor(cr);
    }

    public ArrayList<Participant> getEventParticipants(String event_name){
        Cursor cr = getEventParticipantsCursor(event_name);
        return makeParticipantListFromCursor(cr);
    }

    public ArrayList<Bet> getAllBets(){
        Cursor cr = getAllBetsCursor();
        return makeBetListFromCursor(cr);
    }


    private ArrayList<Competition> makeCompetitionListFromCursor(Cursor cr){
        ArrayList<Competition> competitions = new ArrayList<Competition>();
        if (cr != null && cr.moveToFirst())
            do {
                competitions.add(new Competition(cr.getInt(0), cr.getString(1)));
            } while (cr.moveToNext());
        return competitions;
    }

    private ArrayList<Game> makeGameListFromCursor(Cursor cr){
        ArrayList<Game> games = new ArrayList<Game>();
        if (cr != null && cr.moveToFirst())
            do {
                games.add(new Game(cr.getInt(0), cr.getString(1), cr.getString(2), cr.getString(3), cr.getString(4), cr.getString(5), cr.getString(6), cr.getString(7)));
            } while (cr.moveToNext());
        return games;
    }

    private ArrayList<Participant> makeParticipantListFromCursor(Cursor cr){
        ArrayList<Participant> participants = new ArrayList<Participant>();
        if (cr != null && cr.moveToFirst())
            do {
                participants.add(new Participant(cr.getInt(0), cr.getString(1), cr.getString(2)));
            } while (cr.moveToNext());
        return participants;
    }

    private ArrayList<Bet> makeBetListFromCursor(Cursor cr){
        ArrayList<Bet> bets = new ArrayList<Bet>();
        if (cr != null && cr.moveToFirst())
            do {
                bets.add(new Bet(cr.getInt(0), cr.getString(1), cr.getString(2), cr.getString(3), cr.getString(4)));
            } while (cr.moveToNext());
        return bets;
    }


    protected void createTestData() {
        //Competition-table
        BettingDB.getInstance().insertCompetition("EM 2016");
        BettingDB.getInstance().insertCompetition("VM 2018");

        //Games-table
        BettingDB.getInstance().insertGame("EM 2016", "2016-06-25", "Kroatien", "Portugal", "5", "Åttondel", "-");
        BettingDB.getInstance().insertGame("EM 2016", "2016-07-08", "Polen", "Sverige", "7", "Semifinal", "2-1");
        BettingDB.getInstance().insertGame("VM 2018", "2018-06-15", "Spanien", "Italien", "10", "Final", "-");

        //Participant-table
        BettingDB.getInstance().insertParticipant("EM 2016", "David");
        BettingDB.getInstance().insertParticipant("EM 2016", "Julia");
        BettingDB.getInstance().insertParticipant("VM 2018", "Sandra");
        BettingDB.getInstance().insertParticipant("VM 2018", "Mattias");

        //Bet-table
        BettingDB.getInstance().insertBet("EM 2016", "David", "1", "1-2");

    }

}