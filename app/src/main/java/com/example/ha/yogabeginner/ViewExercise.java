package com.example.ha.yogabeginner;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ha.yogabeginner.Utitls.ActivityUtitls;
import com.example.ha.yogabeginner.Utitls.Common;
import com.example.ha.yogabeginner.database.YogaDB;

public class ViewExercise extends AppCompatActivity {

    private int image_id;
    private String name_exercise;

    TextView timer, title;
    ImageView imageView;

    private Button btnStart;

    boolean isRunning = false;

    YogaDB yogaDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_exercise);

        initView();
        addEvents();
    }

    private void addEvents() {
        timer.setText("");
        if (getIntent()!=null){
            image_id = getIntent().getIntExtra(ActivityUtitls.IMAGE_PUT_EXTRA, -1);
            name_exercise = getIntent().getStringExtra(ActivityUtitls.NAME_EXERCISE_EXTRA);
            imageView.setImageResource(image_id);
            title.setText(name_exercise);
        }
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isRunning){
                    btnStart.setText("DONE");

                    int timeLimit = 0;
                    if(yogaDB.getSettingMode() == 0){
                        timeLimit = Common.TIME_LIMIT_EASY;
                    }else if (yogaDB.getSettingMode() == 1){
                        timeLimit = Common.TIME_LIMIT_MEDIUM;
                    }else if (yogaDB.getSettingMode() == 2){
                        timeLimit = Common.TIME_LIMIT_HARD;
                    }
                    new CountDownTimer(timeLimit, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            timer.setText("" + millisUntilFinished/1000);
                        }

                        @Override
                        public void onFinish() {
                            Toast.makeText(ViewExercise.this, "Finish! Next exercises", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }.start();
                }else{
                    Toast.makeText(ViewExercise.this, "Finish! Next exercises", Toast.LENGTH_SHORT).show();
                    finish();
                }
                isRunning = !isRunning;
            }
        });
    }

    private void initView() {
        imageView = (ImageView) findViewById(R.id.detail_image);
        timer = (TextView) findViewById(R.id.timer);
        title = (TextView) findViewById(R.id.title_detail);
        btnStart = (Button) findViewById(R.id.btnStart);

        yogaDB = new YogaDB(this);
    }
}
