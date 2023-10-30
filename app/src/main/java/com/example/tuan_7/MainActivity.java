package com.example.tuan_7;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    ProgressBar progress;
    TextView percent;
    EditText edt;
    Button doItAgainBtn;
    int globalVar = 0, accum = 0, progressStep = 1;
    long startingMills = System.currentTimeMillis();
    boolean isRunning = false;
    final int MAX_PROGRESS = 100;

    int inputInt;
    Handler myHandler=new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progress=(ProgressBar) findViewById(R.id.progress);
        percent=(TextView) findViewById(R.id.percent);
        edt=(EditText) findViewById(R.id.nwork);
        doItAgainBtn=(Button) findViewById(R.id.doItAgainbtn);
        doItAgainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Start();
            }
        });

    }

    private void Start() {
        doItAgainBtn.setEnabled(false);
        accum = 0;
        progress.setMax(MAX_PROGRESS);
        progress.setProgress(0);
        progress.setVisibility(View.VISIBLE);

        //inputInt = (int) edt.getText().toString();
        String readString = edt.getText().toString();

        inputInt = Integer.parseInt(readString);
        Thread myBackgroundThread = new Thread(backgroundTask,"backAlias1");
        myBackgroundThread.start();
    }

    private Runnable foregroundRunnable = new Runnable() {
        @Override
        public void run() {
            try {

                progress.incrementProgressBy(progressStep);
                accum += progressStep; //try: myBarHorizontal,setProgress(accum*(MAX_PROGRESS/nWork))
                percent.setText(accum + "%");

                if (accum >= progress.getMax()) {
                        doItAgainBtn.setEnabled(true);
                }


            }
            catch (Exception e) { Log.e("<<foregroundTask>>", e.getMessage()); }
        }
    }; // foregroundTask
    private Runnable backgroundTask = new Runnable() {
        @Override
        public void run() { // busy work goes here...
            try {
                for (int n = 0; n < inputInt; n++) {

                    Thread.sleep(0);

                    globalVar++;

                    myHandler.post(foregroundRunnable);
                }
            }
            catch (InterruptedException e) { Log.e("<<foregroundTask>>", e.getMessage()); }
        }// run
    };// backgroundTask
}// ThreadsPosting



