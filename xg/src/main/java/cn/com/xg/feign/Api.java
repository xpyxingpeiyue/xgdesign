package cn.com.xg.feign;

import feign.codec.Decoder;
import feign.codec.Encoder;

public interface Api {
    Encoder encoder();
    Decoder decoder();
}
