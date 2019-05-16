package cn.com.xg.log.design.build;

//Adapter 转换器
//继承模式
public class Converter extends Vm220V implements Vm5V {
    @Override
    public String provide5VBattery() {
        String batteryOf220v = provide220VBattery();
        System.out.println("220V转换为5v");
        return "5V";
    }
}
//委托的模式，使用组合来替代继承。
class HasConverter implements Vm5V{
    private Vm220V vm220V;
    public HasConverter(Vm220V vm220V){
        this.vm220V = vm220V;
    }
    @Override
    public String provide5VBattery() {
        String battery220 = vm220V.provide220VBattery();
        System.out.println("220V转换为5V成功");
        return "5V";
    }
}
