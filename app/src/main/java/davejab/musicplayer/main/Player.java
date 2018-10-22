package davejab.musicplayer.main;

import android.media.MediaPlayer;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import davejab.musicplayer.models.Item;
import davejab.musicplayer.models.Song;

public class Player implements MediaPlayer.OnCompletionListener {

    private static Player PLAYER = null;

    private MediaPlayer mediaPlayer;

    private List<Item> playlist;
    private int currentSongIndex;

    private boolean shuffle = false;
    private boolean repeat = false;

    private Player(){
        setMediaPlayer(new MediaPlayer());
        getMediaPlayer().setOnCompletionListener(this);
        setShuffle(false);
        setRepeat(false);
    }

    public static Player getPlayer() {
        if (PLAYER == null){
            PLAYER = new Player();
        }
        return PLAYER;
    }

    public Song getCurrentSong(){
        Song currentSong = (Song) getPlaylist().get(getCurrentSongIndex());
        return currentSong;
    }

    public long getCurrentPosition(){
        return getMediaPlayer().getCurrentPosition();
    }

    private int getSongCount(){
        return getPlaylist().size();
    }

    private void getNextSong(){
        if(getRepeat()){
            return;
        } else if(getShuffle()){
            Random rand = new Random();
            setCurrentSongIndex(rand.nextInt((getSongCount() - 1) + 1));
        } else{
            if(getCurrentSongIndex() < (getSongCount() - 1)){
                setCurrentSongIndex(getCurrentSongIndex() + 1);
            }else{
                setCurrentSongIndex(0);
            }
        }
    }

    public void playSong(Song song){
        // Play song
        try {
            getMediaPlayer().reset();
            getMediaPlayer().setDataSource(song.getData());
            getMediaPlayer().prepare();
            getMediaPlayer().start();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void next(){
        getNextSong();
        playSong(getCurrentSong());
    }

    public void previous(){
        if(getCurrentSongIndex() > 0){
            setCurrentSongIndex(getCurrentSongIndex() - 1);
        } else {
            setCurrentSongIndex(getSongCount() - 1);
        }
        playSong(getCurrentSong());
    }

    public boolean toggleShuffle(){
        if (getShuffle()) {
            setShuffle(false);
        } else {
            setShuffle(true);
        }
        return getShuffle();
    }

    public boolean toggleRepeat(){
        if (getRepeat()) {
            setRepeat(false);
        } else {
            setRepeat(true);
        }
        return getRepeat();
    }

    public void seek(int position){
        getMediaPlayer().seekTo(position);
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        getNextSong();
        playSong(getCurrentSong());
    }

    private MediaPlayer getMediaPlayer(){
        return this.mediaPlayer;
    }
    private List<Item> getPlaylist(){
        return this.playlist;
    }
    private int getCurrentSongIndex(){
        return this.currentSongIndex;
    }
    private boolean getShuffle(){
        return this.shuffle;
    }
    private boolean getRepeat(){
        return this.repeat;
    }
    private void setMediaPlayer(MediaPlayer mediaPlayer){
        this.mediaPlayer = mediaPlayer;
    }
    public void setPlaylist(List<Item> playlist){
        this.playlist = playlist;
    }
    public void setCurrentSongIndex(int currentSongIndex){
        this.currentSongIndex = currentSongIndex;
    }
    private void setShuffle(boolean shuffle){
        this.shuffle = shuffle;
    }
    private void setRepeat(boolean repeat){
        this.repeat = repeat;
    }


}
