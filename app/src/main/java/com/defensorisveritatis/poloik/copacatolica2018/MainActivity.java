package com.defensorisveritatis.poloik.copacatolica2018;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.defensorisveritatis.poloik.copacatolica2018.matches.MatchesFragment;
import com.defensorisveritatis.poloik.copacatolica2018.news.NewsFragment;
import com.defensorisveritatis.poloik.copacatolica2018.teams.TeamsFragment;

public class MainActivity extends AppCompatActivity {

    private SectionPageAdapter mSectionAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SectionPageAdapter mSectionAdapter = new SectionPageAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    public void setupViewPager(ViewPager viewPager){
        SectionPageAdapter adapter = new SectionPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new NewsFragment(), "News");
        adapter.addFragment(new TeamsFragment(), "Teams");
        adapter.addFragment(new MatchesFragment(), "Matches");

        viewPager.setAdapter(adapter);
    }

}
