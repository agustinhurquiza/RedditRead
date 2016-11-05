package ar.edu.unc.famaf.redditreader.ui;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


import ar.edu.unc.famaf.redditreader.R;
import ar.edu.unc.famaf.redditreader.backend.Backend;
import ar.edu.unc.famaf.redditreader.backend.IteratorListener;
import ar.edu.unc.famaf.redditreader.backend.RedditDBHelper;
import ar.edu.unc.famaf.redditreader.model.Listing;

import static ar.edu.unc.famaf.redditreader.backend.RedditDBHelper.DATABASE_VERSION;


/**
 * A placeholder fragment containing a simple view.
 */
public class NewsActivityFragment extends Fragment implements IteratorListener {
    ListView list = null;

    private boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public NewsActivityFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        boolean  internet = isOnline();
        View rootview = inflater.inflate(R.layout.fragment_news, container, false);
        list = (ListView) rootview.findViewById(R.id.post);
            Backend backend = Backend.getInstance();
            backend.getTopPosts(this, internet, getContext());

        return rootview;
    }

    @Override
    public void getNextPost(Listing listing) {
        PostAdapter adp = new PostAdapter(getContext(), R.layout.list_row, listing.getPosts());
        list.setAdapter(adp);
    }
}
