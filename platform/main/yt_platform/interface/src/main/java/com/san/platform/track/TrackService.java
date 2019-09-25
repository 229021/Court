package com.san.platform.track;

import com.san.platform.trajectory.Trajectory;
import com.san.platform.visitor.Visitor;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

public interface TrackService {
    //查询轨迹
    List<Trajectory> queryAllTrack(Integer trackId);

    //去除重复MapId 获取唯一
    List<Trajectory> queryAllTrackmapIdCk(Integer trackId);

    //查询轨迹之后获取MapId查询
    List<Trajectory> queryAllTrackmapId(Integer trackId,Integer mapId);


    /**
     * 轨迹添加
     * @param idnumber
     * @param deviceIP
     * @param small
     * @param big
     * @return
     */
    public int createTrack (String idnumber,String deviceIP,String small,String big);

    /**
     * 查询当前这个人trackPerId在时间间隔内是否有抓拍
     * @param trackPerId
     * @return
     */
    public Integer queryTrackByPerId (int trackPerId);
    //以图搜图查询
    HashMap<String, Object> queryAllTrackByPic (String idNumber, String startTime, String endTime, String visitCause, Integer regionId, Integer pageNum);
}
