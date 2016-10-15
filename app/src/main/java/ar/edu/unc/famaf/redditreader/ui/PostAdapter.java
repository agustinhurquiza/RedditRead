package ar.edu.unc.famaf.redditreader.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.List;

import ar.edu.unc.famaf.redditreader.R;
import ar.edu.unc.famaf.redditreader.model.PostModel;

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
        DowloadImagenAsyncTaks dw = new DowloadImagenAsyncTaks();
        dw.setImageView(viewHolder.Image, viewHolder.Progress);
        PostModel postModel = myPostModelList.get(position);

        viewHolder.Title.setText(postModel.getmTitle());
        viewHolder.Author.setText(postModel.getmAuthor());
        viewHolder.Comments.setText(toComent((postModel.getmNumberOfComments())));
        URL[] urls = new URL[1];
        urls[0] = postModel.getmImage();
        dw.execute(urls);
        viewHolder.Date.setText(toDate(postModel.getmDate()));
        viewHolder.Sub.setText(postModel.getmSub());


        return convertView;

    }


    public String toComent(int comm){
        return Integer.toString(comm) + "  Comentarios";
    }

    public String toDate(Date date){
        Date horaActual = new Date();
        long different = horaActual.getTime() - date.getTime();

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;

        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;

        long elapsedSeconds = different / secondsInMilli;
        if(elapsedDays >= 1)
            return ("Hace " + Integer.toString((int) elapsedDays) + " dias");
            else if(elapsedHours < 24 && elapsedHours >= 1)
            return ("Hace " + Integer.toString((int) elapsedHours) + " Horas");
        else if(elapsedMinutes < 60 && elapsedMinutes >= 1)
            return ("Hace " + Integer.toString((int) elapsedMinutes) + " Minutos");
        else
            return ("Hace " + Integer.toString((int) elapsedSeconds) + " Segundos");
    }

    @Override
    public boolean isEmpty() {
        return myPostModelList.isEmpty();
    }

    protected class DowloadImagenAsyncTaks extends AsyncTask<URL, Integer, Bitmap>{

        ImageView mImageView;
        ProgressBar mProgressBar;
        public void setImageView(ImageView imageView, ProgressBar progressBar) {
            mImageView = imageView;
            mProgressBar = progressBar;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
    protected Bitmap doInBackground(URL... params) {
            URL url = params[0];
            Bitmap bitmap = null;
            HttpURLConnection connection = null;
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
            if(bitmap == null)
                mImageView.setImageResource(R.mipmap.ic_launcher);
            else
                mImageView.setImageBitmap(bitmap);
        }
    }


}

