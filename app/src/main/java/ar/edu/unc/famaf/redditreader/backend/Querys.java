package ar.edu.unc.famaf.redditreader.backend;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ar.edu.unc.famaf.redditreader.model.Listing;
import ar.edu.unc.famaf.redditreader.model.PostModel;

import static ar.edu.unc.famaf.redditreader.backend.RedditDBHelper.AUTHOR_TABLE;
import static ar.edu.unc.famaf.redditreader.backend.RedditDBHelper.DATE_TABLE;
import static ar.edu.unc.famaf.redditreader.backend.RedditDBHelper.IMAGE_TABLE;
import static ar.edu.unc.famaf.redditreader.backend.RedditDBHelper.NCOMENT_TABLE;
import static ar.edu.unc.famaf.redditreader.backend.RedditDBHelper.SUB_TABLE;
import static ar.edu.unc.famaf.redditreader.backend.RedditDBHelper.TABLE_NAME;
import static ar.edu.unc.famaf.redditreader.backend.RedditDBHelper.TITLE_TABLE;

public class Querys {

    public static void insert(SQLiteDatabase db, Listing listing) {
        ContentValues values =  new ContentValues();
        List<PostModel> list = listing.getPosts();

        for (int i=0; i < list.size(); i++ ) {

            String mTitle = list.get(i).getmTitle();
            String mAuthor = list.get(i).getmAuthor();
            long mDate = list.get(i).getmDate();
            String mSub = list.get(i).getmSub();
            int mNumberOfComments = list.get(i).getmNumberOfComments();
            String mImage = null;
            if(list.get(i).getmImage() != null)
                mImage = list.get(i).getmImage().toString();

            values.put(TITLE_TABLE, mTitle);
            values.put(AUTHOR_TABLE, mAuthor);
            values.put(DATE_TABLE, mDate);
            values.put(SUB_TABLE, mSub);
            values.put(NCOMENT_TABLE, mNumberOfComments);
            values.put(IMAGE_TABLE, mImage);
            db.insert(TABLE_NAME, null, values);
        }

    }

    public static void delete_posts(SQLiteDatabase db){
        db.delete(TABLE_NAME, null, null);
    }

    public static List<PostModel> getPost(SQLiteDatabase db) throws MalformedURLException {
        ArrayList<PostModel> list = new ArrayList<>();
        String mTitle;
        String mAuthor;
        long mDate;
        String mSub;
        int mNumberOfComents;
        String mImage;
        Cursor c = db.rawQuery(" SELECT * FROM " + TABLE_NAME, null);
        if (c.moveToFirst()) {
            do {
                mTitle = c.getString(c.getColumnIndex(TITLE_TABLE));
                mAuthor = c.getString(c.getColumnIndex(AUTHOR_TABLE));
                mDate = c.getLong(c.getColumnIndex(DATE_TABLE));
                mSub = c.getString(c.getColumnIndex(SUB_TABLE));
                mNumberOfComents = c.getInt(c.getColumnIndex(NCOMENT_TABLE));
                mImage = c.getString(c.getColumnIndex(IMAGE_TABLE));
                try {
                    list.add(new PostModel(mTitle, mAuthor, mDate, mSub, mNumberOfComents, new URL(mImage)));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            } while (c.moveToNext());
        }
        c.close();
        return list;
    }
}
