package ar.edu.unc.famaf.redditreader.backend;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import ar.edu.unc.famaf.redditreader.model.Listing;
import ar.edu.unc.famaf.redditreader.model.PostModel;

import static ar.edu.unc.famaf.redditreader.backend.RedditDBHelper.DATABASE_VERSION;

public class Backend {
    private static Backend ourInstance = new Backend();

    public static Backend getInstance() {
        return ourInstance;
    }


    private Backend() {

    }

    public void getTopPosts(final IteratorListener iteratorListener, boolean internet, Context context) {
        URL[] urls = new URL[1];
        final  RedditDBHelper bdHelper = new RedditDBHelper(context, DATABASE_VERSION);
        final SQLiteDatabase dbR = bdHelper.getReadableDatabase();
        final SQLiteDatabase dbW = bdHelper.getWritableDatabase();
        if(internet) {
            try {
                urls[0] = new URL("https://www.reddit.com/top/.json?limit=50");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            new GetTopPostsTask() {
                @Override
                protected void onPostExecute(Listing listing) {
                    try {
                        Querys.delete_posts(dbW);
                        Querys.insert(dbW, listing);
                        List<PostModel> list = Querys.getPost(dbR);
                        listing.setPosts(list);
                        iteratorListener.getNextPost(listing);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                }

            }.execute(urls);

        }else {
            try {
                List<PostModel> list = Querys.getPost(dbR);
                Listing listing = new Listing("0","0",list);
                iteratorListener.getNextPost(listing);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
    }
}
