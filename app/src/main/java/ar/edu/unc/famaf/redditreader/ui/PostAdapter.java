package ar.edu.unc.famaf.redditreader.ui;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import ar.edu.unc.famaf.redditreader.R;
import ar.edu.unc.famaf.redditreader.backend.Querys;
import ar.edu.unc.famaf.redditreader.backend.RedditDBHelper;
import ar.edu.unc.famaf.redditreader.model.PostModel;

import static ar.edu.unc.famaf.redditreader.backend.Querys.get_image;
import static ar.edu.unc.famaf.redditreader.backend.RedditDBHelper.DATABASE_VERSION;

/**
 * Created by agustin on 10/10/16.
 */

public class PostAdapter extends android.widget.ArrayAdapter<PostModel> {

   private class ViewHolder{
       public TextView Title;
       public TextView Author;
       public TextView Date;
       public TextView Sub;
       public TextView Comments;
       public ImageView Image;
       public ProgressBar Progress;
   }



    private List<PostModel> myPostModelList;

    public PostAdapter(Context context, int resourse, List<PostModel> pos){
        super(context, resourse);
        myPostModelList = pos;
    }

    @Override
    public int getCount() {
        return myPostModelList.size();
    }

    @Override
    public PostModel getItem(int position) {
        return myPostModelList.get(position);
    }

    @Override
    public int getPosition(PostModel item) {
        return myPostModelList.indexOf(item);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null) {
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.list_row, parent, false);
        }

        if(convertView.getTag() == null){
            viewHolder = new ViewHolder();
            viewHolder.Title = (TextView) convertView.findViewById(R.id.Title);
            viewHolder.Author = (TextView) convertView.findViewById(R.id.author);
            viewHolder.Image = (ImageView) convertView.findViewById(R.id.image);
            viewHolder.Progress = (ProgressBar) convertView.findViewById(R.id.progress);
            viewHolder.Comments = (TextView) convertView.findViewById(R.id.comentarios);
            viewHolder.Date = (TextView) convertView.findViewById(R.id.date);
            viewHolder.Sub = (TextView) convertView.findViewById(R.id.sub);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.Image.setVisibility(View.GONE);
        viewHolder.Progress.setVisibility(View.VISIBLE);
        DowloadImagenAsyncTaks dw = new DowloadImagenAsyncTaks(getContext());
        dw.setImageView(viewHolder.Image, viewHolder.Progress);
        PostModel postModel = myPostModelList.get(position);

        viewHolder.Title.setText(postModel.getmTitle());
        viewHolder.Author.setText(postModel.getmAuthor());
        viewHolder.Comments.setText(toComent((postModel.getmNumberOfComments())));

        final RedditDBHelper bdHelper = new RedditDBHelper(getContext(), DATABASE_VERSION);
        final SQLiteDatabase dbW = bdHelper.getWritableDatabase();
        URL[] urls = new URL[1];
        urls[0] = postModel.getmImage();
        Bitmap bitmap = get_image(dbW, urls[0]);
        if(bitmap == null)
            dw.execute(urls);
        else{
            viewHolder.Image.setVisibility(View.VISIBLE);
            viewHolder.Progress.setVisibility(View.GONE);
            viewHolder.Image.setImageBitmap(bitmap);
        }
        long now = System.currentTimeMillis();
        CharSequence relativeTime = DateUtils.getRelativeTimeSpanString(postModel.getmDate(), now, DateUtils.MINUTE_IN_MILLIS);
        viewHolder.Date.setText(relativeTime);
        viewHolder.Sub.setText(postModel.getmSub());


        return convertView;

    }


    public String toComent(int comm){
        return Integer.toString(comm) + "  Comentarios";
    }


    @Override
    public boolean isEmpty() {
        return myPostModelList.isEmpty();
    }

    protected class DowloadImagenAsyncTaks extends AsyncTask<URL, Integer, Bitmap>{

        ImageView mImageView;
        ProgressBar mProgressBar;
        Context mContext = null;
        URL mUrl = null;

        public void setImageView(ImageView imageView, ProgressBar progressBar) {
            mImageView = imageView;
            mProgressBar = progressBar;
        }

        public DowloadImagenAsyncTaks(Context context) {
            mContext = context;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected Bitmap doInBackground(URL... params) {
            URL url = params[0];
            mUrl = params[0];
            Bitmap bitmap = null;
            HttpURLConnection connection = null;

            if (params[0] == null)
                return null;
            try {
                connection = (HttpURLConnection) url.openConnection();
                InputStream is = connection.getInputStream();
                bitmap = BitmapFactory.decodeStream(is, null, null);
            } catch (IOException e) {
                return null;
            }

            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            mProgressBar.setVisibility(View.GONE);
            mImageView.setVisibility(View.VISIBLE);
            final RedditDBHelper bdHelper = new RedditDBHelper(mContext, DATABASE_VERSION);
            final SQLiteDatabase dbW = bdHelper.getWritableDatabase();
            if(bitmap == null) {
                mImageView.setImageResource(R.mipmap.ic_launcher);
            } else{
                mImageView.setImageBitmap(bitmap);
                Querys.add_image(dbW, bitmap, mUrl);
            }
        }
    }


}

