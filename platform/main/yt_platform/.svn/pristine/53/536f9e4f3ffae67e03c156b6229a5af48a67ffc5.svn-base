package com.san.platform.track.mapper;

import com.san.platform.track.Track;
import com.san.platform.trajectory.Trajectory;
import com.san.platform.visitor.Visitor;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface TrackMapper extends Mapper<Track> {
    //查询轨迹
    List<Trajectory> queryAllTrack(Integer trackId);

    //去除重复MapId 获取唯一
    List<Trajectory> queryAllTrackmapIdCk(Integer trackId);

    //查询轨迹之后获取MapId查询
    List<Trajectory> queryAllTrackmapId(Integer trackId,Integer mapId);

    /**
     * 查询当前这个人trackPerId在时间间隔内是否有抓拍
     * @param trackPerId
     * @return
     */
    public Integer queryTrackByPerId (int trackPerId);
    //以图搜图查询
    List<Visitor> queryAllTrackByPic (@Param("idNumber") String idNumber,
                                      @Param("startTime") String startTime,
                                      @Param("endTime") String endTime,
                                      @Param("visitCause") String visitCause,
                                      @Param("regionId") Integer regionId);
}
