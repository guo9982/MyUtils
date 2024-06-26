package com.spark.spark.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.*;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructType;

import com.mujie.spark.util.DateUtils;
import com.mujie.spark.util.StringUtils;


/**
 * 模拟数据  数据格式如下：
 * 
 *  日期	      卡口ID		     摄像头编号  	车牌号	       拍摄时间	              车速	             道路ID   	   区域ID
 * date	 monitor_id	 camera_id	 car	action_time		speed	road_id		area_id
 * 
 * monitor_flow_action
 * monitor_camera_info
 * 
 * @author Administrator
 */
public class MockData {
    public static void mock(JavaSparkContext sc, SparkSession spark){
    	List<Row> dataList = new ArrayList<>();
    	Random random = new Random();
    	
    	String[] locations = new String[]{"鲁","京","粤","京","沪","京","京","深","京","京"};
//     	String[] areas = new String[]{"海淀区","朝阳区","昌平区","东城区","西城区","丰台区","顺义区","大兴区"};
    	//date :如：2019-06-27
    	String date = DateUtils.getTodayDate();
    	
    	/**
    	 * 模拟3000个车辆
    	 */
    	for (int i = 0; i < 3000; i++) {
    		//模拟车牌号：如：京A00001
        	String car = locations[random.nextInt(10)] + (char)(65+random.nextInt(26))+StringUtils.fulfuill(5,random.nextInt(100000)+"");
        	
        	//baseActionTime 模拟24小时
        	String baseActionTime = date + " " + StringUtils.fulfuill(random.nextInt(24)+"");//2019-06-27 08
        	/**
        	 * 这里的for循环模拟每辆车经过不同的卡扣不同的摄像头 数据。
        	 */
        	for(int j = 0 ; j < (random.nextInt(300)+1) ; j++){
        		//模拟每个车辆每被30个摄像头拍摄后 时间上累计加1小时。这样做使数据更加真实。
        		if(j % 30 == 0 && j != 0){
					Integer addOneHourTime = Integer.parseInt(baseActionTime.split(" ")[1])+1;
					if(addOneHourTime == 24){
						addOneHourTime = 0;
					}
					baseActionTime = date + " " + StringUtils.fulfuill(addOneHourTime+"");
				}
        		
        		String areaId = StringUtils.fulfuill(2,random.nextInt(8)+1+"");//模拟areaId 【一共8个区域】
        		
        		String roadId = random.nextInt(50)+1+"";//模拟道路id 【1~50 个道路】

        		String monitorId = StringUtils.fulfuill(4, random.nextInt(9)+"");//模拟9个卡扣monitorId，0补全4位
        		
        		String cameraId = StringUtils.fulfuill(5, random.nextInt(100000)+"");//模拟摄像头id cameraId
        		
        		String actionTime = baseActionTime + ":" 
        				+ StringUtils.fulfuill(random.nextInt(60)+"") + ":" 
        				+ StringUtils.fulfuill(random.nextInt(60)+"");//模拟经过此卡扣开始时间 ，如：2019-06-27 08:10:20
        		
        		String speed = (random.nextInt(260)+1)+"";//模拟速度
        		
        		Row row = RowFactory.create(date,monitorId,cameraId,car,actionTime,speed,roadId,areaId);
        		dataList.add(row);
        	}
		}

    	JavaRDD<Row> rowRdd = sc.parallelize(dataList);
    	
    	StructType cameraFlowSchema = DataTypes.createStructType(Arrays.asList(
    			DataTypes.createStructField("date", DataTypes.StringType, true),
    			DataTypes.createStructField("monitor_id", DataTypes.StringType, true),
    			DataTypes.createStructField("camera_id", DataTypes.StringType, true),
    			DataTypes.createStructField("car", DataTypes.StringType, true),
    			DataTypes.createStructField("action_time", DataTypes.StringType, true),
    			DataTypes.createStructField("speed", DataTypes.StringType, true),
    			DataTypes.createStructField("road_id", DataTypes.StringType, true),
    			DataTypes.createStructField("area_id", DataTypes.StringType, true)
    			));

    	Dataset<Row> ds = spark.createDataFrame(rowRdd, cameraFlowSchema);
    	
    	//默认打印出来df里面的20行数据
    	System.out.println("----打印 车辆信息数据----");
		ds.show();
		ds.createOrReplaceTempView("monitor_flow_action");
//		ds.registerTempTable("monitor_flow_action");

    	/**
    	 * monitorAndCameras    key：monitor_id
    	 * 						value:hashSet(camera_id)
    	 * 基于生成的数据，生成对应的卡扣号和摄像头对应基本表
    	 */
    	Map<String,Set<String>> monitorAndCameras = new HashMap<>();
    	
    	int index = 0;
    	for(Row row : dataList){
    		//row.getString(1) monitor_id
    		Set<String> sets = monitorAndCameras.get(row.getString(1));
    		if(sets == null){
    			sets = new HashSet<>();
    			monitorAndCameras.put((String)row.getString(1), sets);
    		}
    		//这里每隔1000条数据随机插入一条数据，模拟出来标准表中卡扣对应摄像头的数据比模拟数据中多出来的摄像头。这个摄像头的数据不一定会在车辆数据中有。即可以看出卡扣号下有坏的摄像头。
    		index++;
    		if(index % 5000 == 0){
    			sets.add(StringUtils.fulfuill(5, random.nextInt(100000)+""));
    		} 
    		//row.getString(2) camera_id
			String cameraId = row.getString(2);
			sets.add(cameraId);
    	}
    	
    	dataList.clear();
    	
    	Set<Entry<String,Set<String>>> entrySet = monitorAndCameras.entrySet();
    	for (Entry<String, Set<String>> entry : entrySet) {
    		String monitor_id = entry.getKey();
    		Set<String> sets = entry.getValue();
    		Row row = null;
    		for (String camera_id : sets) {
    			row = RowFactory.create(monitor_id,camera_id);
    			dataList.add(row);
			}
		}

    	StructType monitorSchema = DataTypes.createStructType(Arrays.asList(
				DataTypes.createStructField("monitor_id", DataTypes.StringType, true),
				DataTypes.createStructField("camera_id", DataTypes.StringType, true)
		));
    	
    	
    	rowRdd = sc.parallelize(dataList);
    	Dataset<Row> monitorDF = spark.createDataFrame(rowRdd, monitorSchema);
    	monitorDF.createOrReplaceTempView("monitor_camera_info");
//    	monitorDF.registerTempTable("monitor_camera_info");
    	System.out.println("----打印 卡扣号对应摄像头号 数据----");
    	monitorDF.show();
    }
}














