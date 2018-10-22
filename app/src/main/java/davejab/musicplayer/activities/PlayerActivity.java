package davejab.musicplayer.activities;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import davejab.musicplayer.R;
import davejab.musicplayer.main.Library;
import davejab.musicplayer.main.Player;
import davejab.musicplayer.models.Song;
import davejab.musicplayer.util.Time;

public class PlayerActivity extends Activity implements SeekBar.OnSeekBarChangeListener {

    private ImageButton btnPlay;
    private ImageButton btnNext;
    private ImageButton btnPrevious;
    private ImageButton btnRepeat;
    private ImageButton btnShuffle;
    private SeekBar songProgressBar;
    private TextView songTitleLabel;
    private TextView songCurrentDurationLabel;
    private TextView songTotalDurationLabel;
    // Media Player
//    private MediaPlayer mp;
//    private List<Song> songsList;
    // Handler to update UI timer, progress bar etc,.
    private Handler progressHandler;// = new Handler();
    //private SongsManager songManager;
    private Time time;
    private int seekForwardTime = 5000; // 5000 milliseconds
    private int seekBackwardTime = 5000; // 5000 milliseconds
    //private ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();


    private Player player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        // All player buttons
        btnPlay = (ImageButton) findViewById(R.id.btnPlay);
        btnNext = (ImageButton) findViewById(R.id.btnNext);
        btnPrevious = (ImageButton) findViewById(R.id.btnPrevious);
        btnRepeat = (ImageButton) findViewById(R.id.btnRepeat);
        btnShuffle = (ImageButton) findViewById(R.id.btnShuffle);
        songProgressBar = (SeekBar) findViewById(R.id.songProgressBar);
        //songTitleLabel = (TextView) findViewById(R.id.songTitle);
        songCurrentDurationLabel = (TextView) findViewById(R.id.songCurrentDurationLabel);
        songTotalDurationLabel = (TextView) findViewById(R.id.songTotalDurationLabel);

        songProgressBar.setOnSeekBarChangeListener(this);

        setTime(new Time());
        setProgressHandler(new Handler());

        setPlayer(Player.getPlayer());

        Intent intent = getIntent();
        int position = intent.getIntExtra("songIndex", 0);

        getPlayer().setPlaylist(Library.getLibrary(getContentResolver()).getCurrentList());
        getPlayer().setCurrentSongIndex(position);
        playSong(getPlayer().getCurrentSong());

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                getPlayer().next();
            }
        });
        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                getPlayer().previous();
            }
        });

        btnRepeat.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (getPlayer().toggleRepeat()){
                    //btnRepeat.setImageResource(R.drawable.btn_repeat_focused);
                    btnShuffle.setImageResource(R.drawable.btn_shuffle);
                } else {
                    btnRepeat.setImageResource(R.drawable.btn_repeat);
                }
            }
        });

        btnShuffle.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (getPlayer().toggleShuffle()){
                    //    btnShuffle.setImageResource(R.drawable.btn_shuffle_focused);
                    btnRepeat.setImageResource(R.drawable.btn_repeat);
                } else {
                    btnShuffle.setImageResource(R.drawable.btn_shuffle);
                }
            }
        });
    }

    public void playSong(Song song) {
        try {
            getPlayer().playSong(song);
            songProgressBar.setProgress(0);
            songProgressBar.setMax(100);
            updateProgressBar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateProgressBar() {
        getProgressHandler().postDelayed(progressUpdater, 100);
    }

    private Runnable progressUpdater = new Runnable() {
        public void run() {
            long totalDuration = getPlayer().getCurrentSong().getDuration();
            long currentDuration = getPlayer().getCurrentPosition();
            songTotalDurationLabel.setText(getTime().milliSecondsToTimer(totalDuration));
            songCurrentDurationLabel.setText(getTime().milliSecondsToTimer(currentDuration));
            int progress = getTime().getProgressPercentage(currentDuration, totalDuration);
            songProgressBar.setProgress(progress);
            getProgressHandler().postDelayed(this, 100);
        }
    };

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromTouch) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        getProgressHandler().removeCallbacks(progressUpdater);
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        getProgressHandler().removeCallbacks(progressUpdater);
        int totalDuration = (int) getPlayer().getCurrentSong().getDuration();
        int currentPosition = time.progressToTimer(seekBar.getProgress(), totalDuration);
        getPlayer().seek(currentPosition);
        updateProgressBar();
    }

    private Handler getProgressHandler(){
        return this.progressHandler;
    }
    private Player getPlayer(){
        return this.player;
    }
    private Time getTime(){
        return this.time;
    }
    private void setPlayer(Player player){
        this.player = player;
    }
    private void setProgressHandler(Handler progressHandler){
        this.progressHandler = progressHandler;
    }
    private void setTime(Time time){
        this.time = time;
    }
}

