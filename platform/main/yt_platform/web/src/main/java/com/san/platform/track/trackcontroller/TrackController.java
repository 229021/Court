package com.san.platform.track.trackcontroller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.san.platform.BaseController;
import com.san.platform.JSON.AnalysisFace;
import com.san.platform.JSON.FaceInfo;
import com.san.platform.JSON.SearchFace;
import com.san.platform.config.Global;
import com.san.platform.hikface.HikfaceService;
import com.san.platform.hikvision.HikvisonService;

import com.san.platform.option.Option;
import com.san.platform.option.OptionService;
import com.san.platform.region.Region;
import com.san.platform.region.RegionService;
import com.san.platform.track.TrackService;
import com.san.platform.trajectory.Trajectory;

import com.san.platform.visitor.VisitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
@Controller
public class TrackController extends BaseController {
    private final ResourceLoader resourceLoader;
    @Autowired
    public TrackController(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }
    @Value("${path.baseUrl}")
    private String baseUrl;
    @Value("${path.picpic}")
    private String picpic;
    @Value("${path.access}")
    private String access;
    @Value("${http.port}")
    private String port;
    @Value("${localhostip.path}")
    private String localPath;
    @Value("${http.path}")
    private String httppath;
    @Reference
    private HikfaceService hikfaceService;
    @Reference
    private VisitorService visitorService;
    @Reference
    private TrackService trackService;
    @Reference
    private HikvisonService hikvisonService;
    @Reference
    private OptionService optionService;
    @Reference
    private RegionService regionService;
    //查询轨迹
    @ResponseBody
    @RequestMapping(value = "/api/track/queryAllTrack")
    public HashMap<String, Object> queryAllTrack(Integer trackId,Integer mapId) {
        logger.info("[track.api]-[get]-[/api/track/queryAllTrack] method have been called.");
        HashMap<String, Object> map = new HashMap<>();
        try {
            List trajectorys = new ArrayList();
            // 查询轨迹
            List<Trajectory> trajectorys1 = trackService.queryAllTrack(trackId);
             // 去除重复MapId值
            List<Trajectory> trajectorys2 = trackService.queryAllTrackmapIdCk(trackId);
            for (Trajectory tra:trajectorys2) {
                HashMap<String, Object> hashmap = new HashMap<>();
                hashmap.put("mapId",tra.getMapId());
                trajectorys.add(hashmap);
            }
            if(mapId==0){
                mapId = trajectorys1.get(0).getMapId();
            }
            // 根据轨迹查询出来的第一张地图Id进行查询第一张地图轨迹
            List<Trajectory> trajectorysMap = trackService.queryAllTrackmapId(trackId,mapId);
            String point = "";
            String Imgpoint1 = "M ";
            for(Trajectory tra1:trajectorysMap){
                String objpoint =tra1.getMapX()+" "+tra1.getMapY() + ",";
                point += objpoint;
                String objpImgpoint1= tra1.getMapX()+" "+ tra1.getMapY()+" L ";
                Imgpoint1 += objpImgpoint1;
            }
            int x1 = trajectorysMap.get(0).getMapX();
            int y1 = trajectorysMap.get(0).getMapY();;
            int x2 = trajectorysMap.get(trajectorysMap.size() - 1).getMapX();
            int y2 = trajectorysMap.get(trajectorysMap.size() - 1).getMapY();
            double sum=Math.sqrt((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2));
            System.out.println();
            map.put("point", point);
            map.put("Imgpoint1", Imgpoint1);
            // 传入主值
            map.put("results", trajectorysMap);
            // 总共被哪些地图抓拍到
            map.put("resultsList", trajectorys);
            // 获取长度
            map.put("resultsListLength", trajectorys2);
            // 传入两点之间的距离
            map.put("resultsSum", sum);
            map.put("code", Global.CODE_SUCCESS);
        } catch (Exception e) {
            map.put("code", Global.CODE_DB_QUERY_EXCEPTION);
            logger.info("[track.api]-[get]-[/api/tra    ck/queryAll] CODE_DB_QUERY_EXCEPTION exception,sel_all:" + e.getMessage());
        }
        return map;
    }

    //查询列表所有信息
    @RequestMapping("/api/track/queryAllTrackByPic")
    @ResponseBody
    public HashMap<String, Object> queryAllTrackByPic() {
        logger.info("[track.api]-[get]-[/api/track/queryAllTrackByPic] method have been called.");
        HashMap<String, Object> map = new HashMap<>();

        try {
            //查询事由和场所
            List<Option> options = optionService.queryAllOption();
            List<Region> regions = regionService.queryAllRegion();
            map.put("regions",regions);
            map.put("options",options);
            map.put("code", Global.CODE_SUCCESS);
        } catch (Exception e) {
            map.put("code", Global.CODE_DB_QUERY_EXCEPTION);
            logger.info("[innerlog.api]-[get]-[/api/innerlog/queryAllInnerlog] CODE_DB_QUERY_EXCEPTION exception:" + e.getMessage());
        }
        return map;
    }

    /**
     * 白鹏 以图搜图
     * @param basepic
     * @param startTime
     * @param endTime
     * @return
     */
    @RequestMapping("fileUpload")
    @ResponseBody
    public  HashMap<String, Object> upload(@RequestParam("basepic") MultipartFile basepic,
                         @RequestParam("startTime") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startTime,
                         @RequestParam("endTime") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endTime,
                         @RequestParam("regionId")  Integer regionId,
                         @RequestParam("visitCause")  String visitCause,
                         @RequestParam("pageNum")  Integer pageNum,
                         @RequestParam("similarity")  Integer similarity) {
        HashMap<String, Object> map = new HashMap<>();
        logger.info("[track.api]-[get]-[/api/track/queryAllTrack] method have been called.");
        // 要上传的目标文件存放路径
        String localPath1 = baseUrl + picpic;
        // 上传成功或者失败的提示
        String msg = "";
        System.out.println(startTime);
        HashMap<String, Object> upload = FileUtils.upload(basepic, localPath1, basepic.getOriginalFilename());
        try{
            boolean isSign = (boolean) upload.get("isSign");
            if (isSign){
                // 上传成功，给出页面提示
                msg = "上传成功！";
                String path = (String)upload.get("path");
                String facePath =httppath+ localPath+":" + port + access + path;
                System.out.println(facePath);
//                facePath = "http://39.97.97.163:8080/bai1.jpg";888888
                String modeData = this.getModelData(facePath);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                String start = "";
                String end = "";
                if (startTime!=null&&("".equals(startTime)==false)){
                    start = simpleDateFormat.format(startTime);
                }
                if (endTime!=null&&("".equals(endTime)==false)){
                    end = simpleDateFormat.format(endTime);
                }
                if(modeData!=null&&!"".equals(modeData)) {
                    FaceInfo faceInfo = this.dealFaceInfo(modeData,similarity);
                    String certificateNumber = faceInfo.getCertificateNumber();
                    map = trackService.queryAllTrackByPic(certificateNumber, start, end, visitCause, regionId, pageNum);
                    map.put("code", Global.CODE_SUCCESS);
                } else {
                    map.put("code", Global.CODE_DATA_NOT_FOUND_EXCEPTION);
                }
            }else {
                msg = "上传失败！";
                map.put("code", Global.CODE_DB_QUERY_EXCEPTION);
            }
        }catch (Exception e){
            e.printStackTrace();
            map.put("code", Global.CODE_DB_QUERY_EXCEPTION);
            logger.info("[track.api]-[get]-[/api/track/queryAll] CODE_DB_QUERY_EXCEPTION exception,sel_all:" + e.getMessage());
        }
        System.out.println(map);
        return map;
    }

    /**
     * 人脸解析-返回modeDate
     *
     * @param smallSrc
     * @return
     */
    public String getModelData(String smallSrc) {
        System.out.println("com.san.platform.hikvison.hkmethod::getModelData人脸解析-返回modeDate");
        String modeDate = "";
        AnalysisFace analysisFace = new AnalysisFace();
        analysisFace.setImagesData(smallSrc);
        String faceinfo = JSONObject.toJSONString(analysisFace);
        String result = hikfaceService.FaceApi(faceinfo, 4);
        if (!result.equals("") && result != "") {
            JSONObject jsonObject = JSON.parseObject(result);
            Long code = Long.parseLong(jsonObject.getString("errorCode"));
            if (code == 1) {
                JSONArray array = jsonObject.getJSONArray("targets");
                for (int i = 0; i < array.size(); i++) {
                    JSONObject jsonTargets = JSONObject.parseObject("" + array.get(i));
                    modeDate = jsonTargets.getString("targetModelData");
                }
            } else {
                System.out.println("人脸解析-返回modeDate::人脸解析失败");
            }
        } else {
            System.out.println("人脸解析-返回modeDate:：人脸服务器登陆失败或地址参数错误");
        }
        return modeDate;
    }

    /**
     * 以图搜图-返回匹配的人员信息
     *
     * @param modeDate
     * @return
     */
    public FaceInfo dealFaceInfo(String modeDate,Integer similarity1) {
        FaceInfo faceInfo = null;
        SearchFace searchFace = new SearchFace();
        if(similarity1!=null&&!"".equals(similarity1)){
            double ss = similarity1/100.0;
            System.out.println(ss);
            searchFace.setMinSimilarity(ss);
        }
        searchFace.setTargetModelData(modeDate);
        String faceinfo = JSONObject.toJSONString(searchFace);
        String result = hikfaceService.FaceApi(faceinfo, 5);
        if (!result.equals("") && result != "") {
            JSONObject jsonObject = JSON.parseObject(result); //将json字符串转换成jsonObject对象
            Long code = Long.parseLong(jsonObject.getString("errorCode"));
            if (code == 1) {
                JSONArray array = jsonObject.getJSONArray("MatchList");
                if (array !=null) {
                    List<FaceInfo> faceInfoList = array.toJavaList(FaceInfo.class);
                    if (faceInfoList.size() > 0) {
                        double similarity = 0;
                        for (FaceInfo f : faceInfoList) {
                            if (similarity == 0 || f.getSimilarity() > similarity) {
                                similarity = f.getSimilarity();
                                faceInfo = f;
                            }
                        }
                    }
                }
            } else {
                System.out.println("以图搜图-返回匹配的人员信息::没有匹配到人脸信息");
            }
        } else {
            System.out.println("人脸解析-返回modeDate:：人脸服务器登陆失败或地址参数错误");
        }
        return faceInfo;
    }

//    //查询轨迹
//    @ResponseBody
//    @RequestMapping(value = "/api/Hik/updateHikDevice")
//    public HashMap<String, Object> updateHikDevice(@RequestBody Visitor visitor) {
//        logger.info("[track.api]-[get]-[/api/track/queryAllTrack] method have been called.");
//        HashMap<String, Object> map = new HashMap<>();
//        try {
//            List<Trajectory> trajectorys =  new ArrayList<>();
//            Boolean sss = hikvisonService.updateHikDevice(visitor.getVisitName(),visitor.getVisitCardNumber());
//            map.put("results", sss);
//            map.put("code", Global.CODE_SUCCESS);
//        } catch (Exception e) {
//            map.put("code", Global.CODE_DB_QUERY_EXCEPTION);
//            logger.info("[track.api]-[get]-[/api/track/queryAll] CODE_DB_QUERY_EXCEPTION exception,sel_all:" + e.getMessage());
//        }
//        return map;
//    }
}
