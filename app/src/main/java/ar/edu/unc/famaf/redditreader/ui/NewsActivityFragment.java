package ar.edu.unc.famaf.redditreader.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unc.famaf.redditreader.R;
import ar.edu.unc.famaf.redditreader.backend.Backend;
import ar.edu.unc.famaf.redditreader.backend.EndlessScrollListener;
import ar.edu.unc.famaf.redditreader.backend.PostsIteratorListener;
import ar.edu.unc.famaf.redditreader.backend.Querys;
import ar.edu.unc.famaf.redditreader.backend.RedditDBHelper;
import ar.edu.unc.famaf.redditreader.model.Listing;
import ar.edu.unc.famaf.redditreader.model.PostModel;

import static ar.edu.unc.famaf.redditreader.backend.RedditDBHelper.DATABASE_VERSION;


/**
 * A placeholder fragment containing a simple view.
 */
public class NewsActivityFragment extends Fragment implements PostsIteratorListener {
    ListView list = null;
    ArrayList postLst;
    PostAdapter adapter;

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
        final boolean internet = isOnline();
        View rootview = inflater.inflate(R.layout.fragment_news, container, false);
        final  RedditDBHelper bdHelper = new RedditDBHelper(getContext(), DATABASE_VERSION);
        final SQLiteDatabase db = bdHelper.getWritableDatabase();
        if (internet || Querys.is_empty(db)) {
            final Backend backend = Backend.getInstance();
            list = (ListView) rootview.findViewById(R.id.post);
            list.setOnScrollListener(new EndlessScrollListener() {
                @Override
                public boolean onLoadMore(int page, int totalItemsCount) {
                    try {
                        backend.getNextPosts(NewsActivityFragment.this, getContext(), internet);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    return true;
                }
            });

            try {
                backend.getNextPosts(this, getContext(), internet);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        else {
            new AlertDialog.Builder(getContext()).setMessage("Connection ERROR")
                    .setPositiveButton("Exit",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    ((Activity)getContext()).finish();
                                }}).show();
        }

        return rootview;
    }

    @Override
    public void nextPosts(List<PostModel> lst) {
        postLst.addAll(lst);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void setAdapter(List<PostModel> lst) {
        postLst = new ArrayList<>(lst);
        adapter = new PostAdapter(getContext(), R.layout.list_row, postLst);
        list.setAdapter(adapter);
    }

    @Override
    public void error() {
        new AlertDialog.Builder(getContext()).setMessage("Connection ERROR")
                .setPositiveButton("Exit",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                ((Activity)getContext()).finish();
                            }
                        })
                .show();

    }
}
