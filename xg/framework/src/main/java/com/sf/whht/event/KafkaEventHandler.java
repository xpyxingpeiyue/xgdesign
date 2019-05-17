package com.sf.whht.event;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.IterUtil;

import java.lang.reflect.Method;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.List;


public class KafkaEventHandler extends EventHandler {
    @Override
    public void process(IEvent event) {
        // do nothing
    }

    @Override
    public void register(Class<? extends IEvent> eventClass, String group, Object bean, Method method) {
        // TODO 粗暴地实现kafka的事件驱动，每次追加消费者都要初始化连接，需要改进
        /*KafkaListenerAnnotationBeanPostProcessor postProcessor = new KafkaListenerAnnotationBeanPostProcessor() {
            @Override
            public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
                this.processKafkaListener(createKafkaListener(eventClass, group, method), method, bean, beanName);
                return bean;
            }
        };

        postProcessor.setBeanFactory(getBeanFactory());
        postProcessor.postProcessAfterInitialization(bean, bean.getClass().getName());
        postProcessor.afterSingletonsInstantiated();*/
    }

    // spring-kafka的监听注解
    /*private KafkaListener createKafkaListener(Class<? extends IEvent> eventClass, String group, Method method) {
        return new KafkaListener() {

            @Override
            public Class<? extends Annotation> annotationType() {
                return KafkaListener.class;
            }

            @Override
            public String id() {
                return method.toGenericString();
            }

            @Override
            public String containerFactory() {
                return "";
            }

            @Override
            public String[] topics() {
                return new String[]{eventClass.getName()};
            }

            @Override
            public String topicPattern() {
                return "";
            }

            @Override
            public TopicPartition[] topicPartitions() {
                return new TopicPartition[0];
            }

            @Override
            public String containerGroup() {
                return "";
            }

            @Override
            public String errorHandler() {
                return "";
            }

            @Override
            public String groupId() {
                return (null == group || group.trim().length() == 0) ? generateGroupId() : group;
            }

            @Override
            public boolean idIsGroup() {
                return true;
            }

            @Override
            public String clientIdPrefix() {
                return "";
            }

            @Override
            public String beanRef() {
                return "__listener";
            }
        };
    }*/

    private String generateGroupId() {
        // 返回相对不变的MAC地址作为groupId，防止每次重启都会消费已消费的事件
        return MacHolder.getMac();
    }

    private static class MacHolder {
        private static String mac;

        /**
         * 生成本机MAC地址，这个值是多个网卡MAC地址的组合
         */
        private synchronized static String build() {
            try {
                Enumeration<NetworkInterface> enumeration = NetworkInterface.getNetworkInterfaces();
                List<String> stringList = CollUtil.newArrayList();
                IterUtil.asIterator(enumeration).forEachRemaining(networkInterface -> {
                    try {
                        StringBuilder stringBuilder = new StringBuilder();
                        byte[] bytes = networkInterface.getHardwareAddress();
                        if (bytes != null) {
                            for (int i = 0; i < bytes.length; i++) {
                                if (i != 0) {
                                    stringBuilder.append("-");
                                }
                                int tmp = bytes[i] & 0xff; // 字节转换为整数
                                String str = Integer.toHexString(tmp);
                                if (str.length() == 1) {
                                    stringBuilder.append("0").append(str);
                                } else {
                                    stringBuilder.append(str);
                                }
                            }
                            String mac = stringBuilder.toString().toUpperCase();
                            stringList.add(mac);
                        }
                    } catch (SocketException e) {
                        e.printStackTrace();
                    }
                });
                return mac = CollUtil.join(stringList, ",");
            } catch (SocketException e) {
                e.printStackTrace();
            }
            return null;
        }

        static String getMac() {
            return mac == null ? build() : mac;
        }
    }
}
