package com.fantasygame.listener;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by HP on 2/5/2017.
 */

public class EventBusListener {
    private static EventBus eventBus;

    public static EventBus getInstance(){
        if (eventBus == null) {
            eventBus = EventBus.getDefault();
        }
        return eventBus;
    }
}
