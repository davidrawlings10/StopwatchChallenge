package com.example.stopwatchchallenge;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Button btnMain;
    TextView textViewObject;
    Handler customerHandler = new Handler();
    LinearLayout container;
    LinearLayout background;
    TextView scoreViewObject;
    TextView attemptsViewObject;

    long startTime=0L,timeInMilliseconds=0L;

    int score = 0;
    int attempts = 10;
    int taps = 0;
    //boolean clockStopped = false;

    Runnable updateTimerThread = new Runnable() {
        @Override
        public void run() {
            System.out.println("timeInMilliseconds:"+timeInMilliseconds+", startTime:"+startTime);
            System.out.println(attempts);
            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
            int secs = (int) (timeInMilliseconds / 1000);
            int mins = secs / 60;
            secs %= 60;
            int milliseconds = (int) (timeInMilliseconds % 1000 / 10);
            if (attempts > 0)
                textViewObject.setText("" + mins + ":" + String.format("%02d", secs) + ":"
                        + String.format("%02d", milliseconds));
            customerHandler.postDelayed(this, 0);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);
        btnMain = (Button)findViewById(R.id.btnRolf);
        textViewObject = (TextView)findViewById(R.id.timerView);
        container = (LinearLayout)findViewById(R.id.container);
        background = (LinearLayout)findViewById(R.id.background);
        scoreViewObject = (TextView)findViewById(R.id.scoreView);
        scoreViewObject.setText("Score   " + Integer.toString(score));
        attemptsViewObject = (TextView)findViewById(R.id.attemptsView);
        attemptsViewObject.setText("Attempts   " + Integer.toString(attempts));
        startTime = SystemClock.uptimeMillis();
        customerHandler.postDelayed(updateTimerThread, 0);

        btnMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (attempts == 0) {
                    // restart the game if button is tapped when user is out of attempts
                    taps = 0;
                    attempts = 10;
                    score = 0;
                    btnMain.setText("Tap Here");
                    scoreViewObject.setText("Points   " + Integer.toString(score));
                    attemptsViewObject.setText("Attempts   " + Integer.toString(attempts));
                    startTime = SystemClock.uptimeMillis();
                    timeInMilliseconds = 0L;
                    container.removeAllViews();
                    return;
                }

                btnMain.setText("");
                ++taps;

                LayoutInflater inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View addView = inflater.inflate(R.layout.row, null);

                Integer tenth = Integer.parseInt(textViewObject.getText().toString().substring(5, 6));
                Integer hundredth = Integer.parseInt(textViewObject.getText().toString().substring(6, 7));
                Integer fraction = Integer.parseInt(textViewObject.getText().toString().substring(5, 7));
                int pointsAwarded = Math.abs(fraction - 50);
                score += pointsAwarded;
                StringBuilder rowText = new StringBuilder();
                rowText.append("    " + textViewObject.getText() + "     +" + pointsAwarded + " pts");
                if (pointsAwarded != 50) {
                    attempts--;
                    if (attempts == 0)
                        btnMain.setText("Restart");
                } else {
                    rowText.append("    +1 attempt");
                }
                TextView txtValue = (TextView)addView.findViewById(R.id.txtContent);
                txtValue.setText(rowText);
                scoreViewObject.setText("Points   " + Integer.toString(score));
                attemptsViewObject.setText("Attempts   " + Integer.toString(attempts));
                switch (tenth) {
                    case 0:
                    case 9:
                        if (tenth == 0 && hundredth == 0)
                            txtValue.setTextColor(Color.BLUE);
                        else
                            txtValue.setTextColor(Color.rgb(0, 255, 0));
                        break;
                    case 1:
                    case 8:
                        txtValue.setTextColor(Color.rgb(255, 200, 0));
                        break;
                    case 2:
                    case 7:
                        txtValue.setTextColor(Color.rgb(255, 125, 0));
                        break;
                    default:
                        txtValue.setTextColor(Color.rgb(255, 0, 0));
                        break;
                }
                container.addView(addView, 0);
                if (taps > 12)
                    container.removeViewAt(12);
            }
        });
    }
}
