package cn.com.xg.feign;

public class ApiFactory {
    public static Api jsonApi(){
        return ApiAdapter.createJson();
    }
    public static Api soapApi(){
        return ApiAdapter.createSoap();
    }
}
