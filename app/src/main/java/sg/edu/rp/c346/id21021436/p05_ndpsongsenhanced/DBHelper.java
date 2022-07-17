package sg.edu.rp.c346.id21021436.p05_ndpsongsenhanced;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "ndpsongs.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_SONGS= "song_table";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_SONG_TITLE = "song_title";
    private static final String COLUMN_SINGER = "singer";
    private static final String COLUMN_YEAR = "year";
    private static final String COLUMN_STARS = "stars";

    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Create database
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createSongsTableSql = "CREATE TABLE " + TABLE_SONGS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_SONG_TITLE + " TEXT," +  COLUMN_SINGER + " TEXT," + COLUMN_YEAR + " INTEGER," + COLUMN_STARS + " INTEGER ) ";
        db.execSQL(createSongsTableSql);

    }

    //Upgrade database - drop table method (not ideal)
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SONGS);
        onCreate(db);


    }

    // Insert a Song
//    public long insertSong(String title, String singers, int year, int stars) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(COLUMN_SONG_TITLE, title);
//        values.put(COLUMN_SINGER, singers);
//        values.put(COLUMN_YEAR, year);
//        values.put(COLUMN_STARS, stars);
//        long result = db.insert(TABLE_SONGS, null, values);
//        db.close();
//        Log.d("SQL Insert","ID:"+ result); //id returned, shouldn’t be -1
//        return result;
//
//    }

    public long insertSong(Song song) {
        String title = song.getTitle();
        String singers = song.getSingers();
        int year = song.getYear();
        int stars = song.getStars();
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_SONG_TITLE, title);
        values.put(COLUMN_SINGER, singers);
        values.put(COLUMN_YEAR, year);
        values.put(COLUMN_STARS, stars);
        long result = db.insert(TABLE_SONGS, null, values);
        db.close();
        Log.d("SQL Insert","ID:"+ result); //id returned, shouldn’t be -1
        return result;

    }

    //Retrieve all songs
    public ArrayList<Song> getAllSongs() {
        ArrayList<Song> songs = new ArrayList<Song>();

        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns= {COLUMN_ID, COLUMN_SONG_TITLE, COLUMN_SINGER, COLUMN_YEAR, COLUMN_STARS};
        Cursor cursor = db.query(TABLE_SONGS, columns, null, null,
                null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String songTitle = cursor.getString(1);
                String songSinger = cursor.getString(2);
                int songYear = cursor.getInt(3);
                int songStars = cursor.getInt(4);
                Song song = new Song (id, songTitle, songSinger, songYear, songStars);
                songs.add(song);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return songs;
    }

    //Retrieve five star rated songs
    public ArrayList<Song> getFiveStar() {
        ArrayList<Song> songs = new ArrayList<Song>();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns= {COLUMN_ID, COLUMN_SONG_TITLE, COLUMN_SINGER, COLUMN_YEAR, COLUMN_STARS};

        //not sure of this SQLite syntax, correct if needed later
        String condition = COLUMN_STARS + " = ?";
        String[] args = {"5"};
        Cursor cursor = db.query(TABLE_SONGS, columns, condition, args,
                null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String songTitle = cursor.getString(1);
                String songSingers = cursor.getString(2);
                int year = cursor.getInt(3);
                int stars = cursor.getInt(4);
                Song song = new Song(id, songTitle, songSingers, year, stars);
                songs.add(song);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return songs;
    }

    //Update a song
    public int updateSong(Song song, String newTitle, String newSinger, int newYear, int newStars){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_SONG_TITLE, newTitle);
        values.put(COLUMN_SINGER, newSinger);
        values.put(COLUMN_YEAR, newYear);
        values.put(COLUMN_STARS, newStars);
        String condition = COLUMN_ID + " = ?";
        String[] args = {song.get_id() + ""};
        int result = db.update(TABLE_SONGS, values, condition, args);
        db.close();
        return result;

    }

    //Delete a song
    public int deleteSong(Song song){
        SQLiteDatabase db = this.getWritableDatabase();
        String condition = COLUMN_ID + " = ?";
        String[] args = {song.get_id() + ""};
        int result = db.delete(TABLE_SONGS, condition, args);
        db.close();
        return result;
    }

    //Deleting *all* songs while retaining table structure
    public void deleteAllSongs(){
        SQLiteDatabase db = this.getWritableDatabase();

//        This method (which is the equivalent of TRUNCATE in SQLite still renumbers the AUTOINCREMENT from previous residual value
//        db.execSQL("DELETE FROM " + TABLE_SONGS);
//        db.close();

        // This method (DROP and recreate) will start numbering at 1 again.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SONGS);
        onCreate(db);
        db.close();


    }


}
