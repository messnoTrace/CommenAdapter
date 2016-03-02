package com.sample;

import android.os.Bundle;
import android.widget.ListView;

import com.notrace.R;
import com.notrace.SmartAdapter;
import com.sample.model.Place;
import com.sample.model.User;
import com.sample.util.DataGenerator;
import com.sample.view.PlaceView;
import com.sample.view.UserView;

import java.util.List;


public class MultiListViewActivity extends BaseActivity {

    ListView listView;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_listview);

        initView();
    }

    private void initView() {
        listView= (ListView) findViewById(R.id.list_view);
        List mixedList = DataGenerator.generateMix(100);
        SmartAdapter.items(mixedList).map(User.class, UserView.class).map(Place.class, PlaceView.class).into(listView);
    }

}
