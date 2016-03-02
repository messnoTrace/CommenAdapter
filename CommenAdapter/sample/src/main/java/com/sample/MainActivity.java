package com.sample;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.notrace.R;

public class MainActivity extends BaseActivity implements View.OnClickListener{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        findViewById(R.id.single_listview_button).setOnClickListener(this);
        findViewById(R.id.single_recyclerview_button).setOnClickListener(this);
        findViewById(R.id.multi_listview_button).setOnClickListener(this);
        findViewById(R.id.multi_recyclerview_button).setOnClickListener(this);
        findViewById(R.id.multi_recyclerview_custom_builder_button).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.single_listview_button:
            startActivity(new Intent(this, SingleListViewActivity.class));
            break;

            case R.id.single_recyclerview_button:
            startActivity(new Intent(this, SingleRecyclerViewActivity.class));
                break;

            case R.id.multi_listview_button:
            startActivity(new Intent(this, MultiListViewActivity.class));
                break;

            case R.id.multi_recyclerview_button:
            startActivity(new Intent(this, MultiRecyclerViewActivity.class));
                break;

            case R.id.multi_recyclerview_custom_builder_button:
            startActivity(new Intent(this, MultiRecyclerViewCustomBuilderActivity.class));
                break;
        }
    }
}
