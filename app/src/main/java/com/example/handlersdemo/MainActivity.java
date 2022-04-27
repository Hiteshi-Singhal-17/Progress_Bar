package com.example.handlersdemo;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity  {
    private ProgressBar mProgressBar ;
    private TextView mProgress ;
    private Handler mHandler ;
    private Button mButton ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initializing data members
        mHandler=new Handler() ;
        mProgressBar=findViewById(R.id.progressBar) ;
        mProgress=findViewById(R.id.progressCounter) ;
        //Data members
        mButton = findViewById(R.id.button);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performAction() ;
            }
        });
    }

    private void performAction() {
        //Stimulate heavy task in background thread
        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<=100;i+=10){
                    final int currentProgress=i ;
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                //Post updates to the User Interface
                    //hANDLER IS ATTACHED TO WHAT AND WHERE IS LOOPER?
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mProgressBar.setProgress(currentProgress);
                            String taskCompleted=currentProgress+"/"+mProgressBar.getMax() ;
                            mProgress.setText(taskCompleted);
                            if(currentProgress==100){
                                findViewById(R.id.animationView).setVisibility(View.VISIBLE);
                                mProgressBar.setVisibility(View.GONE);
                                mProgress.setVisibility(View.GONE);
                                mButton.setVisibility(View.GONE);
                            }

                        }
                    });
                }
            }
        }).start() ;
    }
}
