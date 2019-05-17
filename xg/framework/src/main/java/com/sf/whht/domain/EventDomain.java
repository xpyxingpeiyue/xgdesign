package com.sf.whht.domain;

import com.sf.whht.event.IEvent;

public class EventDomain implements Domain {
    private IEvent event;

    public EventDomain(IEvent event) {
        this.event = event;
    }

    public IEvent getEvent() {
        return event;
    }
}
