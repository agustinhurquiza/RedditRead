package ar.edu.unc.famaf.redditreader.backend;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unc.famaf.redditreader.model.Listing;
import ar.edu.unc.famaf.redditreader.model.PostModel;

import static ar.edu.unc.famaf.redditreader.backend.RedditDBHelper.AUTHOR;
import static ar.edu.unc.famaf.redditreader.backend.RedditDBHelper.DATE;
import static ar.edu.unc.famaf.redditreader.backend.RedditDBHelper.IMAGE_BITMAP;
import static ar.edu.unc.famaf.redditreader.backend.RedditDBHelper.IMAGE_URL;
import static ar.edu.unc.famaf.redditreader.backend.RedditDBHelper.NCOMENT;
import static ar.edu.unc.famaf.redditreader.backend.RedditDBHelper.SUB;
import static ar.edu.unc.famaf.redditreader.backend.RedditDBHelper.TABLE_NAME;
import static ar.edu.unc.famaf.redditreader.backend.RedditDBHelper.TITLE;

public class Querys {

    public static void insert_posts(SQLiteDatabase db, Listing listing) {
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

            values.put(TITLE, mTitle);
            values.put(AUTHOR, mAuthor);
            values.put(DATE, mDate);
            values.put(SUB, mSub);
            values.put(NCOMENT, mNumberOfComments);
            values.put(IMAGE_URL, mImage);
            db.insert(TABLE_NAME, null, values);
        }

    }

    public static void delete_posts(SQLiteDatabase db){
        db.delete(TABLE_NAME, null, null);
    }

    public static List<PostModel> getPosts(SQLiteDatabase db) throws MalformedURLException {
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
                mTitle = c.getString(c.getColumnIndex(TITLE));
                mAuthor = c.getString(c.getColumnIndex(AUTHOR));
                mDate = c.getLong(c.getColumnIndex(DATE));
                mSub = c.getString(c.getColumnIndex(SUB));
                mNumberOfComents = c.getInt(c.getColumnIndex(NCOMENT));
                mImage = c.getString(c.getColumnIndex(IMAGE_URL));
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

    // Chequea si la base de datos es vacia.
    public static boolean is_empty(SQLiteDatabase db){
        Cursor c = db.rawQuery(" SELECT * FROM " + TABLE_NAME, null);
        if (c.moveToFirst()) {
            c.close();
            return true;
        }
        c.close();
        return false;
    }

    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,0, stream);
        return stream.toByteArray();
    }

    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    // Dado un bitmap lo agrega a la base de dato.
   public static void add_image(SQLiteDatabase bd, Bitmap bitmap, URL url){
       ContentValues values = new ContentValues();
       values.put(RedditDBHelper.IMAGE_BITMAP, getBytes(bitmap));

       String whereClause = IMAGE_URL + "=\"" + url.toString()+ "\";";
       bd.update(RedditDBHelper.TABLE_NAME, values, whereClause, null);
   }

    // Dado un url trae el bitmap correspondiente al mismo.
    public static Bitmap get_image(SQLiteDatabase bd, URL url) {
        Bitmap result = null;
        String whereClause = IMAGE_URL + "= \"" + url.toString()+ "\"";
        Cursor c = bd.rawQuery(" SELECT * FROM " + TABLE_NAME + " WHERE " + whereClause +";", null);
        if (!c.moveToFirst()) {
            c.close();
            return null;
        }
        if(c.isNull(c.getColumnIndex(IMAGE_BITMAP)))
            return null;
        result = getImage(c.getBlob(c.getColumnIndex(IMAGE_BITMAP)));
        c.close();
        return result;
    }

}

