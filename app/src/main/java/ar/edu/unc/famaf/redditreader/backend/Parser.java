package ar.edu.unc.famaf.redditreader.backend;

import android.util.JsonReader;
import android.util.JsonToken;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ar.edu.unc.famaf.redditreader.model.Listing;
import ar.edu.unc.famaf.redditreader.model.PostModel;


public class Parser {

    public Listing readJsonStream(InputStream in) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        Listing list ;
        try {
            list = readListingArray(reader);
        } catch (IOException e){
            reader.close();
            throw new IOException(e);
        }
        return list;
    }

    private Listing readListingArray(JsonReader reader) throws IOException {
        List<PostModel> children = null;
        String name;
        String before = "";
        String after = "";
        try {
            reader.beginObject();
            while (reader.hasNext()) {
                name = reader.nextName();
                if(name.equals("data")) {
                    reader.beginObject();
                    break;
                }
                else
                    reader.skipValue();
            }
            while (reader.hasNext()) {
                name = reader.nextName();
                switch (name) {
                    case "children":
                        children = readChildren(reader);
                        break;
                    case "after":
                        after = reader.nextString();
                        break;
                    case "before":
                        if (reader.peek() != JsonToken.NULL)
                            before = reader.nextString();
                        else
                            reader.nextNull();
                        break;
                    default:
                        reader.skipValue();
                        break;
                }
            }
            reader.endObject();
            reader.endObject();
            return new Listing(after, before, children);

        } catch (IOException e) {
            throw new IOException(e);
        }
    }


    private List<PostModel> readChildren(JsonReader reader)  throws IOException {
        ArrayList<PostModel> children = new ArrayList<PostModel>();
        PostModel post= null;
        try {
            reader.beginArray();
            while (reader.hasNext()){
                post = extractPost(reader);
                children.add(post);
            }
            reader.endArray();
            return children;
        } catch (IOException e) {
            throw new IOException(e);
        }
    }

    private PostModel extractPost(JsonReader reader)throws IOException {
        String line;
        String title = null;
        String author = null;
        Long date = null;
        String sub = null;
        int numberOfComments = 0;
        URL image = null;
        try {
            reader.beginObject();
            while (reader.hasNext()) {
                line = reader.nextName();
                if(line.equals("data")) {
                    reader.beginObject();
                    break;
                }
                else
                    reader.skipValue();
            }
            while (reader.hasNext()){
                line = reader.nextName();
                switch (line) {
                    case "title":
                        title = reader.nextString();
                        break;
                    case "author":
                        author = reader.nextString();
                        break;
                    case "created_utc":
                        long fse = reader.nextLong();
                        fse = fse*1000;
                        date = fse;
                        break;
                    case "subreddit":
                        sub = reader.nextString();
                        break;
                    case "num_comments":
                        numberOfComments = reader.nextInt();
                        break;
                    case "thumbnail":
                        try {
                            image = new URL(reader.nextString());
                        } catch (MalformedURLException e){
                            image = null;
                        }

                        break;
                    default:
                        reader.skipValue();
                        break;
                }
            }
            reader.endObject();
            reader.endObject();
            return new PostModel(title,author, date,sub,numberOfComments,image);
        } catch (IOException e) {
            throw new IOException(e);
        }
    }
}

