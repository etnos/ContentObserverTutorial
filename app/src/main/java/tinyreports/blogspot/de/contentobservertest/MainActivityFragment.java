package tinyreports.blogspot.de.contentobservertest;

import android.app.Fragment;
import android.content.ContentValues;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements ContentObserverCallback {

    public MainActivityFragment() {
    }

    MyContentObserver myContentObserver;
    TextView txtCount;
    int count = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        Button button = (Button) view.findViewById(R.id.button);
        txtCount = (TextView) view.findViewById(R.id.txtCount);

        txtCount.setText(String.valueOf(count));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // do some action with database like Update, Insert, Delete
                // with registered in ContentObserver Uri
                ContentValues contentValues = new ContentValues();
                getActivity().getContentResolver().update(MyContentProvider.uriTest, contentValues, null, null);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (myContentObserver == null) {
            myContentObserver = new MyContentObserver(this);
        }

        // register ContentObserver in onResume
        getActivity().getContentResolver().
                registerContentObserver(
                        MyContentProvider.uriTest,
                        true,
                        myContentObserver);
    }

    @Override
    public void onPause() {
        super.onPause();
        // always call unregisterContentObserver in onPause
        // skipping this call will produce memory leak
        getActivity().getContentResolver().unregisterContentObserver(myContentObserver);
    }

    /**
     * this is a callback method for a content observer
     * runs NOT in UI thread
     */
    @Override
    public void update() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                txtCount.setText(String.valueOf(++count));
            }
        });
    }
}
