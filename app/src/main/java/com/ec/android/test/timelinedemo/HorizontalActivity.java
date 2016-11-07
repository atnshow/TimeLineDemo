package com.ec.android.test.timelinedemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HorizontalActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horizontal);
        //
        initView();
    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        RecyclerView.LayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setHasFixedSize(true);
        //
        List<TimeLineModel> mDataList = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            TimeLineModel model = new TimeLineModel();
            model.setName("Random" + i);
            model.setAge(i);
            mDataList.add(model);
        }

        HorizontalAdapter adapter = new HorizontalAdapter(mDataList);

        mRecyclerView.setAdapter(adapter);
    }
}
