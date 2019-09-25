package com.san.platform.region;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.san.platform.BaseController;
import com.san.platform.config.Global;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class RegionController extends BaseController {

    @Reference
    private RegionService regionService;

    @ResponseBody
    @RequestMapping(value = "/api/region/queryAllRegion")
    public HashMap<String, Object> queryAllRegion(){
        logger.info("[region.api]-[get]-[/api/region/queryAllRegion] method have been called.");
        HashMap<String, Object> map = new HashMap<>();
        try {
            List<Region> regionList = regionService.queryAllRegion();
            map = this.dealRegion(regionList);
            // map加入code信息 200
            map.put("code", Global.CODE_SUCCESS); // 数据正常，返回code 200
        } catch (Exception e) {
            map.put("code", Global.CODE_DB_QUERY_EXCEPTION);
            logger.info("[region.api]-[get]-[/api/region/queryAllRegion] CODE_DB_QUERY_EXCEPTION exception:" + e.getMessage());
        }
        return map;
    }

    /**
     * 处理树形结构
     * @param regionList
     * @return
     */
    public HashMap<String,Object> dealRegion (List<Region> regionList) {
        HashMap<String, Object> tree = new HashMap<>();
        HashMap<String, Object> root = new HashMap<>();
        List<Map> sons = new ArrayList();
        for (Region region : regionList) {
            if (region.getRegionParentId() == 0) {
                root.put("name",region.getRegionName());
                root.put("id",region.getRegionId());
                root.put("root",true);
                tree.put("root",root);
            }else {
                Region regionSon = new Region();
                regionSon.setRegionParentId(Integer.parseInt(root.get("id").toString()));
                List<Region> regionSonList = regionService.queryRegionParentId(regionSon);
                for (Region re: regionSonList) {
                    HashMap<String, Object> son = new HashMap<>();
                    son.put("name",re.getRegionName());
                    son.put("id",re.getRegionId());
                    if (!re.getRegionSon().equals(",")) {
                        List<Map> child = this.buildChild(re.getRegionId());
                        son.put("children",child);
                    }
                    sons.add(son);
                }
                tree.put("children",sons);
                break;
            }
        }
        return tree;
    }

    /**
     * 处理树形结构
     * @param regionId
     * @return
     */
    public List<Map> buildChild(Integer regionId){
        List<Map> sons = new ArrayList();
        Region regionSon = new Region();
        regionSon.setRegionParentId(regionId);
        List<Region> regionSonList = regionService.queryRegionParentId(regionSon);
        for (Region re: regionSonList) {
            HashMap<String, Object> son = new HashMap<>();
            son.put("name",re.getRegionName());
            son.put("id",re.getRegionId());
            if (!re.getRegionSon().equals(",")) {
                List<Map> child = this.buildChild(re.getRegionId());
                son.put("children",child);
            }
            sons.add(son);
        }
        return sons;
    }


    /**
     * 地图信息-查询相关的区域信息
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/api/region/queryRegionInfo")
    public HashMap<String, Object> queryRegionInfo(){
        logger.info("[region.api]-[get]-[/api/region/queryRegionInfo] method have been called.");
        HashMap<String, Object> map = new HashMap<>();
        List reList = new ArrayList();
        try {
            // select选择 默认值
            HashMap<String, Object> mapDefault = new HashMap<>();
            mapDefault.put("text","请选择---");
            mapDefault.put("value",0);
            // 存入list
            reList.add(mapDefault);
            // 查询所有的场所信息
            List<Region> regionList = regionService.queryAllRegion();
            // 如果有对应的场所信息，继续循环存入list
            if (regionList.size() > 0) {
                for (Region region : regionList) {
                    HashMap<String, Object> regMap = new HashMap<>();
                    regMap.put("text",region.getRegionName());
                    regMap.put("value",region.getRegionId().toString());
                    reList.add(regMap);
                }
                map.put("results",reList);
            }
            // map加入code信息 200
            map.put("code", Global.CODE_SUCCESS); // 数据正常，返回code 200
        } catch (Exception e) {
            map.put("code", Global.CODE_DB_QUERY_EXCEPTION);
            logger.info("[region.api]-[get]-[/api/region/queryRegionInfo] CODE_DB_QUERY_EXCEPTION exception:" + e.getMessage());
        }
        return map;
    }

    @ResponseBody
    @RequestMapping(value = "/api/region/createRegion")
    public HashMap<String, Object> createRegion(@RequestBody Region region) {
        logger.info("[region.api]-[post]-[/api/region/createRegion] method have been called.");
        HashMap<String, Object> map = new HashMap<>();
        try {
            int i = regionService.createRegion(region);
            if (i == 0) { // 创建设备失败，返回code 3831
                map.put("code", Global.CODE_REGION_CREATE_EXCEPTION);
            } else { // 创建设备成功，返回code 200
                map.put("code", Global.CODE_SUCCESS);
            }
        } catch (Exception e) {
            map.put("code", Global.CODE_DB_QUERY_EXCEPTION);
            logger.info("[region.api]-[post]-[/api/region/createRegion] CODE_DB_QUERY_EXCEPTION exception:" + e.getMessage());
        }
        return map;
    }

    @ResponseBody
    @RequestMapping(value = "/api/region/updateRegion")
    public HashMap<String, Object> updateRegion(@RequestBody Region region) {
        logger.info("[region.api]-[post]-[/api/region/updateRegion] method have been called.");
        HashMap<String, Object> map = new HashMap<>();
        try {
            int i = regionService.updateRegin(region);
            if (i == 0) { // 创建设备失败，返回code 3832
                map.put("code", Global.CODE_REGION_UPDATE_EXCEPITON);
            } else { // 创建设备成功，返回code 200
                map.put("code", Global.CODE_SUCCESS);
            }
        } catch (Exception e) {
            map.put("code", Global.CODE_DB_QUERY_EXCEPTION);
            logger.info("[region.api]-[post]-[/api/region/updateRegion] CODE_DB_QUERY_EXCEPTION exception:" + e.getMessage());
        }
        return map;
    }

    //删除
    @ResponseBody
    @RequestMapping(value = "/api/region/removeRegionById")
    public HashMap<String, Object> removeRegionById(Region regionId) {
        logger.info("[region.api]-[post]-[/api/region/removeRegionById] method have been called.");
        HashMap<String, Object> map = new HashMap<>();
        //循环查出来的本区域
        List<Region> regionList=new ArrayList();
        //循环查出模糊查询出的rson数据分割
        List<Region> resonList=new ArrayList();
        try{
            regionList=regionService.queryReginByParid(regionId.getRegionId());
            //模糊查询出的son值获取拆分,号
            String regionson;
            //同上，用于下面更新使用
            //用于比较
            String upreon;
            //拼接此区域的删除此区域rid
            Integer rsid=regionId.getRegionId();
            //删除成功则大于0不成功则=0
            int s=0;
            for(Region reg:regionList){
                regionson=reg.getRegionSon();
                //获取本区域id值和son值进行加上进行拆分删除
                rsid=reg.getRegionId();
                //转成String类型
                String rbid=String.valueOf(rsid);
                regionson=regionson+rsid;
                String[] strs=regionson.split(",");
                for(int i=0,len=strs.length;i<len;i++) {
                    String rid = strs[i].toString();
                    if (!rid.equals("")) {
                        s = regionService.removeRegin(rid);
                        resonList = regionService.querySonByLike("," + rid + ",");
                        for (Region regio : resonList) {
                            upreon = regio.getRegionSon();
                            Integer rsonid = regio.getRegionId();
                            String newid = ",";
                            String[] stras = upreon.split(",");
                            for (String so : stras) {
                                if (!so.equals("")) {
                                    if (!so.toString().equals(rid)) {
                                        newid += so.toString() + ",";
                                    }
                                }
                            }
                            regionService.updateReginonId(newid, rsonid);
                        }
                    }

                }
                if (s == 0) { // 创建设备失败，返回code 3832
                    map.put("code", Global.CODE_REGION_UPDATE_EXCEPITON);
                } else { // 创建设备成功，返回code 200
                    map.put("code", Global.CODE_SUCCESS);
                }

            }
        } catch (Exception e) {
            map.put("code", Global.CODE_DB_QUERY_EXCEPTION);
            logger.info("[region.api]-[post]-[/api/region/removeRegionById] CODE_DB_QUERY_EXCEPTION exception:" + e.getMessage());
}
        return map;
                }

    //根据节点上级查询
    @ResponseBody
    @RequestMapping(value = "/api/region/queryregionid")
    public HashMap<String, Object> queryregionid(Region regionId) {
        logger.info("[region.api]-[post]-[/api/region/queryregionid] method have been called.");
        HashMap<String, Object> map = new HashMap<>();
        List<Region> list=new ArrayList();
        try {
            Region regionList=regionService.queryReginById(regionId);
            //根据获取出来的id查询父id
            Integer pid=regionList.getRegionParentId();
            list=regionService.queryReginByParid(pid);
            for(Region re:list){
                map.put("regionName",re.getRegionName());
                map.put("rid",re.getRegionId());
            }
            map.put("code", Global.CODE_SUCCESS);
        }catch (Exception e){
            map.put("code", Global.CODE_SUCCESS);
        }
        return map;
    }

    //根据节点同级查询
    @ResponseBody
    @RequestMapping(value = "/api/region/queryregionidsub")
    public HashMap<String, Object> queryregionidsub(Region regionId) {
        logger.info("[region.api]-[post]-[/api/region/queryregionidsub] method have been called.");
        HashMap<String, Object> map = new HashMap<>();
        try {
            Region regionList=regionService.queryReginById(regionId);
            map.put("regionName",regionList.getRegionName());
            map.put("rid",regionList.getRegionId());
            map.put("code", Global.CODE_SUCCESS);
        }catch (Exception e){
            map.put("code", Global.CODE_SUCCESS);
        }
        return map;
    }
    //根据同级从查询
    @ResponseBody
    @RequestMapping(value = "/api/region/queryregionupdate")
    public HashMap<String, Object> queryregionupdate(Region regionId) {
        logger.info("[region.api]-[post]-[/api/region/queryregionupdate] method have been called.");
        HashMap<String, Object> map = new HashMap<>();
        try {
            Region regionList=regionService.queryReginById(regionId);
            List<Region> Rlist=regionService.queryReginByParid(regionList.getRegionParentId());
            for(Region reg:Rlist){
                String regionName=reg.getRegionName();
                map.put("regionName",regionName);
            }
            map.put("results",regionList);
            map.put("rid",regionList.getRegionId());
            map.put("code", Global.CODE_SUCCESS);
        }catch (Exception e){
            map.put("code", Global.CODE_SUCCESS);
        }
        return map;
    }

    //新增区域信息
    @ResponseBody
    @RequestMapping(value = "/api/region/SaveRegion")
    public HashMap<String, Object> SaveRegion(@RequestBody Region region) {
        logger.info("[region.api]-[post]-[/api/region/SaveRegion] method have been called.");
        HashMap<String, Object> map = new HashMap<>();
        List<Region> list=new ArrayList();
        List<Region> list2=new ArrayList();
        List<Region> list3=new ArrayList();
        if(region.getRegionSon()==null){
            region.setRegionSon(",");
        }
        try {
            String rname=region.getRegionName();
            list=regionService.queryReginByname(rname);
            //如果list里面没有查询出数据证明没有此条 则进行增加
           if(list.size()==0){
               int re=regionService.createRegion(region);
               list=regionService.queryReginByname(rname);//再次查询出有数据进行遍历
               if(re>0){
                   //根据namne拿出自己的主id
                   //主id
                   Integer rid=0;
                   //父id
                   Integer regionId=region.getRegionParentId();
                   for (Region res:list){
                       rid=res.getRegionId();
                       break;
                   }
                   //获取子节点的字段
                   String regionSon="";
                   while (regionId!=0){
                       //查出从name里的父id继续查父的值拼接
                       list2=regionService.queryReginByParid(regionId);
                       for(Region res2:list2){
                           regionSon=res2.getRegionSon();
                           //拼接rregionson
                           regionSon=regionSon+rid+",";
                           int update=regionService.updateReginonId(regionSon,regionId);
                           regionId=res2.getRegionParentId();
                           break;
                       }
                   }
                   System.out.println(regionSon);
                   map.put("code", Global.CODE_SUCCESS);
               }
           }else{
               map.put("code", Global.CODE_REGION_NAME_IS_EXIST);
           }
        }catch (Exception e){
            map.put("code", Global.CODE_SUCCESS);
        }
        return map;
    }

    //修改区域信息(编辑)
    @ResponseBody
    @RequestMapping(value = "/api/region/UpdateRegion")
    public HashMap<String, Object> UpdateRegion(@RequestBody Region region) {
        logger.info("[region.api]-[post]-[/api/region/UpdateRegion] method have been called.");
        HashMap<String, Object> map = new HashMap<>();
        try {
            int re=regionService.updateRegin(region);
            if(re>0){
                map.put("code", Global.CODE_SUCCESS);
            }
        }catch (Exception e){
            map.put("code", Global.CODE_SUCCESS);
        }
        return map;
    }

}
