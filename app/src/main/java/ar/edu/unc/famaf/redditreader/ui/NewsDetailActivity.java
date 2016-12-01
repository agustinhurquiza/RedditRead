package ar.edu.unc.famaf.redditreader.ui;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ar.edu.unc.famaf.redditreader.R;

public class NewsDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        Intent intent = getIntent();
        Bundle bundle = new Bundle();
        bundle.putSerializable("post", intent.getSerializableExtra("post"));
        NewsDetailActivityFragment activityFragment = new NewsDetailActivityFragment();
        activityFragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction().add(R.id.activity_news_detail, activityFragment).commit();

    }
}
