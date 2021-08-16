package cn.org.mytest.data;

import cn.org.mytest.dateutile.DateUtils;
import cn.org.mytest.string.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

public class DataUtils {

    private static final Logger logger = LoggerFactory.getLogger(DataUtils.class);

    public static void moke(int len) {
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

        for (int i = 0; i < len; i++) {
            String car = locations[(random.nextInt(locations.length))] + (char) (65 + random.nextInt(26)) + StringUtil.getStrWithLength(10);

            String baseActionTime = todayDate + " " + StringUtil.fulfuill(random.nextInt(24)+"");

            logger.info(baseActionTime +"###"+ car);
            /*
             * 此处的代码模拟车辆行驶轨迹
             */
            for (int j = 0; j < random.nextInt(300) + 1; j++) {
                // 区域id
                String areaId = StringUtil.fulfuill(random.nextInt(15)+1+"");
                // 道路id
                String rowId = random.nextInt(50)+1+"";
                // 摄像头id

            }

        }

    }
}
