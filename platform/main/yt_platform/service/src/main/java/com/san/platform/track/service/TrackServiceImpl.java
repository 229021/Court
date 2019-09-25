package com.san.platform.track.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.san.platform.config.Global;
import com.san.platform.device.Device;
import com.san.platform.device.mapper.DeviceMapper;
import com.san.platform.track.Track;
import com.san.platform.track.TrackService;
import com.san.platform.track.mapper.TrackMapper;
import com.san.platform.trajectory.Trajectory;
import com.san.platform.visitor.Visitor;
import com.san.platform.visitor.mapper.VisitorMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.HashMap;
import java.util.List;


@Service
public class TrackServiceImpl implements TrackService {

    @Autowired
    private TrackMapper trackMapper;
    @Autowired
    private VisitorMapper visitorMapper;
    @Autowired
    private DeviceMapper deviceMapper;

    //查询轨迹
    @Override
    public List<Trajectory> queryAllTrack(Integer trackId) {
        return trackMapper.queryAllTrack(trackId);
    }

    @Override
    public List<Trajectory> queryAllTrackmapIdCk(Integer trackId) {
        return trackMapper.queryAllTrackmapIdCk(trackId);
    }

    //查询轨迹之后获取mapId查询
    @Override
    public List<Trajectory> queryAllTrackmapId(Integer trackId, Integer mapId) {
        return trackMapper.queryAllTrackmapId(trackId,mapId);
    }

    /**
     * 根据比对的身份证信息-查询今日来访的人员信息
     * @param idnumber
     * @return
     */
    public Visitor queryVisitorByIDNo (String idnumber) {
        Visitor visitor = new Visitor();
        visitor = visitorMapper.queryVisitorByIDNo(idnumber);
        return visitor;
    }

    /**
     * 添加轨迹
     * @param idnumber
     * @param deviceIP
     * @param small
     * @param big
     * @return
     */
    @Override
    public int createTrack (String idnumber,String deviceIP,String small,String big) {
        int result = 0;
        Visitor visitor = visitorMapper.queryVisitorIdByIDNoToday(idnumber);
        Device device = deviceMapper.queryDeviceByIP(deviceIP);

        if (visitor!=null && device!=null) {
            Track track = new Track();
            track.setTrackDate(new Date());
            track.setTrackDeviceId(device.getDeviceId());
            track.setTrackPerId(visitor.getVisitId());
            track.setTrackFaceImageBig(big);
            track.setTrackFaceImageSmail(small);
            Integer interval = this.queryTrackByPerId(visitor.getVisitId());
            if (interval == null || interval>=1) {
                result = trackMapper.insertSelective(track);
            }
        }
        return result;
    }
    /**
     * 查询当前这个人trackPerId在时间间隔内是否有抓拍
     * @param trackPerId
     * @return
     */
    public Integer queryTrackByPerId (int trackPerId){
        return trackMapper.queryTrackByPerId(trackPerId);
    }

    /**
     * 白鹏 以图搜图
     * @param idNumber
     * @param startTime
     * @param endTime
     * @param visitCause
     * @param regionId
     * @return
     */
    @Override
    public HashMap<String, Object> queryAllTrackByPic(String idNumber, String startTime, String endTime, String visitCause, Integer regionId, Integer pageNum) {
        HashMap<String, Object> map = new HashMap<>();
        // 当前页码 pageNum 与 每页显示条数 pageSize分页
        PageHelper.startPage(pageNum, Global.PAGESIZE);
        List<Visitor> visitors = trackMapper.queryAllTrackByPic(idNumber, startTime, endTime, visitCause, regionId);
        // 得到page相关的info
        PageInfo pageInfo = new PageInfo(visitors);
        // 将list与相关的page信息传入map到web端
        map.put("pages",new Integer(pageInfo.getPages()));
        map.put("total",pageInfo.getTotal());
        map.put("results",visitors);
        return map;
    }


}
