package cn.com.xg.log.design;

import cn.com.xg.log.design.build.Converter;
import cn.com.xg.log.design.build.Vm5V;

public class AdapterBuild {
    public static void main(String[] args) {
        Vm5V vm5V = new Converter();
        String result = vm5V.provide5VBattery();
        System.out.println(result);
    }
}
