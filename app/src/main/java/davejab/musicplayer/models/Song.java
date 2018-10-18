package davejab.musicplayer.models;

import android.database.Cursor;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.provider.MediaStore.Audio.Media;

public class Song extends Item{

    // Static

    private static Uri EXTERNAL_URI = Media.EXTERNAL_CONTENT_URI;
    private static String[] PROJECTION = {
            Media.TITLE,
            Media.ALBUM,
            Media.ARTIST,
            Media.DATA,
            Media.DURATION
    };

    // Instance

    @Override
    public Uri getExternalUri() {
        return EXTERNAL_URI;
    }

    @Override
    public String[] getProjection() {
        return PROJECTION;
    }

    @Override
    public String getSelection() {
        return null;
    }

    @Override
    public String getOrder() {
        return null;
    }

    @Override
    public Item cursorToItem(Cursor cursor) {
        Song song = new Song();
        song.setTitle(cursor.getString(cursor.getColumnIndex(getProjection()[0])));
        song.setData(cursor.getString(cursor.getColumnIndex(getProjection()[0])));
        return song;
    }

    private String title;
    private String data;

    public String getTitle(){
        return this.title;
    }
    public String getData(){
        return this.data;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public void setData(String data){
        this.data = data;
    }

}
