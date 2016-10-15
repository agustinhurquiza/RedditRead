package ar.edu.unc.famaf.redditreader.model;


import android.widget.TextView;

import org.w3c.dom.Text;

import java.net.URL;
import java.util.Date;
import java.util.StringTokenizer;

public class PostModel {
    private String mTitle;
    private String mAuthor;
    private Date mDate;
    private String mSub;
    private int mNumberOfComments;
    private URL mImage;

    public PostModel(String mTitle, String mAuthor, Date mDate, String mSub,
                     int mNumberOfComments, URL mImage) {
        this.mTitle = mTitle;
        this.mAuthor = mAuthor;
        this.mDate = mDate;
        this.mSub = mSub;
        this.mNumberOfComments = mNumberOfComments;
        this.mImage = mImage;
    }

    public String getmSub() {
        return mSub;
    }

    public void setmSub(String mSub) {
        this.mSub = mSub;
    }

    public PostModel(String mSub) {
        this.mSub = mSub;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmAuthor() {
        return mAuthor;
    }

    public void setmAuthor(String mAuthor) {
        this.mAuthor = mAuthor;
    }

    public Date getmDate() {
        return mDate;
    }

    public void setmDate(Date mDate) {
        this.mDate = mDate;
    }

    public int getmNumberOfComments() {
        return mNumberOfComments;
    }

    public void setmNumberOfComments(int mNumberOfComments) {
        this.mNumberOfComments = mNumberOfComments;
    }

    public URL getmImage() {
        return mImage;
    }

    public void setmImage(URL mImage) {
        this.mImage = mImage;
    }
}
