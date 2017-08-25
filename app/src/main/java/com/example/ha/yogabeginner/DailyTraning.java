package com.example.ha.yogabeginner;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ha.yogabeginner.Utitls.Common;
import com.example.ha.yogabeginner.database.YogaDB;
import com.example.ha.yogabeginner.model.Exercise;
import com.example.ha.yogabeginner.model.ListData;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

public class DailyTraning extends AppCompatActivity {

    Button btnStart;
    ImageView ex_image;
    TextView txtGetReady, txtCountDown, txtTimer, ex_name;
    MaterialProgressBar progressBar;
    LinearLayout layoutGetReady;

    private int ex_id = 0, limit_time = 0;
    List<Exercise> list = new ArrayList<>();

    YogaDB yogaDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_traning);

        initData();
        initView();
        //addEvent();
        setProgressBarEvent();
        setExerciseInformation(ex_id);
        setEventButton();
    }

    private void addEvent() {
        if (yogaDB.getSettingMode() == 0){
            limit_time = Common.TIME_LIMIT_EASY;
        }else if(yogaDB.getSettingMode() == 1){
            limit_time = Common.TIME_LIMIT_MEDIUM;
        }else{
            limit_time = Common.TIME_LIMIT_HARD;
        }
    }

    private void setEventButton() {
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnStart.getText().toString().toLowerCase().equals("start")) {
                    showGetReady();
                    btnStart.setText("DONE");
                }else
                    if (btnStart.getText().toString().toLowerCase().equals("done")){
                        if (yogaDB.getSettingMode() == 0){
                            exerciseEasyCountDown.cancel();
                        }else if (yogaDB.getSettingMode() == 1){
                            exerciseMediumCountDown.cancel();
                        } else if (yogaDB.getSettingMode() == 2){
                            exerciseHardCountDown.cancel();
                        }

                        restTimeCountDown.cancel();
                        if (ex_id < list.size()){
                            showRestTime();
                            ex_id ++ ;
                            progressBar.setProgress(ex_id);
                            txtTimer.setText("");
                        }else
                            showFinished();
                }else{
                        if (yogaDB.getSettingMode() == 0){
                            exerciseEasyCountDown.cancel();
                        }else if (yogaDB.getSettingMode() == 1){
                            exerciseMediumCountDown.cancel();
                        } else if (yogaDB.getSettingMode() == 2){
                            exerciseHardCountDown.cancel();
                        }
                        restTimeCountDown.cancel();
                        if (ex_id < list.size()){
                            setExerciseInformation(ex_id);
                        }else{
                            showFinished();
                        }
                }
            }
        });
    }

    private void showRestTime() {
        ex_image.setVisibility(View.INVISIBLE);
        txtTimer.setVisibility(View.INVISIBLE);
        btnStart.setText("Skip");
        btnStart.setVisibility(View.VISIBLE);
        layoutGetReady.setVisibility(View.VISIBLE);

        restTimeCountDown.start();

        txtGetReady.setText("REST TIME");
    }

    private void showFinished() {
        ex_image.setVisibility(View.INVISIBLE);
        btnStart.setVisibility(View.INVISIBLE);
        txtTimer.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
        layoutGetReady.setVisibility(View.VISIBLE);

        txtGetReady.setText("FINISH");
        txtCountDown.setText("Congratulation \n You're done with exercise to day");
        txtCountDown.setTextSize(20);

        //  Save without done
        yogaDB.saveDay(" " + Calendar.getInstance().getTimeInMillis());
    }

    private void showGetReady() {
        ex_image.setVisibility(View.INVISIBLE);
        btnStart.setVisibility(View.INVISIBLE);
        txtTimer.setVisibility(View.VISIBLE);

        layoutGetReady.setVisibility(View.VISIBLE);

        txtGetReady.setText("GET READY");
        new CountDownTimer(6000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                txtCountDown.setText("" + (millisUntilFinished - 1000) / 1000);
            }

            @Override
            public void onFinish() {
                showExercises();
            }
        }.start();
    }

    private void showExercises() {
        if (ex_id < list.size()){
            ex_image.setVisibility(View.VISIBLE);
            btnStart.setVisibility(View.VISIBLE);
            layoutGetReady.setVisibility(View.INVISIBLE);

            if (yogaDB.getSettingMode() == 0){
                exerciseEasyCountDown.start();
            }else if (yogaDB.getSettingMode() == 1){
                exerciseMediumCountDown.start();
            } else if (yogaDB.getSettingMode() == 2){
                exerciseHardCountDown.start();
            }


            ex_image.setImageResource(list.get(ex_id).getImage_id());
            ex_name.setText(list.get(ex_id).getName());
        }else{
            showFinished();
        }
    }


    // Count down for easy
    CountDownTimer exerciseEasyCountDown = new CountDownTimer(Common.TIME_LIMIT_EASY, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            txtTimer.setText("" + millisUntilFinished/1000);
        }

        @Override
        public void onFinish() {
            if (ex_id < list.size() - 1){
                ex_id ++ ;
                progressBar.setProgress(ex_id);
                txtTimer.setText("");

                setExerciseInformation(ex_id);
                btnStart.setText("Start");
            }else{
                showFinished();
            }
        }
    };
    // Count down for medium
    CountDownTimer exerciseMediumCountDown = new CountDownTimer(Common.TIME_LIMIT_MEDIUM, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            txtTimer.setText("" + millisUntilFinished/1000);
        }

        @Override
        public void onFinish() {
            if (ex_id < list.size() - 1 ){
                ex_id ++ ;
                progressBar.setProgress(ex_id);
                txtTimer.setText("");

                setExerciseInformation(ex_id);
                btnStart.setText("Start");
            }else{
                showFinished();
            }
        }
    };
    // Count down for hard
    CountDownTimer exerciseHardCountDown = new CountDownTimer(Common.TIME_LIMIT_EASY, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            txtTimer.setText("" + millisUntilFinished/1000);
        }

        @Override
        public void onFinish() {
            if (ex_id < list.size() -1 ){
                ex_id ++ ;
                progressBar.setProgress(ex_id);
                txtTimer.setText("");

                setExerciseInformation(ex_id);
                btnStart.setText("Start");
            }else{
                showFinished();
            }
        }
    };


    CountDownTimer restTimeCountDown = new CountDownTimer(10000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            txtCountDown.setText("" + millisUntilFinished/1000);
        }

        @Override
        public void onFinish() {
            setExerciseInformation(ex_id);
            showExercises();
        }
    };

    private void setExerciseInformation(int ex_id) {
        ex_image.setImageResource(list.get(ex_id).getImage_id());
        ex_name.setText(list.get(ex_id).getName());
        btnStart.setText("Start");
        ex_image.setVisibility(View.VISIBLE);
        btnStart.setVisibility(View.VISIBLE);
        txtTimer.setVisibility(View.VISIBLE);

        layoutGetReady.setVisibility(View.INVISIBLE);
    }

    private void setProgressBarEvent() {
        progressBar.setMax(list.size());
    }

    private void initView() {
        btnStart = (Button) findViewById(R.id.btnStart);
        ex_image = (ImageView) findViewById(R.id.detail_image);
        txtCountDown = (TextView) findViewById(R.id.txtCountDown);
        txtGetReady = (TextView) findViewById(R.id.txtGetReady);
        txtTimer = (TextView) findViewById(R.id.timer);
        ex_name = (TextView) findViewById(R.id.title_detail);

        layoutGetReady = (LinearLayout) findViewById(R.id.layout_get_ready);
        progressBar = (MaterialProgressBar) findViewById(R.id.progressBar);
        yogaDB = new YogaDB(this);
    }

    private void initData() {
        for (int i = 0; i < ListData.listImage.length ; i++) {
            list.add(new Exercise(ListData.listImage[i], ListData.listExerciseName[i]));
        }
    }
}
