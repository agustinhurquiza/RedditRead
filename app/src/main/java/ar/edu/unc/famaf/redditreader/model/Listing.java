package ar.edu.unc.famaf.redditreader.model;

import java.util.List;

/**
 * Created by agustin on 27/10/16.
 */
public class Listing {
    private String after;
    private String before;
    private List<PostModel> posts;
    public Listing(String after, String before, List<PostModel> posts) {
        this.after = after;
        this.before = before;
        this.posts = posts;
    }

    public String getAfter() {
        return after;
    }

    public String getBefore() {
        return before;
    }

    public List<PostModel> getPosts() {
        return posts;
    }


    public void setAfter(String after) {
        this.after = after;
    }

    public void setBefore(String before) {
        this.before = before;
    }

    public void setPosts(List<PostModel> posts) {
        this.posts = posts;
    }
}