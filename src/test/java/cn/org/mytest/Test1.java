package cn.org.mytest;

import cn.org.mytest.dateutile.DateUtils;
import org.junit.Test;

public class Test1 {

    @Test
    public void test1() {

        System.out.println(DateUtils.getDays(2020, 12, 1));
    }

    @Test
    public void test2() {
        System.out.println(DateUtils.getTodayDate());

    }
}
