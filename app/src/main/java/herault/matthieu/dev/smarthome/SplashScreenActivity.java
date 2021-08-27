package herault.matthieu.dev.smarthome;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.os.Handler;
import android.widget.ProgressBar;
import java.util.Timer;
import java.util.TimerTask;

public class SplashScreenActivity extends AppCompatActivity {

    //Timeout in ms
    private final int SPLASH_SCREEN_TIMEOUT = 1500;

    ProgressBar progressBar;
    int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        progressBar = findViewById(R.id.progressBar);
        final Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                counter++;
                progressBar.setProgress(counter);

                if(counter == SPLASH_SCREEN_TIMEOUT)
                    timer.cancel();
            }
        };

        timer.schedule(timerTask, 0, 20);

        //Redirect after 3 seconds
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                //Launch LoginActivity
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.static_anim);
                finish();
            }
        };

        //Handler post delayed
        new Handler().postDelayed(runnable, SPLASH_SCREEN_TIMEOUT);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}
