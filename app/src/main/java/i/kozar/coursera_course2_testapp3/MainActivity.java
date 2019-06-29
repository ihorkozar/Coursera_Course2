package i.kozar.coursera_course2_testapp3;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Button buttonStart;
    ProgressBar progressBar;
    TextView textView;

    SleepService sleepService;
    boolean mBound = false;

    private BroadcastReceiver mLocalBroadcast = new BroadcastReceiver(){
        @Override
        public void onReceive(Context context, Intent intent) {
            /** Пытался какой-то костыль навесить
             * boolean finish = intent.getBooleanExtra("finnish", false);
            if (finish){
                unbindService(mConnection);
                mBound = false;
                buttonStart.setEnabled(true);
                progressBar.setVisibility(ProgressBar.INVISIBLE);
                textView.setText(R.string.text_for_finish);
            }
             **/

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.progressBar);
        textView = findViewById(R.id.text);
        buttonStart = findViewById(R.id.btn_start);

        LocalBroadcastManager.getInstance(this).registerReceiver(mLocalBroadcast ,
                new IntentFilter("myBroadcast"));

        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonStart.setEnabled(false);
                progressBar.setVisibility(ProgressBar.VISIBLE);
                textView.setText(R.string.text_for_TextView);
                Intent intent = new Intent(MainActivity.this, SleepService.class);
                bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
                //start task
            }
        });
    }

    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            SleepService.LocalBinder binder = (SleepService.LocalBinder) service;
            sleepService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };
}
