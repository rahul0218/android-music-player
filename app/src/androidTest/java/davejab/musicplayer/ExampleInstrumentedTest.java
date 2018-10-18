package davejab.musicplayer;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import davejab.musicplayer.main.MediaStoreManager;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("davejab.musicplayer", appContext.getPackageName());



        MediaStoreManager msm = new MediaStoreManager(appContext.getContentResolver());

        //for (String s: msm.getArtistList()) {
           // Log.d("TESTDABRA", msm.getArtistList().toString());

    }
}
