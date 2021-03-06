package davejab.musicplayer.models;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore.Audio.Artists;

/**
 * Artist class for modeling artists from the android media store.
 *
 * @author davejab
 */
public class Artist extends Item{

    private String artist;
    private int numberOfAlbums;
    private int numberOfSongs;

    public Artist(ContentResolver contentResolver) {
        super(contentResolver);
        // Sorts artist lists alphabetically
        setOrder(Artists.DEFAULT_SORT_ORDER);
    }

    @Override
    public Uri getExternalUri() {
        return Artists.EXTERNAL_CONTENT_URI;
    }

    @Override
    public String[] getProjection() {
        return new String[] {
                Artists._ID,
                Artists.ARTIST,
                Artists.NUMBER_OF_ALBUMS,
                Artists.NUMBER_OF_TRACKS,
        };
    }

    @Override
    public void setItemSelection(Item item) {
        mergeItem(item);
    }

    @Override
    protected Artist cursorToItem(Cursor cursor) {
        Artist artist = new Artist(getContentResolver());
        artist.setId(cursor.getLong(cursor.getColumnIndex(getProjection()[0])));
        artist.setArtist(cursor.getString(cursor.getColumnIndex(getProjection()[1])));
        artist.setNumberOfAlbums(cursor.getInt(cursor.getColumnIndex(getProjection()[2])));
        artist.setNumberOfSongs(cursor.getInt(cursor.getColumnIndex(getProjection()[3])));
        return artist;
    }

    void mergeArtist(Artist artist) {
        mergeItem(artist);
        setArtist(artist.getArtist());
    }

    public String getArtist(){
        return this.artist;
    }
    public int getNumberOfAlbums(){
        return this.numberOfAlbums;
    }
    public int getNumberOfSongs(){
        return this.numberOfSongs;
    }
    public void setArtist(String artist){
        this.artist = artist;
    }
    public void setNumberOfAlbums(int numberOfAlbums){
        this.numberOfAlbums = numberOfAlbums;
    }
    public void setNumberOfSongs(int numberOfSongs){
        this.numberOfSongs = numberOfSongs;
    }

}
