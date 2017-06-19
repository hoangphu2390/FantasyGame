package com.fantasygame.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;

/**
 * This is the base holder include general method
 * <p>
 * Created by LoiHo on 12/10/2015
 */
public class BaseHolder<V> extends RecyclerView.ViewHolder {

    public BaseHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        bindEvent();
    }

    public void bindData(V data) {
    }

    public void bindEvent() {
    }
}
