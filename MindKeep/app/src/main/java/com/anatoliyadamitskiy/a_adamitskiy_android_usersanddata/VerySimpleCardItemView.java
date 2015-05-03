package com.anatoliyadamitskiy.a_adamitskiy_android_usersanddata;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.dexafree.materialList.model.CardItemView;

/**
 * Created by Anatoliy on 4/26/15.
 */
public class VerySimpleCardItemView extends CardItemView<VerySimpleCard> {

    TextView mTitle;
    TextView mDescription;

    public VerySimpleCardItemView(Context context) {
        super(context);
    }

    public VerySimpleCardItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public VerySimpleCardItemView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void build(VerySimpleCard card) {
        setTitle(card.getTitle());
        setDescription(card.getDescription());
    }

    public void setTitle(String title){
        mTitle = (TextView)findViewById(R.id.titleTextView);
        mTitle.setText(title);
    }

    public void setDescription(String description){
        mDescription = (TextView)findViewById(R.id.descriptionTextView);
        mDescription.setText(description);
    }
}
