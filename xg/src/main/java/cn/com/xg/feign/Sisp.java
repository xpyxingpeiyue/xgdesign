package cn.com.xg.feign;

import feign.Headers;
import feign.RequestLine;

import java.util.Map;

public interface Sisp {
    @Headers("Content-type: text/xml")
    @RequestLine("POST ")
    String query(Map<String, String> params);
}
