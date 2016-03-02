package com.sample.view;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.notrace.R;
import com.notrace.views.BindableFrameLayout;
import com.sample.model.User;
import com.sample.util.Interactions;
import com.squareup.picasso.Picasso;

/**
 * Created by notrace on 2016/3/1.
 */
public class UserView extends BindableFrameLayout<User> {

    ImageView userImage;

    TextView userText;

    public UserView(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.view_user;
    }

    @Override
    public void onViewInflated() {
        userImage= (ImageView) findViewById(R.id.user_image);
        userText= (TextView) findViewById(R.id.user_text);
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    @Override
    public void bind(User item) {
        userText.setText(TextUtils.concat(item.getFirstName(), " ", item.getLastName(), "\n", item.getRole()));
        Picasso.with(getContext()).load(item.getAvatar()).into(userImage);

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyItemAction(Interactions.USER_CLICKED);
            }
        });
    }
}
