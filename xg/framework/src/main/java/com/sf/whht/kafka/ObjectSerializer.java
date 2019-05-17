package com.sf.whht.kafka;

import com.alibaba.dubbo.common.serialize.support.kryo.KryoFactory;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

@SuppressWarnings("unused")
public class ObjectSerializer implements Serializer<Object>, Deserializer<Object> {
    @Override
    public void configure(Map<String, ?> map, boolean b) {
    }

    @Override
    public Object deserialize(String s, byte[] bytes) {
        Input input = new Input(bytes);
        try {
            return KryoFactory.getDefaultFactory().getKryo().readClassAndObject(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public byte[] serialize(String s, Object o) {
        Output output = new Output(4096);
        KryoFactory.getDefaultFactory().getKryo().writeClassAndObject(output, o);
        return output.toBytes();
    }

    @Override
    public void close() {
    }
}
