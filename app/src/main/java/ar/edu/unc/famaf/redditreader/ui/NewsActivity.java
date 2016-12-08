package ar.edu.unc.famaf.redditreader.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TabHost;

import ar.edu.unc.famaf.redditreader.R;
import ar.edu.unc.famaf.redditreader.model.PostModel;


public class NewsActivity extends AppCompatActivity  implements NewsActivityFragment.OnPostItemSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TabHost tabHost = (TabHost)findViewById(R.id.tabHost);
        tabHost.setup();

        //Top
        TabHost.TabSpec spec = tabHost.newTabSpec("Top");
        spec.setContent(R.id.host_top);
        spec.setIndicator("Top");
        tabHost.addTab(spec);

        //New
        spec = tabHost.newTabSpec("New");
        spec.setContent(R.id.host_new);
        spec.setIndicator("New");
        tabHost.addTab(spec);


        //Hot
        spec = tabHost.newTabSpec("Hot");
        spec.setContent(R.id.host_hot);
        spec.setIndicator("Hot");
        tabHost.addTab(spec);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_news, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPostItemPicked(PostModel post) {
        Intent intent = new Intent(this, NewsDetailActivity.class);
        intent.putExtra("post", post);
        startActivity(intent);
    }


}
