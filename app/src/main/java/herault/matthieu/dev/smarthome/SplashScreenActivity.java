package herault.matthieu.dev.smarthome;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.os.Handler;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class SplashScreenActivity extends AppCompatActivity {

    //Timeout in ms
    private final int SPLASH_SCREEN_TIMEOUT = 4000;

    ProgressBar progressBar;
    int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
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

        timer.schedule(timerTask, 0, 30);

        //Redirect after 3 seconds
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                //Start a page
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                //Toast on startup
                Toast.makeText(SplashScreenActivity.this, "Server Connection Success !", Toast.LENGTH_LONG).show();
                finish();
            }
        };

        //Handler post delayed
        new Handler().postDelayed(runnable, SPLASH_SCREEN_TIMEOUT);
    }
}
