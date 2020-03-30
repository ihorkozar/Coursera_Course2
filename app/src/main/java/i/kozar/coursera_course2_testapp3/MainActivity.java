package i.kozar.coursera_course2_testapp3;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {
    private Button buttonStart;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonStart = findViewById(R.id.btn_start);
        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickStart();
            }
        });
        if (LoaderManager.getInstance(this).getLoader(1) != null) {
            LoaderManager.getInstance(this).initLoader(1, null, this);
            getProgressBar().setVisibility(View.VISIBLE);
            getTextView().setText(R.string.text_for_TextView);
            buttonStart.setEnabled(false);
        }
    }

    public void onClickStart(){
        LoaderManager.getInstance(this).initLoader(1,null,this);
        getProgressBar().setVisibility(View.VISIBLE);
        getTextView().setText(R.string.text_for_TextView);
        buttonStart.setEnabled(false);
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


    @NonNull
    @Override
    public Loader<String> onCreateLoader(int i, @Nullable Bundle bundle) {
        return new AsyncTaskLoader<String>(this) {
            @Nullable
            @Override
            public String loadInBackground() {
                try {
                    TimeUnit.MILLISECONDS.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return "Ok";
            }

            @Override
            protected void onStartLoading() {
                forceLoad();
            }
        };
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String s) {
        getProgressBar().setVisibility(View.GONE);
        getTextView().setText(R.string.text_for_finish);
        getButton().setEnabled(true);
        LoaderManager.getInstance(this).destroyLoader(1);
        //After getting result we will update our UI here
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }
}
