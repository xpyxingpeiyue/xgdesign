package com.sf.whht.domain.repository;

import com.sf.whht.domain.EventDomain;
import com.sf.whht.domain.EventRepository;
import com.sf.whht.event.IEvent;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

//@Service("eventRepository")
public class EventRepositoryImpl implements EventRepository {
    @Resource
//    private KafkaTemplate<String, IEvent> kafkaTemplate;

    @Override
    public EventDomain load(IEvent id) {
        return new EventDomain(id);
    }

    @Override
    public boolean save(EventDomain domain) {
        System.out.println("saved in DB");
        return send(domain.getEvent());
    }

    @Override
    public boolean delete(IEvent id) {
        return false;
    }

    private boolean send(IEvent event) {
//        kafkaTemplate.send(event.getClass().getName(), String.valueOf(event.hashCode()), event);
        return true;
    }
}
