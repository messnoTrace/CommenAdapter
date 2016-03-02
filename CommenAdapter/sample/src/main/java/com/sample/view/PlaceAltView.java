package com.sample.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.notrace.R;
import com.notrace.views.BindableRelativeLayout;
import com.sample.model.Place;
import com.sample.util.Interactions;
import com.squareup.picasso.Picasso;

/**
 * Created by notrace on 2016/3/1.
 */
public class PlaceAltView extends BindableRelativeLayout<Place> {

    ImageView placeImage;

    TextView placeText;

    public PlaceAltView(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.view_place_alt;
    }

    @Override
    public void onViewInflated() {
        placeImage= (ImageView) findViewById(R.id.place_alt_image);
        placeText= (TextView) findViewById(R.id.place_alt_text);
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    @Override
    public void bind(Place item) {
        placeText.setText(item.getName());
        Picasso.with(getContext()).load(item.getImageUrl()).into(placeImage);

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyItemAction(Interactions.PLACE_CLICKED);
            }
        });

    }

}
