package cn.org.mytest;

import cn.org.mytest.dateutile.DateUtils;
import org.junit.Test;

import java.time.LocalDate;

public class Test1 {

    @Test
    public void test1() {

        System.out.println(DateUtils.getDays(2020, 12, 1));
    }

    @Test
    public void test2() {
        System.out.println(DateUtils.getTodayDate());

    }

    @Test
    public void test3() {
        System.out.println(DateUtils.getDays("2020-04-04", "2020-04-05"));
    }
}
