package cn.org.mytest.data;

import cn.org.mytest.dateutile.DateUtils;

import java.util.Random;

public class DataUtils {
    public static void moke() {
        Random random = new Random();
        // 大陆地区
        String[] locations = new String[]
                {"京", "津", "渝", "沪",
                        "冀", "晋",
                        "辽", "吉", "黑",
                        "苏", "浙", "皖",
                        "闽", "赣", "鲁", "豫", "鄂", "湘",
                        "粤", "琼", "川", "黔", "云",
                        "陕", "甘", "青", "内蒙古", "桂", "宁", "新", "藏"};
        // 获取今天日期
        String todayDate = DateUtils.getTodayDate();
    }
}
