package com.sample;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.notrace.R;
import com.notrace.SmartAdapter;
import com.sample.model.User;
import com.sample.util.DataGenerator;
import com.sample.view.UserView;

import java.util.List;


public class SingleRecyclerViewActivity extends BaseActivity {

    RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_recyclerview);

        initView();
    }

    private void initView() {
        recyclerView= (RecyclerView) findViewById(R.id.recycler_view);
        List<User> userList = DataGenerator.generateUsers(100);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        SmartAdapter.items(userList).map(User.class, UserView.class).into(recyclerView);
    }

}
