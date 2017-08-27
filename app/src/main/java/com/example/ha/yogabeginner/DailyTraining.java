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

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

public class DailyTraining extends AppCompatActivity {
    @BindView(R.id.btnStart)
    Button btnStart;
    @BindView(R.id.detail_image)
    ImageView ex_image;
    @BindView(R.id.txtGetReady)
    TextView txtGetReady;
    @BindView(R.id.txtCountDown)
    TextView txtCountDown;
    @BindView(R.id.timer)
    TextView txtTimer;
    @BindView(R.id.title_detail)
    TextView ex_name;
    @BindView(R.id.progressBar)
    MaterialProgressBar progressBar;
    @BindView(R.id.layout_get_ready)
    LinearLayout layoutGetReady;

    private int ex_id = 0;
    List<Exercise> list = new ArrayList<>();

    YogaDB yogaDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_traning);

        initData();
        ButterKnife.bind(this);
        yogaDB = new YogaDB(this);
        //addEvent();
        setProgressBarEvent();
        setExerciseInformation(ex_id);
        setEventButton();
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
                        checkSettingMode();
                        restTimeCountDown.cancel();
                        if (ex_id < list.size()){
                            showRestTime();
                            ex_id ++ ;
                            progressBar.setProgress(ex_id);
                            txtTimer.setText("");
                        }else
                            showFinished();
                }else{
                        checkSettingMode();
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

    private void checkSettingMode(){
        if (yogaDB.getSettingMode() == 0){
            exerciseEasyCountDown.cancel();
        }else if (yogaDB.getSettingMode() == 1){
            exerciseMediumCountDown.cancel();
        } else if (yogaDB.getSettingMode() == 2){
            exerciseHardCountDown.cancel();
        }
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
        new CountDownTimer(4000, 1000) {
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
    CountDownTimer exerciseHardCountDown = new CountDownTimer(Common.TIME_LIMIT_HARD, 1000) {
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

    CountDownTimer restTimeCountDown = new CountDownTimer(3000, 1000) {
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

    private void initData() {
        for (int i = 0; i < ListData.listImage.length ; i++) {
            list.add(new Exercise(ListData.listImage[i], ListData.listExerciseName[i]));
        }
    }
}
