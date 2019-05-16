package cn.com.xg.feign;

import cn.hutool.json.JSONObject;
import com.google.gson.JsonObject;
import feign.Headers;
import feign.RequestLine;

import java.util.Map;

public interface Osms {
    @Headers("Content-type: application/json")
    @RequestLine("POST osms/services/restful/order/checkstay")
    JsonObject query(JSONObject jsonObject);
}
