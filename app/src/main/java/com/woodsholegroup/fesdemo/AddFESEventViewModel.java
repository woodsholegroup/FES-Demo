package com.woodsholegroup.fesdemo;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

/**
 * Created by ltossou on 8/25/2018.
 *
 * @author ltossou
 */
public class AddFESEventViewModel extends ViewModel {

    private MutableLiveData<List<FESEvent>> events;

    public AddFESEventViewModel() {
        super();
        events = new MutableLiveData<>();
    }

    public void addEvent(FESEvent event) {
        events.getValue().add(event);
    }

    public MutableLiveData<List<FESEvent>> getEvents() {
        if (events == null) {
            events = new MutableLiveData<>();
            // loadEvents
        }
        return events;
    }
}
