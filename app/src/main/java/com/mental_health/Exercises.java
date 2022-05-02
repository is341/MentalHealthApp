package com.mental_health;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.mental_health.Adapters.ExercisesAdapter;
import com.mental_health.dataModel.DataModelExercises;

import java.util.ArrayList;

public class Exercises extends AppCompatActivity {
    android.content.res.Resources res;



    GridLayoutManager gridLayoutManager;
    RecyclerView recyclerView;

    ExercisesAdapter mAdapter;
    ArrayList<DataModelExercises> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises);
        getSupportActionBar().setTitle("Exercises");
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        gridLayoutManager = new GridLayoutManager(Exercises.this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);


        String[] nameArray = {
                getString(R.string.ext1),
                getString(R.string.ext2),
                getString(R.string.ext3),
                getString(R.string.ext4),
                getString(R.string.ext5),
                getString(R.string.ext6),
                getString(R.string.ext7)


        };

        String[] statusArray = {

                getString(R.string.exd1),
                getString(R.string.exd2),
                getString(R.string.exd3),
                getString(R.string.exd4),
                getString(R.string.exd5),
                getString(R.string.exd6),
                getString(R.string.exd7),
        };


        data = new ArrayList<DataModelExercises>();
        for (int i = 0; i < nameArray.length; i++) {
            data.add(new DataModelExercises( nameArray[i], statusArray[i]));
        }

        mAdapter = new ExercisesAdapter(data, Exercises.this);
        recyclerView.setAdapter(mAdapter);
    }
}