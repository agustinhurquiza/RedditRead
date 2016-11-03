package ar.edu.unc.famaf.redditreader.backend;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by agustin on 03/11/16.
 */

public class RedditDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "db1.db";
    private static final String TABLE_NAME = "post_model";
    private static final String TABLE_ID = "_id";
    private static final String TITLE_TABLE = "title";
    private static final String AUTHOR_TABLE = "author";
    private static final String DATE_TABLE = "date";
    private static final String SUB_TABLE = "sub";
    private static final String NCOMENT_TABLE = "ncoment";
    private static final String IMAGE_TABLE = "image";

    public RedditDBHelper(Context context, int version) {
        super(context, DATABASE_NAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createSentence = "create table " + TABLE_NAME + "( "+ TABLE_ID + " integer primary key autoincremet, "
                + TITLE_TABLE + " text no null, " + AUTHOR_TABLE + " text no null, "
                + DATE_TABLE + " integer no null, "+ SUB_TABLE + " text no null, "
                + NCOMENT_TABLE + " integer no null, " + IMAGE_TABLE + " text no null);";
        db.execSQL(createSentence);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXIST"+ TABLE_NAME);
    }
}
