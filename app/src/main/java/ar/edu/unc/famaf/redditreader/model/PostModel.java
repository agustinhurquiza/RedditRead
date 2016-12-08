package ar.edu.unc.famaf.redditreader.model;


import java.io.Serializable;
import java.net.URL;


public class PostModel implements Serializable {
    private String mTitle;
    private String mAuthor;
    private long mDate;
    private String mSub;
    private int mNumberOfComments;
    private URL mImage;
    private URL mUrlPage;
    private String mtype;

    public String getMtype() {
        return mtype;
    }

    public void setMtype(String mtype) {
        this.mtype = mtype;
    }

    public String getmPostHint() {
        return mPostHint;
    }

    public void setmPostHint(String mPostHint) {
        this.mPostHint = mPostHint;
    }

    private String mPostHint;

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

    public long getmDate() {
        return mDate;
    }

    public void setmDate(long mDate) {
        this.mDate = mDate;
    }

    public String getmSub() {
        return mSub;
    }

    public void setmSub(String mSub) {
        this.mSub = mSub;
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

    public URL getmUrlPage() {
        return mUrlPage;
    }

    public void setmUrlPage(URL mUrlPage) {
        this.mUrlPage = mUrlPage;
    }

    public PostModel(String mTitle, URL mUrlPage, URL mImage, int mNumberOfComments, String mSub,
                     long mDate, String mAuthor, String mPostHint, String mtype) {
        this.mTitle = mTitle;
        this.mUrlPage = mUrlPage;
        this.mImage = mImage;
        this.mNumberOfComments = mNumberOfComments;
        this.mSub = mSub;
        this.mDate = mDate;
        this.mAuthor = mAuthor;
        this.mPostHint = mPostHint;
        this.mtype = mtype;
    }


}
