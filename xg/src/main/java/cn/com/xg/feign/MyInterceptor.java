package cn.com.xg.feign;

import cn.hutool.json.JSONUtil;
import feign.Request;
import feign.RequestInterceptor;
import feign.RequestTemplate;

import java.nio.charset.Charset;
import java.util.Map;

public class MyInterceptor implements RequestInterceptor {
    private Map<String, String> patameterMap;
    private String methodName;
    public MyInterceptor(Map<String, String> patameterMap,String methodName){
        super();
        this.patameterMap = patameterMap;
        this.methodName = methodName;
    }
    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.body(Request.Body.encoded(this.buildRequestData().getBytes(),Charset.forName("utf-8")));
    }
    private String buildRequestData() {
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
