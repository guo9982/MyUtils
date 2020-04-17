package cn.org.mytest.dateutile;

import java.time.LocalDate;
import java.time.Period;

public class MyDate {
    public static String getDays(int year,int month,int day) {
        // 获取当前时间
        LocalDate nowDate = LocalDate.now();
        // 构建指定日期
        LocalDate dirtDate = LocalDate.of(year, month, day);

        Period period = Period.between(nowDate, dirtDate);

        int intYear = period.getYears();
        int intMonth = period.getMonths();
        int intDay = period.getDays();

        StringBuffer sb;
        sb = new StringBuffer();
        sb.append(year).append("年").append(month).append("月").append(day).append("日比今天");
        if (intYear > 0 || (intYear == 0 && intMonth > 0) || (intYear == 0 && intMonth == 0 && intDay > 0)) {
            sb.append("晚");
        } else{
            sb.append("早");
        }

        sb.append(intYear).append("年").append(intMonth).append("月").append(intDay).append("日");
        // 获取相隔天数
        return sb.toString();
    }
}
