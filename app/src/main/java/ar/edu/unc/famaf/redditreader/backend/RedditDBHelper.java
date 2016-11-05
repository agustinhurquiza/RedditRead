package ar.edu.unc.famaf.redditreader.backend;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class RedditDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "db1.db";
    public static final String TABLE_NAME = "post_model";
    public static final String ID = "_id";
    public static final String TITLE = "title";
    public static final String AUTHOR = "author";
    public static final String DATE = "date";
    public static final String SUB = "sub";
    public static final String NCOMENT = "ncoment";
    public static final String IMAGE_URL = "image_url";
    public static final String IMAGE_BITMAP ="image_bitmap";
    public static final int DATABASE_VERSION = 1;

    public RedditDBHelper(Context context, int version) {
        super(context, DATABASE_NAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createSentence = "create table " + TABLE_NAME + "( "+ ID +
                                " integer primary key autoincrement, "
                                + TITLE + " text no null, "
                                + AUTHOR + " text no null, "
                                + DATE + " integer no null, "
                                + SUB + " text no null, "
                                + IMAGE_BITMAP + " blob, "
                                + NCOMENT + " integer no null, " + IMAGE_URL + " text no null);";
        db.execSQL(createSentence);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXIST"+ TABLE_NAME);
    }
}
