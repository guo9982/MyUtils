package cn.org.mytest;

import cn.org.mytest.dateutile.MyDate;
import org.junit.Test;

public class Test1 {

    @Test
    public void test1() {
        System.out.println(MyDate.getDays(2019, 12, 1));
    }
}
