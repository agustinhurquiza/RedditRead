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
    private int nextPost = -1;
    private static final int  QUANTYTI = 5;
    private static Backend ourInstance = new Backend();

    public static Backend getInstance() {
        return ourInstance;
    }


    private Backend() {

    }

    public void getTopPosts(final PostsIteratorListener listener, boolean internet, Context context,
                            final String type) throws MalformedURLException {
        URL[] urls = new URL[1];
        final RedditDBHelper bdHelper = new RedditDBHelper(context, DATABASE_VERSION);
        final SQLiteDatabase dbR = bdHelper.getReadableDatabase();
        final SQLiteDatabase dbW = bdHelper.getWritableDatabase();
        final String mtype = type;
        if (internet) {
            try {
                if(type == "top")
                    urls[0] = new URL("https://www.reddit.com/top/.json?limit=50");
                else if(type == "new")
                    urls[0] = new URL("https://www.reddit.com/new/.json?limit=50");
                else
                    urls[0] = new URL("https://www.reddit.com/hot/.json?limit=50");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            new GetTopPostsTask() {

                @Override
                protected void onPreExecute() {
                    setType(mtype);
                }

                @Override
                protected void onPostExecute(Listing listing) {
                    if (listing != null)
                        try {
                            Querys.delete_posts(dbW, type);
                            Querys.insert_posts(dbW, listing);
                            List<PostModel> list = Querys.getPosts(dbR, 0, mtype);
                            nextPost = QUANTYTI;
                            listing.setPosts(list);
                            listener.setAdapter(list);
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                    else {
                        listener.error();
                    }
                }

            }.execute(urls);

        } else {
            List<PostModel> list = Querys.getPosts(dbR, 0, type);
            Listing listing = new Listing("0","0",list);
            listener.setAdapter(list);
            nextPost = QUANTYTI;

        }
    }


    public void getNextPosts(final PostsIteratorListener listener, Context context, boolean internet,
                             String type) throws MalformedURLException {
        final  RedditDBHelper bdHelper = new RedditDBHelper(context, DATABASE_VERSION);
        SQLiteDatabase db = bdHelper.getReadableDatabase();

        if (nextPost == -1) {
            getTopPosts(listener, internet, context, type);
        } else {
            listener.nextPosts(Querys.getPosts(db, nextPost, type));
            nextPost += QUANTYTI;
        }
    }
}
