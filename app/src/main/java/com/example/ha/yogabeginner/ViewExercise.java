package com.example.ha.yogabeginner;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ViewExercise extends AppCompatActivity {

    private int image_id;
    private String name_exercise;

    TextView timer, title;
    ImageView imageView;

    private Button btnStart;

    boolean isRunning = false;

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
                    new CountDownTimer(20000, 1000) {
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
    }
}
