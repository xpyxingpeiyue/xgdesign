package cn.com.xg.log.design;

//遍历、switch 等常用操作
public class EnumBuild {
    private static final EnumBuild.WeekDay weekDay;

    static {
        weekDay = WeekDay.THU;
    }

    private static enum WeekDay {
        MON, TUE, WED, THU, FRI, SAT, SUN;

        private WeekDay() {
        }
    }

    public static void main(String[] args) {
        switch (weekDay) {
            case MON:
                System.out.println("周一");
                break;
            case TUE:
                System.out.println("周二");
                break;
            case THU:
                System.out.println("周四");
                break;
            default:
                System.out.println("未知");
        }
    }
}
