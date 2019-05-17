package com.sf.whht.event;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DefaultEventHandler extends EventHandler {
    private ConcurrentMap<Class<? extends IEvent>, ConcurrentMap<String, List<IEventListener>>> eventListenerMap = new ConcurrentHashMap<>();

    @Override
    public void process(IEvent event) {
        if (event != null) {
            List<IEventListener> listenerList = getListenerList(event.getClass());
            // 动态监听并触发
            EventBridge.create(event).addListener(listenerList.toArray(new IEventListener[0])).notifyListeners();
        }
    }

    @Override
    public void register(Class<? extends IEvent> eventClass, String group, Object bean, Method method) {
        addListener(eventClass, group, event -> {
            try {
                method.invoke(bean, event); // 触发时执行回调方法
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 给事件以组的形式追加监听者
     */
    private void addListener(Class<? extends IEvent> eventClass, String group, IEventListener eventListener) {
        ConcurrentMap<String, List<IEventListener>> map = eventListenerMap.computeIfAbsent(eventClass, k -> new ConcurrentHashMap<>());
        List<IEventListener> listenerList = map.computeIfAbsent(group, k -> new ArrayList<>());
        listenerList.add(eventListener);
    }

    /**
     * 获取监听者列表，每组随便返回一个监听者
     */
    private List<IEventListener> getListenerList(Class<? extends IEvent> eventClass) {
        return getListenerList(eventClass, iEventListeners -> iEventListeners == null || iEventListeners.isEmpty()
                ? null : iEventListeners.get(ThreadLocalRandom.current().nextInt(iEventListeners.size())));
    }

    /**
     * 获取监听者列表，每组一个监听者
     */
    private List<IEventListener> getListenerList(Class<? extends IEvent> eventClass, Function<List<IEventListener>, IEventListener> function) {
        ConcurrentMap<String, List<IEventListener>> map
                = eventListenerMap.computeIfAbsent(eventClass, k -> new ConcurrentHashMap<>());
        return map.values().stream().map(function).filter(Objects::nonNull).collect(Collectors.toList());
    }
}
