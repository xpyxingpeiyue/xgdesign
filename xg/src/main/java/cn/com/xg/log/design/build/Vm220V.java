package cn.com.xg.log.design.build;

////Adaptee  提供业务方法
public class Vm220V {
    public String provide220VBattery() {
        System.out.println("提供220V电流");
        return "220V";
    }
}