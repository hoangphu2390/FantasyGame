package com.fantasygame.ui.sport;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.fantasygame.R;
import com.fantasygame.data.model.response.FeatureResponse.Datum;
import com.fantasygame.define.Constant;
import com.fantasygame.utils.Utils;

import java.util.List;

/**
 * Created by HP on 20/06/2017.
 */

public class FeatureAdapter extends PagerAdapter {

    List<Datum> listFeature;
    LayoutInflater inflater;

    public FeatureAdapter(LayoutInflater inflater, List<Datum> listFeature) {
        this.inflater = inflater;
        this.listFeature = listFeature;
    }


    @Override
    public int getCount() {
        return listFeature.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, final int position) {
        View layout = inflater.inflate(R.layout.item_feature, view, false);
        ImageView iv_feature = (ImageView) layout.findViewById(R.id.iv_feature);

        Datum feature = listFeature.get(position);
        Utils.loadImageFromURL(view.getContext(), Constant.URL_ADDRESS_SERVER + feature.image, iv_feature);

        view.addView(layout);
        return layout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}

