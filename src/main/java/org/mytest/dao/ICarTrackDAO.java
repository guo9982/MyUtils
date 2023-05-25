package org.mytest.dao;

import java.util.List;

import com.mujie.spark.domain.CarTrack;

public interface ICarTrackDAO {
	
	/**
	 * 批量插入车辆轨迹信息
	 * @param carTracks
	 */
	void insertBatchCarTrack(List<CarTrack> carTracks);
}
