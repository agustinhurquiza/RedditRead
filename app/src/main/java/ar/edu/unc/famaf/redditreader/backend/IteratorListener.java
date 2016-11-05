package ar.edu.unc.famaf.redditreader.backend;

import ar.edu.unc.famaf.redditreader.model.Listing;


public interface IteratorListener {
    public void getNextPost(Listing listing);
    public void error();
}
