package com.example.mcmac.kompistippning;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.SQLException;

/**
 * Created by Mattias on 2016-01-06.
 */
public abstract class AbstractBettingDB {


    //Competitionstable
    public static final String DB_TABLE_COMPETITIONS ="competitions";
    public static final String KEY_EVENT_NAME = "event_name";
    public static final String KEY_ROW_ID = "row_id";

    //Gamestable
    public static final String DB_TABLE_GAMES = "games";
    public static final String KEY_DATE = "date";
    public static final String KEY_TEAM_A ="team_a";
    public static final String KEY_TEAM_B ="team_b";
    public static final String KEY_GAME_ID ="game_id";
    public static final String KEY_BET_AMOUNT= "bet_amount";
    public static final String KEY_GAME_TYPE = "game_type";

    //Participantstable
    public static final String DB_TABLE_PARTICIPANTS = "participants";
    public static final String KEY_PERSON = "person";

    //Betstable
    public static final String DB_TABLE_BETS = "bets";
    public static final String KEY_BET="bet";
/*
    //Kommuntabellen
    public static final String DB_TABLE_MUNICIP ="municip";
    public static final String KEY_MUNICIP_ID ="municip_id";
    public static final String KEY_MUNICIP_NAME="municip_name";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PHONE = "phone";

    //Händelsetabellen
    public static final String DB_TABLE_EVENT ="event";
    public static final String KEY_EVENT_ID = "event_id";
    //public static final String KEY_EVENT_NAME = "event_name";
    public static final String KEY_EVENT_GRADE = "event_grade";
    public static final String KEY_EVENT_PLACE = "event_place";
    public static final String KEY_EVENT_DESCRIPTION ="event_description";
    public static final String KEY_EVENT_STATUS ="event_status";
    public static final String KEY_EVENT_DATE = "event_date";
    public static final String DB_NAME = "municipalities";
*/
    public static final String DB_NAME = "competitionbets";
    protected SQLiteDatabase database;

    private static final String [] competitions_columns = {KEY_ROW_ID, KEY_EVENT_NAME};
    private static final String [] games_columns = {KEY_ROW_ID, KEY_EVENT_NAME,KEY_DATE, KEY_TEAM_A, KEY_TEAM_B, KEY_BET_AMOUNT, KEY_GAME_TYPE};
    private static final String [] participants_columns = {KEY_ROW_ID, KEY_EVENT_NAME, KEY_PERSON};
    private static final String [] bets_columns= {KEY_EVENT_NAME, KEY_PERSON, KEY_GAME_ID, KEY_BET};

    //private static final String [] municip_columns = {KEY_MUNICIP_ID,KEY_MUNICIP_NAME, KEY_EMAIL, KEY_PHONE};
    //private static final String [] event_columns = {KEY_EVENT_ID, KEY_EVENT_NAME, KEY_MUNICIP_NAME, KEY_EVENT_GRADE, KEY_EVENT_PLACE, KEY_EVENT_DESCRIPTION, KEY_EVENT_STATUS, KEY_EVENT_DATE};

    private static final int DB_VERSION = 5;
    private MyDbHelper myDbHelper;
    private Context context;

    //Strängar för skapande av de fyra tabellerna
    private static final String DB_CREATE_COMPETITIONS = "create table " + DB_TABLE_COMPETITIONS + " (" +KEY_ROW_ID + " integer primary key autoincrement, " + KEY_EVENT_NAME +" text not null );";
    private static final String DB_CREATE_GAMES = "create table " + DB_TABLE_GAMES + " ("+KEY_ROW_ID + " integer primary key autoincrement, "  + KEY_EVENT_NAME +" text not null, " +KEY_DATE +" text not null, " +KEY_TEAM_A +" text not null, " + KEY_TEAM_B + " text not null, " +KEY_BET_AMOUNT +" text not null, " +KEY_GAME_TYPE +" text not null );";
    private static final String DB_CREATE_PARTICIPANTS = "create table " + DB_TABLE_PARTICIPANTS + " (" +KEY_ROW_ID +" integer primary key autoincrement, " + KEY_EVENT_NAME + " text not null, " + KEY_PERSON +" text not null );";
    private static final String DB_CREATE_BETS = "create table " + DB_TABLE_BETS + " (" +KEY_EVENT_NAME + " text not null," +KEY_PERSON +" text not null, "+ KEY_GAME_ID + " text not null, " + KEY_BET + " text not null );";
    //private static final String DB_CREATE_MUNICIP = "create table " + DB_TABLE_MUNICIP + " (" +KEY_MUNICIP_ID + " integer primary key autoincrement, " + KEY_MUNICIP_NAME + " text not null, " + KEY_EMAIL + " text not null, " + KEY_PHONE + " text not null );";
    //private static final String DB_CREATE_EVENT = "create table " + DB_TABLE_EVENT + " (" +KEY_EVENT_ID + " integer primary key autoincrement, "+ KEY_EVENT_NAME + " text not null, " + KEY_MUNICIP_NAME + " text not null, " + KEY_EVENT_GRADE + " text not null, "+ KEY_EVENT_PLACE + " text not null, " + KEY_EVENT_DESCRIPTION + " text not null, "+ KEY_EVENT_STATUS + " text not null, " + KEY_EVENT_DATE + " text not null );";


    protected abstract void createTestData(); //Must be implemented

    public void open(boolean writable, Context ctx) throws SQLException {
        this.context = ctx;
        myDbHelper= new MyDbHelper(context);
        if (writable){
            database =myDbHelper.getWritableDatabase();
            if (myDbHelper.isFirstTime())
                createTestData();
        }
        else
            database =myDbHelper.getReadableDatabase();
    }

    public void close(){
        myDbHelper.close();
    }
/*
    protected Cursor getAllMunicipsCursor(){ //select * from Municip
        return database.query(DB_TABLE_MUNICIP, municip_columns, null, null, null, null, null);
    }

    protected Cursor getAllEventPointsCursor(){ //select * from EventPoint
        return database.query(DB_TABLE_EVENT, event_columns, null, null, null, null, null);
    }
    */

    protected Cursor getAllCompetitionsCursor(){ //select * from Competitions
        return database.query(DB_TABLE_COMPETITIONS,competitions_columns, null, null, null, null, null);
    }

    protected Cursor getAllGamesCursor(){ //select * from Games
        return database.query(DB_TABLE_GAMES,games_columns, null, null, null, null, null);
    }

    protected Cursor getAllParticipantsCursor(){ //select * from Participants
        return database.query(DB_TABLE_PARTICIPANTS,participants_columns, null, null, null, null, null);
    }

    protected Cursor getAllBetsCursor(){ //select * from Bets
        return database.query(DB_TABLE_BETS,bets_columns, null, null, null, null, null);
    }

    protected static class MyDbHelper extends SQLiteOpenHelper { //Local help class
        private boolean firstTime = false;
        MyDbHelper(Context context){
            super(context, DB_NAME, null, DB_VERSION);
        }
        public void onCreate(SQLiteDatabase sqldb){
//            sqldb.execSQL(DB_CREATE_MUNICIP);
//            sqldb.execSQL(DB_CREATE_EVENT);
            sqldb.execSQL(DB_CREATE_COMPETITIONS);
            sqldb.execSQL(DB_CREATE_GAMES);
            sqldb.execSQL(DB_CREATE_PARTICIPANTS);
            sqldb.execSQL(DB_CREATE_BETS);
            firstTime =true;
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        //    db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE_MUNICIP);
        //    db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE_EVENT);
            db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE_COMPETITIONS);
            db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE_GAMES);
            db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE_PARTICIPANTS);
            db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE_BETS);
            onCreate(db);
        }
        public boolean isFirstTime(){
            return firstTime;
        }
    }
}