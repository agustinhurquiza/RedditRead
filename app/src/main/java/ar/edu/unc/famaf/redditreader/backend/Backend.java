package ar.edu.unc.famaf.redditreader.backend;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import ar.edu.unc.famaf.redditreader.R;
import ar.edu.unc.famaf.redditreader.model.Listing;
import ar.edu.unc.famaf.redditreader.model.PostModel;

public class Backend {
    private static Backend ourInstance = new Backend();

    public static Backend getInstance() {
        return ourInstance;
    }


    private Backend() {

    }

    public void getTopPosts(final IteratorListener iteratorListener) {
        URL[] urls = new URL[1];
        try {
            urls[0] = new URL("https://www.reddit.com/top/.json?limit=50");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        new GetTopPostsTask() {
            @Override
            protected void onPostExecute(Listing listing) {
                iteratorListener.getNextPost(listing);
            }
        }.execute(urls);
    }
}
