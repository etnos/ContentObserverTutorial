package tinyreports.blogspot.de.contentobservertest;

import android.database.ContentObserver;
import android.net.Uri;
import android.util.Log;

public class MyContentObserver extends ContentObserver {

    private static final String LOG_TAG = "MyContentObserver";
    private ContentObserverCallback contentObserverCallback;

    public MyContentObserver(ContentObserverCallback contentObserverCallback) {
        super(null);
        this.contentObserverCallback = contentObserverCallback;
    }

    @Override
    public void onChange(boolean selfChange) {
        this.onChange(selfChange, null);
    }

    @Override
    public void onChange(boolean selfChange, Uri uri) {
        Log.i(LOG_TAG, "Received onChange");
        contentObserverCallback.update();
    }
}
