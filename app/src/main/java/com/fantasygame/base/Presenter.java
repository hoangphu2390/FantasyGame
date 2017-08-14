package com.fantasygame.base;

import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import rx.subscriptions.CompositeSubscription;

/**
 * Base presenter implementation.
 * <p>
 * Created by LoiHo on 12/7/2015.
 */
public class Presenter<V> {

    @Nullable
    private volatile V view;

    protected CompositeSubscription subscriptions;

    @CallSuper
    public void bindView(@NonNull V view) {
        final V previousView = this.view;

        this.view = view;
        this.subscriptions = new CompositeSubscription();
    }

    @Nullable
    protected V view() {
        return view;
    }


    @CallSuper
    public void unbindView(@NonNull V view) {
        if (subscriptions != null) {
            if (subscriptions.isUnsubscribed()) {
                subscriptions.unsubscribe();
            }
            if (subscriptions.hasSubscriptions()) {
                subscriptions.clear();
            }
            subscriptions = null;
        }
        this.view = null;
    }

    @CallSuper
    public void unbindView() {
        unbindView(view);
    }
}
