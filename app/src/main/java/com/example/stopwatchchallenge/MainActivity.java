package com.example.stopwatchchallenge;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
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

    Button /*btnStart, btnPause,*/ btnHome, btnRolf;
    TextView textViewObject;
    Handler customerHandler = new Handler();
    LinearLayout container;
    LinearLayout background;
    TextView scoreViewObject;
    TextView attemptsViewObject;

    long startTime=0L,timeInMilliseconds=0L; //,timeSwapBuff=0L,updateTime=0L;

    int score = 0;
    int attempts = 10;

    Runnable updateTimerThread = new Runnable() {
        @Override
        public void run() {
            timeInMilliseconds = SystemClock.uptimeMillis()-startTime;
            //updateTime = timeSwapBuff+timeInMilliseconds;
            int secs=(int)(timeInMilliseconds/1000);
            int mins=secs/60;
            secs%=60;
            int milliseconds=(int)(timeInMilliseconds%1000/10);
            textViewObject.setText(""+mins+":"+String.format("%02d",secs)+":"
                    +String.format("%02d",milliseconds));
            customerHandler.postDelayed(this, 0);
        }
    };

    //private Chronometer chronometer;
    //private boolean running;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //btnStart = (Button)findViewById(R.id.btnStart);
        //btnPause = (Button)findViewById(R.id.btnPause);
        btnHome = (Button)findViewById(R.id.btnHome);
        btnRolf = (Button)findViewById(R.id.btnRolf);
        textViewObject = (TextView)findViewById(R.id.timerView);
        container = (LinearLayout)findViewById(R.id.container);
        background = (LinearLayout)findViewById(R.id.background);
        scoreViewObject = (TextView)findViewById(R.id.scoreView);
        scoreViewObject.setText("Score   " + Integer.toString(score));
        attemptsViewObject = (TextView)findViewById(R.id.attemptsView);
        attemptsViewObject.setText("Attempts   " + Integer.toString(attempts));

        //Typeface type = Typeface.createFromAsset(getAssets(),"font/dsdigii.tff");
        //textViewObject.setTypeface(type);

        //btnStart.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View view) {
        startTime = SystemClock.uptimeMillis();
        customerHandler.postDelayed(updateTimerThread, 0);
        //    }
        //});

        /*btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timeSwapBuff+=timeInMilliseconds;
                customerHandler.removeCallbacks(updateTimerThread);
            }
        });*/

        btnRolf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = (LayoutInflater)getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View addView = inflater.inflate(R.layout.row, null);

                TextView txtValue = (TextView)addView.findViewById(R.id.txtContent); // row
                txtValue.setText(textViewObject.getText()); // main
                Integer tenth = Integer.parseInt(textViewObject.getText().toString().substring(5, 6));
                Integer hundredth = Integer.parseInt(textViewObject.getText().toString().substring(6, 7));
                switch (tenth) {
                    case 0:
                    case 9:
                        if (tenth == 0 && hundredth == 0) {
                            txtValue.setTextColor(Color.BLUE);
                            score += 10;
                            attempts--;
                            scoreViewObject.setText("Score   " + Integer.toString(score));
                            attemptsViewObject.setText("Attempts   " + Integer.toString(attempts));
                        }
                        else {
                            //txtValue.setTextColor(Color.GREEN);
                            txtValue.setTextColor(Color.rgb(0, 255, 0));
                            score += 5;
                            attempts--;
                            scoreViewObject.setText("Score   " + Integer.toString(score));
                            attemptsViewObject.setText("Attempts   " + Integer.toString(attempts));
                        }
                        break;
                    case 1:
                    case 8:
                        txtValue.setTextColor(Color.rgb(255, 200, 0));
                        score += 3;
                        attempts--;
                        scoreViewObject.setText("Score   " + Integer.toString(score));
                        attemptsViewObject.setText("Attempts   " + Integer.toString(attempts));
                        break;
                    case 2:
                    case 7:
                        txtValue.setTextColor(Color.rgb(255, 125, 0));
                        score += 1;
                        attempts--;
                        scoreViewObject.setText("Score   " + Integer.toString(score));
                        attemptsViewObject.setText("Attempts   " + Integer.toString(attempts));
                        break;
                    default:
                        txtValue.setTextColor(Color.rgb(255, 0, 0));
                        break;
                }
                container.addView(addView, 0);
            }
        });
    }
}
