package com.sample;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.notrace.R;
import com.notrace.SmartAdapter;
import com.sample.model.Place;
import com.sample.model.User;
import com.sample.util.DataGenerator;
import com.sample.view.PlaceView;
import com.sample.view.UserView;

import java.util.List;

public class MultiRecyclerViewActivity extends BaseActivity {

    RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_recyclerview);

        initView();
    }

    private void initView() {
        recyclerView= (RecyclerView) findViewById (R.id.recycler_view);
        List mixedList = DataGenerator.generateMix(100);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        SmartAdapter.items(mixedList)
                .map(User.class, UserView.class)
                .map(Place.class, PlaceView.class)
                .into(recyclerView);
    }

}
