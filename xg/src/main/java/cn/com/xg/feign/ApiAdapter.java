package cn.com.xg.feign;

import cn.hutool.core.map.MapUtil;
import cn.hutool.json.JSONUtil;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import feign.Util;
import feign.codec.Decoder;
import feign.codec.Encoder;

import java.io.IOException;
import java.io.Reader;
import java.util.Map;

public class ApiAdapter {
    public static Api createJson(){
        return JsonFeign.create();
    }
    public static Api createSoap(){
        return SoapFeign.create();
    }
    public static class JsonFeign implements Api {
        private static JsonFeign create(){
            return new JsonFeign();
        }
        private static Gson gson;

        private JsonFeign() {
        }

        static {
            gson = new Gson();
        }

        @Override
        public Encoder encoder() {
            return (object, bodyType, template) -> template.body(gson.toJson(object, bodyType));
        }

        @Override
        public Decoder decoder() {
            return (response, type) -> {
                if (response.status() == 404)
                    return Util.emptyValueOf(type);
                if (response.body() == null)
                    return null;
                Reader reader = response.body().asReader();
                try {
                    return gson.fromJson(reader, type);
                } catch (JsonIOException e) {
                    if (e.getCause() != null && e.getCause() instanceof IOException) {
                        throw IOException.class.cast(e.getCause());
                    }
                    throw e;
                } finally {
                    reader.close();
                }
            };
        }
    }

    public static class SoapFeign implements Api {
        private SoapFeign() {

        }
        private static SoapFeign create(){
            return new SoapFeign();
        }
        @Override
        public Encoder encoder() {
            return (object, bodyType, template) -> {
                Map<String,String> map = (Map<String,String>)object;
                String xml = buildRequestData(map,"queryGwAcceptCriteria");
                template.body(xml);
            };
        }

        @Override
        public Decoder decoder() {
            return (response, type) -> {
                if (response.status() == 404)
                    return Util.emptyValueOf(type);
                if (response.body() == null)
                    return null;
                Reader reader = response.body().asReader();
                try {
                    return Util.toString(reader);
                } catch (JsonIOException e) {
                    if (e.getCause() != null && e.getCause() instanceof IOException) {
                        throw IOException.class.cast(e.getCause());
                    }
                    throw e;
                } finally {
                    reader.close();
                }
            };
        }
        private String buildRequestData(Map<String, String> patameterMap,String methodName) {
            return "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
                    "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" " +
                    "xmlns:wsdl=\"http://www.sf-express.com/cmdm/wsdl\">" +
                    "<soapenv:Header/>" + "<soapenv:Body>" + "<wsdl:" + methodName + ">" +
                    JSONUtil.toXmlStr(JSONUtil.parse(patameterMap)) +
                    "</wsdl:" + methodName + ">" +
                    "</soapenv:Body>" +
                    "</soapenv:Envelope>";
        }
    }
}
