package cn.org.mytest.dateutile;

import java.time.LocalDate;
import java.time.Period;

public class MyDate {
    public static int getDay(int year,int mouth,int day) {
        // 获取当前时间
        LocalDate nowDate = LocalDate.now();
        // 构建指定日期
        LocalDate dirtDate = LocalDate.of(year, mouth, day);

        Period period = Period.between(nowDate, dirtDate);

        // 获取相隔天数
        return period.getDays();
    }
}
