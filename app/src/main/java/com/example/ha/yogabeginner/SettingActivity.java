package com.example.ha.yogabeginner;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.ha.yogabeginner.database.YogaDB;

import java.util.Calendar;
import java.util.Date;

public class SettingActivity extends AppCompatActivity {

    Button btnSave;
    RadioGroup rdiGroup;
    RadioButton rdiEasy, rdiHard, rdiMedium;
    YogaDB yogaDB;
    ToggleButton switchAlarm;
    TimePicker timePicker;
    private int radioMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        btnSave = (Button) findViewById(R.id.btnSave);
        rdiGroup = (RadioGroup) findViewById(R.id.rdiGroup);
        rdiEasy = (RadioButton) findViewById(R.id.rdiEasy);
        rdiMedium = (RadioButton) findViewById(R.id.rdiMedium);
        rdiHard = (RadioButton) findViewById(R.id.rdiHard);

        switchAlarm = (ToggleButton) findViewById(R.id.switchAlarm);
        timePicker = (TimePicker) findViewById(R.id.timePicker);

        yogaDB = new YogaDB(this);
        int mode = yogaDB.getSettingMode();
        setRadioMode(mode);


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveWorkoutMode();
                saveAlarm(switchAlarm.isChecked());
                Toast.makeText(SettingActivity.this, "Saved!!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void saveAlarm(boolean checked) {
        if (checked){
            AlarmManager alarmManager  = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            Intent intent ;
            PendingIntent pendingIntent;

            intent = new Intent(SettingActivity.this, AlarmNotificationReceiver.class);
            pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

            //Set time to alarm
            Calendar calendar = Calendar.getInstance();
            Date today = calendar.getTime();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                calendar.set(today.getYear(), today.getMonth(), today.getDay(), timePicker.getHour(), timePicker.getMinute());
            }
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                Log.d("DEBUG", "Time picker will wake up at " + timePicker.getHour() + ":" + timePicker.getMinute());
            }
        }else{
            Intent intent = new Intent(SettingActivity.this, AlarmNotificationReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
            AlarmManager alarmManager  = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.cancel(pendingIntent);

        }
    }

    private void saveWorkoutMode() {
        int selectedID = rdiGroup.getCheckedRadioButtonId();
        if(selectedID == rdiEasy.getId()){
            yogaDB.saveSettingMode(0);
        }else if(selectedID == rdiEasy.getId()){
            yogaDB.saveSettingMode(1);
        } else{
            yogaDB.saveSettingMode(2);
        }
    }

    public void setRadioMode(int radioMode) {
        if (radioMode == 0){
            rdiGroup.check(R.id.rdiEasy);
        }else if (radioMode == 1){
            rdiGroup.check(R.id.rdiMedium);
        }else{
            rdiGroup.check(R.id.rdiHard);
        }
    }
}
