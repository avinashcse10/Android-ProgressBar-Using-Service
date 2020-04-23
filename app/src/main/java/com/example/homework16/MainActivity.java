package com.example.homework16;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    IntentFilter intentFilter;
    private int progressStatus = 0;
    private Handler handler = new Handler();
    //private TextView textView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //textView =  findViewById(R.id.text);

        progressBar= findViewById(R.id.progressBar);

    }
    @Override
    public void onResume() {
        super.onResume();
        intentFilter = new IntentFilter();
        intentFilter.addAction("FILE_DOWNLOADED_ACTION");
        //---register the receiver---
        registerReceiver(intentReceiver, intentFilter);
    }
    @Override
    public void onPause() {
        super.onPause();
        //---unregister the receiver---
        unregisterReceiver(intentReceiver);
    }
    public void startService(View view) {
        startService(new Intent(getBaseContext(), MyService.class));
        new Thread(new Runnable() {
            public void run() {
                while (progressStatus < 100) {
                    progressStatus += 1;
                    // Update the progress bar and display the
                    //current value in the text view
                    handler.post(new Runnable() {
                        public void run() {
                            progressBar.setProgress(progressStatus);
                            //textView.setText(progressStatus+"/"+progressBar.getMax());
                        }
                    });
                    try {
                        // Sleep for 200 milliseconds.
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        }

    public void stopService(View view) {
        stopService(new Intent(getBaseContext(), MyService.class));
        progressBar.setProgress(0);
    }
    private BroadcastReceiver intentReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            progressBar.setProgress(100);

            Toast.makeText(getBaseContext(), "File downloaded!",
                    Toast.LENGTH_LONG).show();
        }
    };
}

