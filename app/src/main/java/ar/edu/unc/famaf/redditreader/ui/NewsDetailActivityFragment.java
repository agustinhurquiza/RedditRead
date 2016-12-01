package ar.edu.unc.famaf.redditreader.ui;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import ar.edu.unc.famaf.redditreader.R;
import ar.edu.unc.famaf.redditreader.backend.RedditDBHelper;
import ar.edu.unc.famaf.redditreader.model.PostModel;

import static ar.edu.unc.famaf.redditreader.backend.Querys.get_image;
import static ar.edu.unc.famaf.redditreader.backend.RedditDBHelper.DATABASE_VERSION;


public class NewsDetailActivityFragment extends Fragment {



    private class DownloadImageAsyncTask extends AsyncTask<URL, Integer, Bitmap> {

        private ImageView mImageView;
        private ProgressBar mProgressBar;
        private String mUrl;


        public DownloadImageAsyncTask(ImageView imageView, ProgressBar progressBar) {
            this.mImageView = imageView;
            this.mProgressBar = progressBar;
        }

        @Override
        protected Bitmap doInBackground(URL... urls) {

            URL url = urls[0];
            mUrl = url.toString();
            Bitmap bitmap = null;
            HttpURLConnection connection = null;
            try {
                connection = (HttpURLConnection) url.openConnection();
                InputStream is = connection.getInputStream();
                bitmap = BitmapFactory.decodeStream(is, null, null);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            if (result == null) {
                mProgressBar.setVisibility(View.GONE);
                mImageView.setVisibility(View.VISIBLE);
                mImageView.setImageResource(R.mipmap.ic_launcher);
            } else {
                mProgressBar.setVisibility(View.GONE);
                mImageView.setVisibility(View.VISIBLE);
                mImageView.setImageBitmap(result);
            }
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_news_detail_activity, container, false);

        final PostModel post = (PostModel) getArguments().getSerializable("post");

        final TextView title = (TextView) rootview.findViewById(R.id.detail_title);
        title.setText(post.getmTitle());

        final TextView sub = (TextView) rootview.findViewById(R.id.detail_sub);
        sub.setText(post.getmSub());

        final TextView date = (TextView) rootview.findViewById(R.id.detail_date);
        long now = System.currentTimeMillis();
        CharSequence relativeTime = DateUtils.getRelativeTimeSpanString(post.getmDate(), now, DateUtils.MINUTE_IN_MILLIS);
        date.setText(relativeTime);

        final TextView author = (TextView) rootview.findViewById(R.id.detail_author);
        author.setText(post.getmAuthor());

        final RedditDBHelper bdHelper = new RedditDBHelper(getContext(), DATABASE_VERSION);
        final SQLiteDatabase dbW = bdHelper.getWritableDatabase();
        final ImageView image = (ImageView) rootview.findViewById(R.id.detail_image);
        ProgressBar progressBar = (ProgressBar) rootview.findViewById(R.id.progress_bar);
        URL[] urls = new URL[1];
        urls[0] = post.getmUrlPage();
        boolean flag = false;
        String post_hint = post.getmPostHint();
        if(post_hint != null && post_hint.equals("image"))
            flag = true;

        if(!flag){
            Bitmap bitmap = get_image(dbW, post.getmImage());
            if(bitmap == null){
                progressBar.setVisibility(View.GONE);
                image.setVisibility(View.VISIBLE);
                image.setImageResource(R.mipmap.ic_launcher);
            }
            else {
                progressBar.setVisibility(View.GONE);
                image.setVisibility(View.VISIBLE);
                image.setImageBitmap(bitmap);
            }

        } else {
            DownloadImageAsyncTask dw = new DownloadImageAsyncTask(image, progressBar);
            dw.execute(urls);
        }


        final Button button = (Button) rootview.findViewById(R.id.detail_button);
        final String url = post.getmUrlPage().toString();
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), WebViewActivity.class);
                intent.putExtra("url", url);
                startActivity(intent);
            }
        });

        if(flag) {
            button.setVisibility(View.GONE);
        }

        return rootview;
    }

}
