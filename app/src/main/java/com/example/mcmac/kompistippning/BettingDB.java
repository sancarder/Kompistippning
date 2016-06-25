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

    public Game insertGame(String event_name, String date, String team_A, String team_B, String bet_amounts, String game_type ){
        ContentValues values = new ContentValues();
        values.put(KEY_EVENT_NAME, event_name);
        values.put(KEY_DATE, date);
        values.put(KEY_TEAM_A, team_A);
        values.put(KEY_TEAM_B, team_B);
//        values.put(KEY_GAME_ID, gameId);
        values.put(KEY_BET_AMOUNT, bet_amounts);
        values.put(KEY_GAME_TYPE, game_type);
        long rowId = database.insert(DB_TABLE_GAMES, null, values);
        return new Game(rowId, event_name, date, team_A, team_B, bet_amounts, game_type );
    }

    public Participant insertParticipant(String event_name, String person){
        ContentValues values = new ContentValues();
        values.put(KEY_EVENT_NAME, event_name);
        values.put(KEY_PERSON, person);
        long rowId = database.insert(DB_TABLE_PARTICIPANTS, null, values);
        return new Participant(rowId, event_name, person);
    }
    public Bet insertBet(String event_name, String person, int game_id, String bet){
        ContentValues values = new ContentValues();
        values.put(KEY_EVENT_NAME, event_name);
        values.put(KEY_PERSON, person);
        values.put(KEY_GAME_ID, game_id);
        values.put(KEY_BET, bet);
        long rowId = database.insert(DB_TABLE_BETS, null, values);
        return new Bet(rowId, event_name, person, game_id, bet);
    }

    /*
    public Municip insertMunicip(String municip, String email, String phone){
        ContentValues values = new ContentValues();
        values.put(KEY_MUNICIP_NAME, municip);
        values.put(KEY_EMAIL, email);
        values.put(KEY_PHONE, phone);
        long rowId = database.insert(DB_TABLE_MUNICIP, null, values);
        Log.v("MyTag", "inserts");
        return new Municip(rowId, municip, email, phone);
    }

    public EventPoint insertEventPoint(String event, String municip, String grade, String place, String description, String status, String date){
        ContentValues values = new ContentValues();
        values.put(KEY_EVENT_NAME, event);
        values.put(KEY_MUNICIP_NAME, municip);
        values.put(KEY_EVENT_GRADE, grade);
        values.put(KEY_EVENT_PLACE, place);
        values.put(KEY_EVENT_DESCRIPTION, description);
        values.put(KEY_EVENT_STATUS, status);
        values.put(KEY_EVENT_DATE, date);
        long rowId = database.insert(DB_TABLE_EVENT, null, values);
        Log.v("MyTag",String.valueOf(rowId));
        return new EventPoint(rowId, event, municip, grade, place, description, status, date);
    }*/

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
    public ArrayList<Participant> getAllParticipants(){
        Cursor cr = getAllParticipantsCursor();
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
                games.add(new Game(cr.getInt(0), cr.getString(1), cr.getString(2), cr.getString(3), cr.getString(4), cr.getString(5), cr.getString(6)));
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
                bets.add(new Bet(cr.getInt(0), cr.getString(1), cr.getString(2), cr.getInt(3), cr.getString(4)));
            } while (cr.moveToNext());
        return bets;
    }

/*
    public ArrayList<Municip> getAllMunicips() {
        Cursor cr = getAllMunicipsCursor();
        return makeMunicipListFromCursor(cr);
    }
    public ArrayList<EventPoint> getAllEventPoints() {
        Cursor cr = getAllEventPointsCursor();
        return makeEventPointListFromCursor(cr);
    }

    private ArrayList<Municip> makeMunicipListFromCursor(Cursor cr){
        ArrayList<Municip> municips = new ArrayList<Municip>();
        if (cr != null && cr.moveToFirst())
            do {
                municips.add(new Municip(cr.getInt(0), cr.getString(1), cr.getString(2), cr.getString(3)));
            } while (cr.moveToNext());
        return municips;
    }

    private ArrayList<EventPoint> makeEventPointListFromCursor(Cursor cr){
        ArrayList<EventPoint> events = new ArrayList<EventPoint>();
        if (cr != null && cr.moveToFirst())
            do {
                events.add(new EventPoint(cr.getInt(0), cr.getString(1), cr.getString(2), cr.getString(3), cr.getString(4), cr.getString(5),cr.getString(6), cr.getString(7)));
            } while (cr.moveToNext());
        return events;
    }*/

    protected void createTestData() {
        //Competition-table
        BettingDB.getInstance().insertCompetition("EM 2016");

        //Games-table
        BettingDB.getInstance().insertGame("EM 2016","2016-06-25","Kroatien", "Portugal", "5","Åttondel");

        //Participant-table
        BettingDB.getInstance().insertParticipant("EM 2016", "David");

        //Bet-table
        BettingDB.getInstance().insertBet("EM 2016", "David", 0, "1-2");

        /*
        //Municip-table
        MunicipDB.getInstance().insertMunicip("Goteborgs kommun", "goteborg@testdata.se", "031-XXXXX");
        MunicipDB.getInstance().insertMunicip("Vaggeryds kommun", "vaggeryd@testdata.se", "0393-XXXXX");
        MunicipDB.getInstance().insertMunicip("Molndals kommun", "molndal@testdata.se", "031-2XXXXX");

        //EventPoint-table
        MunicipDB.getInstance().insertEventPoint("Trasig lampa", "Vaggeryds kommun", "Allvarlig", "Koord", "Kvall i december", "false", "2015-12-10");
        MunicipDB.getInstance().insertEventPoint("Trasig jarnvagsbom", "Göteborgs kommun", "Mycket allvarlig", "Koord", "Kvall i december", "true", "2015-12-24");
    */
    }

}