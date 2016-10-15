package ar.edu.unc.famaf.redditreader.ui;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.net.MalformedURLException;
import java.util.List;

import ar.edu.unc.famaf.redditreader.R;
import ar.edu.unc.famaf.redditreader.backend.Backend;
import ar.edu.unc.famaf.redditreader.model.PostModel;


/**
 * A placeholder fragment containing a simple view.
 */
public class NewsActivityFragment extends Fragment {

    public NewsActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        List list = null;
        View row = inflater.inflate(R.layout.fragment_news, container, false);
        ListView listw = (ListView) row.findViewById(R.id.post);
        try {
            list = Backend.getInstance().getTopPosts();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        PostAdapter adp = new PostAdapter(getContext(), R.layout.list_row, list);
        listw.setAdapter(adp);
        return row;
    }
}
