package com.colourmemory.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * store all the sqlite staff here
 *
 * @author donlinglok
 *
 */
public class SQLiteHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Local_Rank";
    private static final String TABLE_NAME = "HighScore";

    /**
     * High scores must be stored and persist as long as the user does not
     * uninstall the app or clear the app's stored data. High scores database
     * may be implemented using the device's internal database.
     *
     * @param context
     */
    public SQLiteHelper(final Context context) {
	super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * create table on start if not exists
     */
    @Override
    public void onCreate(final SQLiteDatabase sqldb) {
	sqldb.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(_id INTEGER PRIMARY KEY, "
		+ "username TEXT NOT NULL," + "point INT(11) NOT NULL, " + " time TEXT NOT NULL)");
	// The high scores table may contain negative numbers.
    }

    /**
     * on database version change,
     */
    @Override
    public void onUpgrade(final SQLiteDatabase sqldb, final int oldVersion, final int newVersion) {
	// usally i just add another table.
    }

    /**
     * get data from sqldb
     *
     * @return
     */
    public Cursor select() {
	final SQLiteDatabase sqldb = this.getReadableDatabase();
	return sqldb.query(TABLE_NAME, null, null, null, null, null, "point ASC, time ASC");
	// A score is considered a high score if the score is higher than a
	// current high score OR there is space in the table.
    }

    /**
     * add data to sqldb
     *
     * @param username
     * @param point
     * @param time
     * @return
     */
    public long insert(final String username, final String point, final String time) {
	final SQLiteDatabase sqldb = this.getWritableDatabase();
	final ContentValues contentValues = new ContentValues();
	contentValues.put("username", username);
	contentValues.put("point", point);
	contentValues.put("time", time);
	return sqldb.insert(TABLE_NAME, null, contentValues);
    }
}
