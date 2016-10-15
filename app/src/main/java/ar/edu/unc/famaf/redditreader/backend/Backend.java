package ar.edu.unc.famaf.redditreader.backend;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import ar.edu.unc.famaf.redditreader.R;
import ar.edu.unc.famaf.redditreader.model.PostModel;

public class Backend {
    private static Backend ourInstance = new Backend();

    public static Backend getInstance() {
        return ourInstance;
    }

    private Backend() {
    }

    public List<PostModel> getTopPosts() throws MalformedURLException {
        List<PostModel> myList = new ArrayList<>();
        URL[] url = new URL[10];
        url[0] = new URL("https://i.reddituploads.com/76f89cf1fe5e4a73852e74f5b96afbf1?fit=max&h=1536&w=1536&s=4d4fea8f9414e18bc63b69c3f0b34e71");
        url[1] = new URL("https://i.redd.1o4ylhrx.png");
        url[2] = new URL("https://b.thumbs.redditmedia.com/YOSWC3zoPPXzxD15hVKhBlcUdP5QrkccryNtkZia7Gg.jpg");
        url[3] = new URL("http://i.imgur.com/VJ0mSIH.jpg");
        url[4] = new URL("https://b.thumbs.redditmedia.com/JZUrEDtmUD-grkiTYaeBNagRv0nkJUfIZirHqdPdNxI.jpg");
        url[5] = new URL("https://b.thumbs.redditmedia.com/aMCmKcyNmZ_i2L4QLu1PREhwJIXNTt-LpJRUfWh5mec.jpg");
        url[6] = new URL("https://b.thumbs.redditmedia.com/4UKdyTfXzsIWZ4EzQtM0Ed6qYx-72cKeAgIHjPjkklA.jpg");
        url[7] = new URL("https://b.thumbs.redditmedia.com/SiXXW80AzrS9EMhMxJ_TsCdaMYAxF9BQAj0z3pUlVFU.jpg");
        url[8] = new URL("https://a.thumbs.redditmedia.com/QlPHfNHLVhnsvrythkiN_iPStOLHT11IZs9dGhwqbu0.jpg");
        url[9] = new URL("https://b.thumbs.redditmedia.com/H2taiYYPOhm93x3zkf2ogmylK4agKggWubpsIzNSFqo.jpg");
        url[9] = new URL("https://b.thumbs.redditmedia.com/h74JWprM3wljpdBOOpKDxt5sdZWPRtJBVULIobFfCBU.jpg");
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2016);
        cal.set(Calendar.MONTH, 9);
        cal.set(Calendar.DAY_OF_MONTH, 14);
        cal.set(Calendar.HOUR_OF_DAY, 21);
        cal.set(Calendar.MINUTE,33);
        java.util.Date fecha = cal.getTime();
        PostModel pos1 = new PostModel("14 year old floof still looks like a pup" ,"Lester_Knopf", fecha,
                "i.reddituploads.com" , 478, url[0]);


        cal.set(Calendar.YEAR, 2016);
        cal.set(Calendar.MONTH, 9);
        cal.set(Calendar.DAY_OF_MONTH, 14);
        cal.set(Calendar.HOUR_OF_DAY, 15);
        cal.set(Calendar.MINUTE,33);;
        fecha = cal.getTime();
        PostModel pos2 = new PostModel("A natural response." ,"BaconCat42", fecha, "i.redd.it", 835,
                url[1]);

        cal.set(Calendar.YEAR, 2016);
        cal.set(Calendar.MONTH, 9);
        cal.set(Calendar.DAY_OF_MONTH, 14);
        cal.set(Calendar.HOUR_OF_DAY, 10);
        cal.set(Calendar.MINUTE,25);
        fecha = cal.getTime();
        PostModel pos3 = new PostModel("I installed a beer and soda tap in my kitchen ",
                                       "averagejones", fecha, "imgur.com", 639, url[2]);

        cal.set(Calendar.YEAR, 2016);
        cal.set(Calendar.MONTH, 9);
        cal.set(Calendar.DAY_OF_MONTH, 15);
        cal.set(Calendar.HOUR_OF_DAY, 8);
        cal.set(Calendar.MINUTE,12);;
        fecha = cal.getTime();
        PostModel pos4 = new PostModel("In Israel, if you are in the active military you must carry your weapon with you at all times",
                                       "FishermansDick", fecha, "i.imgur.com", 4449, url[3]);

        cal.set(Calendar.YEAR, 2016);
        cal.set(Calendar.MONTH, 9);
        cal.set(Calendar.DAY_OF_MONTH, 9);
        cal.set(Calendar.HOUR_OF_DAY, 18);
        cal.set(Calendar.MINUTE,0);
        fecha = cal.getTime();
        PostModel pos5 = new PostModel("Painted a picture of my cat", "mydoglixu", fecha, "/r/mildlyinteresting"
                , 348, url[4]);


        cal.set(Calendar.YEAR, 2016);
        cal.set(Calendar.MONTH, 9);
        cal.set(Calendar.DAY_OF_MONTH, 14);
        cal.set(Calendar.HOUR_OF_DAY, 21);
        cal.set(Calendar.MINUTE,33);
        fecha = cal.getTime();
        PostModel pos6 = new PostModel("14 year old floof still looks like a pup" ,"Lester_Knopf", fecha,
                "i.reddituploads.com" , 478, url[5]);


        cal.set(Calendar.YEAR, 2016);
        cal.set(Calendar.MONTH, 9);
        cal.set(Calendar.DAY_OF_MONTH, 14);
        cal.set(Calendar.HOUR_OF_DAY, 15);
        cal.set(Calendar.MINUTE,33);;
        fecha = cal.getTime();
        PostModel pos7 = new PostModel("A natural response." ,"BaconCat42", fecha, "i.redd.it", 835,
                url[6]);

        cal.set(Calendar.YEAR, 2016);
        cal.set(Calendar.MONTH, 9);
        cal.set(Calendar.DAY_OF_MONTH, 14);
        cal.set(Calendar.HOUR_OF_DAY, 10);
        cal.set(Calendar.MINUTE,25);
        fecha = cal.getTime();
        PostModel pos8 = new PostModel("I installed a beer and soda tap in my kitchen ",
                "averagejones", fecha, "imgur.com", 639, url[7]);

        cal.set(Calendar.YEAR, 2016);
        cal.set(Calendar.MONTH, 9);
        cal.set(Calendar.DAY_OF_MONTH, 15);
        cal.set(Calendar.HOUR_OF_DAY, 8);
        cal.set(Calendar.MINUTE,12);;
        fecha = cal.getTime();
        PostModel pos9 = new PostModel("In Israel, if you are in the active military you must carry your weapon with you at all times",
                "FishermansDick", fecha, "i.imgur.com", 4449, url[8]);

        cal.set(Calendar.YEAR, 2016);
        cal.set(Calendar.MONTH, 9);cal.set(Calendar.DAY_OF_MONTH, 9);
        cal.set(Calendar.HOUR_OF_DAY, 18);
        cal.set(Calendar.MINUTE,0);
        fecha = cal.getTime();
        PostModel pos10 = new PostModel("Painted a picture of my cat", "mydoglixu", fecha, "/r/mildlyinteresting"
                , 348, url[9]);


        myList.add(pos1);
        myList.add(pos2);
        myList.add(pos3);
        myList.add(pos4);
        myList.add(pos5);
        myList.add(pos6);
        myList.add(pos7);
        myList.add(pos8);
        myList.add(pos9);
        myList.add(pos10);

        return myList;
    }
}
