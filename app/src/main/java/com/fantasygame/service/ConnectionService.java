package com.fantasygame.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fantasygame.define.Constant;
import com.fantasygame.utils.NetworkUtil;

/**
 * Created by Kiet Nguyen on 12-Jan-17.
 */

public class ConnectionService {

    private UpdateReceiver updateReceiver;
    private Context context;
    private TextView tvConnection;
    private FrameLayout frame_container;
    private LinearLayout layout_tabs;
    private postResultConnection listenner;

    public interface postResultConnection {
        void postResultConnection(boolean isConnection);
    }

    public ConnectionService(Context context, TextView tvConnection, FrameLayout frame_container, postResultConnection listenner) {
        this.context = context;
        this.tvConnection = tvConnection;
        this.frame_container = frame_container;
        this.layout_tabs = layout_tabs;
        this.listenner = listenner;
    }

    public ConnectionService(Context context, postResultConnection listenner) {
        this.context = context;
        this.listenner = listenner;
    }

    public void registerBroadcastConnection() {
        if (updateReceiver == null) {
            updateReceiver = new UpdateReceiver() {
                @Override
                public void handleConnection(int status) {
                    if (status == NetworkUtil.NETWORK_STATUS_NOT_CONNECTED) {
                        setupUI(false);
                    } else {
                        setupUI(true);
                    }
                }
            };
        }
        IntentFilter intentFilter = new IntentFilter(Constant.ACTION_WIFI_CONECTION);
        intentFilter.addAction(Constant.ACTION_CONNECTION);
        context.registerReceiver(updateReceiver, intentFilter);
    }

    public void unregisterBroadcastConnection(Context context) {
        if (updateReceiver != null) {
            context.unregisterReceiver(updateReceiver);
            updateReceiver = null;
        }
    }

    public class UpdateReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            int status = NetworkUtil.getConnectivityStatusString(context);
            handleConnection(status);
        }

        public void handleConnection(int status) {
        }
    }

    private void setupUI(boolean isConnected) {
        if(tvConnection!=null)tvConnection.setVisibility(isConnected ? View.GONE : View.VISIBLE);
        if(frame_container!=null)frame_container.setVisibility(isConnected ? View.VISIBLE : View.GONE);
        listenner.postResultConnection(isConnected ? true : false);
    }
}
