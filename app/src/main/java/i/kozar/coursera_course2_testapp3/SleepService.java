package i.kozar.coursera_course2_testapp3;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SleepService extends Service {

    // Binder given to clients
    private final IBinder mBinder = new LocalBinder();
    ScheduledExecutorService mScheduledExecutorService;

    public SleepService() {
    }

    public class LocalBinder extends Binder {
        SleepService getService() {
            return SleepService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        mScheduledExecutorService.submit(new Runnable() {
            @Override
            public void run() {
                Thread.sleep(5);
            }
        });
        return mBinder;
    }

    @Override
    public void onCreate() {
        mScheduledExecutorService = Executors.newScheduledThreadPool(1);
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        mScheduledExecutorService.shutdownNow();
        super.onDestroy();
    }
}
