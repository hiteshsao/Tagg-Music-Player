package gg.joshbra.tagg.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;

import java.util.Observable;

import gg.joshbra.tagg.Helpers.AboutDialogGenerator;
import gg.joshbra.tagg.Helpers.CurrentPlaybackNotifier;
import gg.joshbra.tagg.Fragments.NowPlayingBarFragment;
import gg.joshbra.tagg.Fragments.SongListFragment;
import gg.joshbra.tagg.Helpers.MediaControllerHolder;
import gg.joshbra.tagg.R;
import gg.joshbra.tagg.Comparators.SongComparator;
import gg.joshbra.tagg.SongManager;

public class RecentlyAddedActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;

    private SongListFragment songListFragment;
    private NowPlayingBarFragment nowPlayingBarFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recently_added);

        mDrawerLayout = findViewById(R.id.drawer);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle("Recently Added");

        NavigationView navigationView = findViewById(R.id.navView);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);

        songListFragment = (SongListFragment) getSupportFragmentManager().findFragmentById(R.id.songList);
        songListFragment.initRecycler(SongManager.getSelf().getDateSortedSongs(SongComparator.SORT_DATE_DESC));

        nowPlayingBarFragment = (NowPlayingBarFragment) getSupportFragmentManager().findFragmentById(R.id.nowPlayingBar);
        nowPlayingBarFragment.initNowPlayingBar();
        nowPlayingBarFragment.update(new Observable(), MediaControllerHolder.getMediaController().getPlaybackState());
        nowPlayingBarFragment.update(new Observable(), MediaControllerHolder.getMediaController().getMetadata());
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.songMenu) {
            item.setChecked(true);
            //Toast.makeText(this, "Songs", Toast.LENGTH_LONG).show();

            Intent intent = new Intent(this, gg.joshbra.tagg.Activities.MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
        } else if (id == R.id.taggMenu) {
            item.setChecked(true);
            //Toast.makeText(this, "Taggs", Toast.LENGTH_LONG).show();

            Intent intent = new Intent(this, TaggActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
        } else if (id == R.id.recentMenu) {
            item.setChecked(true);
            //Toast.makeText(this, "Recently Added", Toast.LENGTH_LONG).show();
        } else if (id == R.id.aboutMenu) {
            AboutDialogGenerator.createDialog(this);
        }

        mDrawerLayout.closeDrawer(Gravity.START);

        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ((NavigationView)findViewById(R.id.navView)).getMenu().getItem(2).setChecked(true);
        CurrentPlaybackNotifier.getSelf().notifyPlaybackStateChanged(MediaControllerHolder.getMediaController().getPlaybackState());
    }
}