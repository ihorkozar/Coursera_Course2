package i.kozar.coursera_course2_testapp3;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    private Button buttonStart;
    private SleepTask mSleepTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSleepTask = new SleepTask(MainActivity.this);

        buttonStart = findViewById(R.id.btn_start);
        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSleepTask.execute();
            }
        });
    }

    TextView getTextView() {
        return findViewById(R.id.text);
    }

    ProgressBar getProgressBar() {
        return findViewById(R.id.progressBar);
    }

    Button getButton(){
        return findViewById(R.id.btn_start);
    }

    /**
     * Асинк таск :
     */

    private static class SleepTask extends AsyncTask<String, Void, Bitmap> {
        private WeakReference<MainActivity> mActivityWeakReference;

        private SleepTask(MainActivity activity) {
            mActivityWeakReference = new WeakReference<>(activity);
        }

        @Override
        protected void onPreExecute() {
            MainActivity activity = mActivityWeakReference.get();
            if (activity != null) {
                activity.getProgressBar().setVisibility(View.VISIBLE);
                activity.getTextView().setText(R.string.text_for_TextView);
                activity.getButton().setEnabled(false);
            }
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            try {
                TimeUnit.MILLISECONDS.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            MainActivity activity = mActivityWeakReference.get();
            if (activity != null) {
                activity.getProgressBar().setVisibility(View.INVISIBLE);
                activity.getTextView().setText(R.string.text_for_finish);
                activity.getButton().setEnabled(true);
            }
        }

        @Override
        protected void onCancelled() {
            MainActivity mainActivity = mActivityWeakReference.get();
            if (mainActivity != null) {
                mainActivity.getProgressBar().setVisibility(View.INVISIBLE);
                Toast.makeText(mainActivity, "Canceled", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
