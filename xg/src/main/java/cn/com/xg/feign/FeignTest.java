package cn.com.xg.feign;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import feign.Feign;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FeignTest {
    public static void main(String[] args){
//        GitHub github = Feign.builder()
////                .decoder(new GsonDecoder())
//                .target(GitHub.class, "https://api.github.com");
//        // Fetch and print a list of the contributors to this library.
//        List<Contributor> contributors = github.contributors("OpenFeign", "feign");
//        for (Contributor contributor : contributors) {
//            System.out.println(contributor.login + " (" + contributor.contributions + ")");
//        }
//        xmlTest();
        jsonTest();
    }
    public static void jsonTest(){
        Osms osms = Feign.builder()
                .encoder(ApiFactory.jsonApi().encoder())
                .decoder(ApiFactory.jsonApi().decoder())
                .target(Osms.class,"http://osms.sit.sf-express.com:2080");
        JSONObject params = JSONUtil.createObj();
        params.put("operator", "1");
        params.put("source", "WHHT");
        params.put("local", "zh_cn");
        JSONObject obj = JSONUtil.createObj();
        obj.put("clientCode", "fc34c561a34f");
        obj.put("mailno", "SF1000115725203");
        obj.put("submit_account", "OSMS_1");
        params.put("obj", obj);

        JsonObject result = osms.query(params);
        System.out.println(result.get("isSuccess"));
        System.out.println(result.getAsJsonObject("data"));
    }
    public static void xmlTest(){
        Sisp sisp = Feign.builder()
                .encoder(ApiFactory.soapApi().encoder())
                .decoder(ApiFactory.soapApi().decoder())
                .target(Sisp.class, "http://10.202.40.40:8080");
        Map<String,String> params = new HashMap<>();
        String result = sisp.query(params);
        System.out.println(result);

    }
    private static String buildRequestData() {
        return "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:web=\"http://webservice.sispint.sisp.sf.com/\">\n" +
                "   <soapenv:Header/>\n" +
                "   <soapenv:Body>\n" +
                "      <web:queryWaybillInfo>\n" +
                "         <!--Optional:-->\n" +
                "         \n" +
                "      <arg0>{ \"contextOrder\": \"00001\", \"language\": \"zh_CN\", \"sysCode\": \"SHIVA-SISP-INT\", \"userId\": \"80003301\", \"clientCode\": \"lijing\", \"waybillNos\": [     \"SF1000122077366\" ] } </arg0></web:queryWaybillInfo>\n" +
                "   </soapenv:Body>\n" +
                "</soapenv:Envelope>";
    }
}
