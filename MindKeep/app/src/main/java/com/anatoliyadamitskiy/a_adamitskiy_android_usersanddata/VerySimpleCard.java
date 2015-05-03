package com.anatoliyadamitskiy.a_adamitskiy_android_usersanddata;

import android.content.Context;

import com.dexafree.materialList.cards.SimpleCard;

/**
 * Created by Anatoliy on 4/26/15.
 */
public class VerySimpleCard extends SimpleCard {

    public VerySimpleCard(Context context) {
        super(context);
    }

    @Override
    public int getLayout() {
        return R.layout.verysimplecard_layout;
    }
}
