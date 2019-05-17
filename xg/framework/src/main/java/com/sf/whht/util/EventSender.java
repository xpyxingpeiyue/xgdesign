package com.sf.whht.util;

import com.sf.whht.domain.EventDomain;
import com.sf.whht.domain.EventRepository;
import com.sf.whht.event.IEvent;

public class EventSender {
    private static final EventRepository eventRepository = SpringContextUtil.getBean(EventRepository.class);

    private EventSender() {
    }

    public static boolean send(IEvent event) {
        EventDomain eventDomain = eventRepository.load(event);
        return eventDomain != null && eventRepository.save(eventDomain);
    }
}
