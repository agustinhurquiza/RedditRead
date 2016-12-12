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
    private int nextPostTop = -1;
    private int nextPostHot = -1;
    private int nextPostNew = -1;
    private boolean flagTop = false;
    private boolean flagHot = false;
    private boolean flagNew = false;
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
                            if(type == "top")
                                nextPostTop = QUANTYTI;
                            else if (type == "new")
                                nextPostNew = QUANTYTI;
                            else
                                nextPostHot = QUANTYTI;
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
            if(type == "top")
                nextPostTop = QUANTYTI;
            else if (type == "new")
                nextPostNew = QUANTYTI;
            else
                nextPostHot = QUANTYTI;

        }
    }


    public void getNextPosts(final PostsIteratorListener listener, Context context, boolean internet,
                             String type) throws MalformedURLException {
        final  RedditDBHelper bdHelper = new RedditDBHelper(context, DATABASE_VERSION);
        SQLiteDatabase db = bdHelper.getReadableDatabase();
        int nextPost = -1;
        boolean flag = false;
        if(type == "top") {
            flag = flagTop;
            nextPost = nextPostTop;
            nextPostHot = 0;
            nextPostNew = 0;
        } else if (type == "new") {
            flag = flagNew;
            nextPost = nextPostNew;
            nextPostHot = 0;
            nextPostTop = 0;
        } else if(type == "hot"){
            flag = flagHot;
            nextPost = nextPostHot;
            nextPostNew = 0;
            nextPostTop = 0;
        }
        if (!flag) {
            getTopPosts(listener, internet, context, type);
            if(type == "top")
                flagTop = true;
            else if (type == "new")
                flagNew = true;
            else if(type == "hot")
                flagHot = true;

        } else {
            if(nextPost == 0){
                List<PostModel> list = Querys.getPosts(db, 0, type);
                Listing listing = new Listing("0","0",list);
                listener.setAdapter(list);
                if(type == "top")
                    nextPostTop = QUANTYTI;
                else if (type == "new")
                    nextPostNew = QUANTYTI;
                else
                    nextPostHot = QUANTYTI;
            }
            listener.nextPosts(Querys.getPosts(db, nextPost, type));
            if(type == "top")
                nextPostTop += QUANTYTI;
            else if (type == "new")
                nextPostNew += QUANTYTI;
            else
                nextPostHot += QUANTYTI;
        }
    }
}
