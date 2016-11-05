package ar.edu.unc.famaf.redditreader.backend;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class RedditDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "db1.db";
    public static final String TABLE_NAME = "post_model";
    public static final String TABLE_ID = "_id";
    public static final String TITLE_TABLE = "title";
    public static final String AUTHOR_TABLE = "author";
    public static final String DATE_TABLE = "date";
    public static final String SUB_TABLE = "sub";
    public static final String NCOMENT_TABLE = "ncoment";
    public static final String IMAGE_TABLE = "image";
    public static final String IMAGE_B_TABLE ="imageB";
    public static final int DATABASE_VERSION = 1;

    public RedditDBHelper(Context context, int version) {
        super(context, DATABASE_NAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createSentence = "create table " + TABLE_NAME + "( "+ TABLE_ID +
                                " integer primary key autoincrement, "
                                + TITLE_TABLE + " text no null, "
                                + AUTHOR_TABLE + " text no null, "
                                + DATE_TABLE + " integer no null, "
                                + SUB_TABLE + " text no null, "
                                + IMAGE_B_TABLE + " blob, "
                                + NCOMENT_TABLE + " integer no null, " + IMAGE_TABLE + " text no null);";
        db.execSQL(createSentence);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXIST"+ TABLE_NAME);
    }
}
