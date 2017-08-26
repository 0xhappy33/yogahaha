package com.example.ha.yogabeginner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.ha.yogabeginner.adapter.RecyclerViewAdapter;
import com.example.ha.yogabeginner.model.Exercise;
import com.example.ha.yogabeginner.model.ListData;

import java.util.ArrayList;
import java.util.List;

public class ListExercises extends AppCompatActivity {

    private List<Exercise> exerciseList = new ArrayList<>();
    RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerView;
    RecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_exercises);

        initData();
        initView();
    }

    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.list_ex);
        adapter = new RecyclerViewAdapter(exerciseList, getApplicationContext());
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void initData() {
        for (int i = 0; i < ListData.listImage.length ; i++) {
            exerciseList.add(new Exercise(ListData.listImage[i], ListData.listExerciseName[i]));
        }
    }
}
