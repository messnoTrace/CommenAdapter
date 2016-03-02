package com.sample;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.notrace.R;
import com.notrace.SmartAdapter;
import com.notrace.builders.DefaultBindableLayoutBuilder;
import com.notrace.utils.Mapper;
import com.notrace.views.IBindableLayout;
import com.sample.model.Place;
import com.sample.model.User;
import com.sample.util.DataGenerator;
import com.sample.view.PlaceAltView;
import com.sample.view.PlaceView;
import com.sample.view.UserAltView;
import com.sample.view.UserView;

import java.util.List;

public class MultiRecyclerViewCustomBuilderActivity extends BaseActivity {

    RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_recyclerview);

        initView();
    }

    private void initView() {
        recyclerView= (RecyclerView) findViewById(R.id.recycler_view);
        List mixedList = DataGenerator.generateMix(100);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        SmartAdapter.items(mixedList)
                .map(User.class, UserView.class)
                .map(User.class, UserAltView.class)
                .map(Place.class, PlaceView.class)
                .map(Place.class, PlaceAltView.class)
                .builder(new DefaultBindableLayoutBuilder() {

                    @Override
                    public Class<? extends IBindableLayout> viewType(
                            @NonNull Object item, int position, @NonNull Mapper mapper) {
                        if (item instanceof User) {
                            User user = (User) item;
                            if (user.getFirstName().length() % 2 == 1) {
                                return UserView.class;
                            } else {
                                return UserAltView.class;
                            }
                        } else if (item instanceof Place) {
                            Place place = (Place) item;
                            if (place.getName().length() % 2 == 1) {
                                return PlaceView.class;
                            } else {
                                return PlaceAltView.class;
                            }
                        } else {
                            return super.viewType(item, position, mapper);
                        }
                    }

                    @Override
                    public boolean allowsMultimapping() {
                        return true;
                    }
                })
                .into(recyclerView);
    }
}
