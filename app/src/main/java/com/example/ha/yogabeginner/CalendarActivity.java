package com.example.ha.yogabeginner;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.ha.yogabeginner.custom.WorkoutDoneDecorator;
import com.example.ha.yogabeginner.database.YogaDB;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

public class CalendarActivity extends AppCompatActivity {

    MaterialCalendarView materialCalendarView;
    HashSet<CalendarDay> list = new HashSet<>();

    YogaDB yogaDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        yogaDB = new YogaDB(this);

        materialCalendarView = (MaterialCalendarView) findViewById(R.id.calendar);
        List<String> workoutDay = yogaDB.getWorkoutDays();
        HashSet<CalendarDay> convertList = new HashSet<>();
        for (String value: workoutDay){
            convertList.add(CalendarDay.from(new Date(Long.parseLong(value))));
        }
        materialCalendarView.addDecorator(new WorkoutDoneDecorator(convertList));
    }
}
