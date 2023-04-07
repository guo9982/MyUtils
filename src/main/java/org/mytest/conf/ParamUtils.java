package org.mytest.conf;

import org.mytest.constant.Constants;

public class ParamUtils {
    /**
     * 从命令行参数中提取任务id
     * @param args 命令行参数
     * @param taskType 参数类型(任务id对应的值是Long类型才可以)，对应my.properties中的key
     * @return 任务id
     *    spark.local.taskId.monitorFlow
     */
    public static Long getTaskIdFromArgs(String[] args, String taskType) {
        boolean local = ConfigurationManager.getBoolean(Constants.SPARK_LOCAL);

        if(local) {
            return ConfigurationManager.getLong(taskType);
        } else {
            try {
                if(args != null && args.length > 0) {
                    return Long.valueOf(args[0]);
                }else {
                    System.out.println("集群提交任务，需要参数");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return 0L;
    }
}
