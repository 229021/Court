package com.san.platform.visitor;

import com.alibaba.dubbo.config.annotation.Reference;
import com.san.platform.BaseController;
import com.san.platform.config.*;
import com.san.platform.option.OptionService;
import com.san.platform.personnel.Personnel;
import com.san.platform.personnel.PersonnelService;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.zip.ZipOutputStream;

import static com.san.platform.personnel.controller.PersonnelController.readInputStream;

@RestController
public class visitorController extends BaseController {

    @Reference
    private VisitorService visitorService;

    @Reference
    private PersonnelService personnelService;

    @Reference
    private OptionService optionService;

    //存储图片人像路径
    @Value("${path.access}")
    private String access;
    @Value("${localhostip.path}")
    private String localPath;
    @Value("${http.port}")
    private String serverPort;
    //存储图片根路径
    @Value("${path.baseUrl}")
    private String baseUrl;
    //存储图片人像路径
    @Value("${path.person}")
    private String person;
    @Value("${path.visit}")
    private String visit;



    /**
     * 创建访客记录
     *
     * @param visitor
     * @return
     */
    @PostMapping("/api/visitor/createVisitor")
    public HashMap<String, Object> createVisit(@RequestBody Visitor visitor) {
        logger.info("[visitor.api]-[post]-[/api/visitor/createVisitor] method have been called.");
        HashMap<String, Object> map = new HashMap<>();
        try {

            visitorService.createVisitor(visitor);
            map.put("code", Global.CODE_SUCCESS);

        } catch (Exception e) {

            logger.warn("[visitor.api]-[post]-[/api/visitor/createVisitor]  " +
                    "CODE = " + Global.CODE_DATA_INSERT_EXCEPION + "," +
                    "exception message:" + e.getMessage());

            map.put("code", Global.CODE_DATA_INSERT_EXCEPION);

        }

        return map;
    }

    /**
     * 查询访客记录
     *
     * @param visitor
     * @return
     */
    @GetMapping("/api/NameItems/queryVisit")
    public HashMap<String, Object> queryVisit(Visitor visitor) {
        logger.info("[visitor.api]-[get]-[/api/visitor/queryVisit] method have been called.");
        HashMap<String, Object> map = new HashMap<>();
        try {

            if (visitor.getKeyword() != null) {
                if ("男".equals(visitor.getKeyword())) {
                    visitor.setVisitSex(0);
                    visitor.setKeyword(null);
                }
                if ("女".equals(visitor.getKeyword())) {
                    visitor.setVisitSex(1);
                    visitor.setKeyword(null);
                }
            }
            System.out.println(visitor);
            String isToday = visitor.getIsToday();
            if(isToday.equals("no")&&!"".equals(isToday)){
            visitor.setIsToday(null);}
            map = visitorService.queryVisitor(visitor);
            map.put("options",optionService.queryAllOption());
            map.put("code", Global.CODE_SUCCESS);

        } catch (Exception e) {
            logger.warn("[visitor.api]-[get]-[/api/visitor/queryVisit]  " +
                    "CODE = " + Global.CODE_DB_QUERY_EXCEPTION + "," +
                    "exception message:" + e.getMessage());

            map.put("code", Global.CODE_DB_QUERY_EXCEPTION);
        }

        return map;
    }

    /**
     * 根据访客id查询访客 白鹏
     * 参数 主键id
     */
    @RequestMapping("/api/visitor/queryVisitorByID")
    public HashMap<String, Object> queryVisitorByID(@RequestBody Visitor visitor) {
        logger.info("[visitor.api]-[get]-[/api/visitor/queryVisitorByID] method have been called.");
        HashMap<String, Object> map = new HashMap<>();
        try {
            Visitor visitor1 = visitorService.queryVisitorByID(visitor);
            map.put("code", Global.CODE_SUCCESS);
            map.put("results",visitor1);

        } catch (Exception e) {
            logger.warn("[visitor.api]-[get]-[/api/visitor/queryVisitorByID]  " +
                    "CODE = " + Global.CODE_DB_QUERY_EXCEPTION + "," +
                    "exception message:" + e.getMessage());
            map.put("code", Global.CODE_DB_QUERY_EXCEPTION);

        }

        return map;
    }
    /**
     * 删除访客信息
     *
     * @param
     * @return
     */

    @DeleteMapping("/api/NameItems/removeVisit")
    public HashMap<String, Object> removeVisit(@RequestParam(value = "visitorIds[]") Integer[] visitorIds) {
        logger.info("[visitor.api]-[delete]-[/api/visitor/removeVisit] method have been called.");

        HashMap<String, Object> map = new HashMap<>();
        try {

            int i = visitorService.removeVisitor(Arrays.asList(visitorIds));
            map.put("delNum", i);
            map.put("code", Global.CODE_SUCCESS);

        } catch (Exception e) {

            logger.warn("[visitor.api]-[delete]-[/api/visitor/removeVisit]  " +
                    "CODE = " + Global.CODE_DATA_DELETE_EXCEPION + "," +
                    "exception message:" + e.getMessage());

            map.put("code", Global.CODE_DATA_DELETE_EXCEPION);
        }

        return map;
    }



    /**
     * 保存到人像库
     *
     * @param personnel
     * @return
     */
    @PostMapping("/api/NameItems/createToPersonnel")
    public HashMap<String, Object> createToPersonnel(@RequestBody Personnel personnel) {
        logger.info("[visitor.api]-[post]-[/api/visitor/createToPersonnel] method have been called.");
        HashMap<String, Object> map = new HashMap<>();
        try {
            //查询visitor属性复=赋值给personnel
            List<Visitor> visitors = visitorService.queryVisitor(personnel.getPerIds());
            for (Visitor visitor : visitors) {
                String uuid = UUID.randomUUID().toString();
                Personnel save_personnel = new Personnel();
                save_personnel.setPerUUID(uuid);
                save_personnel.setPerName(visitor.getVisitName());
                save_personnel.setPerIDType(visitor.getVisitCardType());
                save_personnel.setPerNumber(visitor.getVisitCardNumber());
                save_personnel.setPerSex(visitor.getVisitSex());
                save_personnel.setPerNation(visitor.getVisitNation());
                save_personnel.setPerAddress(visitor.getVisitAddress());
                save_personnel.setPerPicture(visitor.getVisitPicture());
                save_personnel.setPerType(personnel.getPerType());
                save_personnel.setPerMemo("");
                save_personnel.setCreateDate(new Date());
                save_personnel.setUpdateDate(new Date());

                // 图像移动
                String srcPathStr = baseUrl + visit + visitor.getVisitPicture().substring(7);
                String desPathStr = baseUrl + person;
                FileUtil.copyFile(srcPathStr,desPathStr);

                Personnel query_personnel = new Personnel();
                query_personnel.setPerNumber(visitor.getVisitCardNumber());
                Personnel queryPersonnel = personnelService.queryPersonnelByNumber(query_personnel);
                if (queryPersonnel != null) {
                    save_personnel.setPerId(queryPersonnel.getPerId());
                    personnelService.updatePersonnel(save_personnel);
                } else {
                    personnelService.createPersonnel(save_personnel);
                }
                map.put("code", Global.CODE_SUCCESS);
            }
        } catch (Exception e) {

            logger.warn("[visitor.api]-[post]-[/api/visitor/createToPersonnel]  " +
                    "CODE = " + Global.CODE_DATA_INSERT_EXCEPION + "," +
                    "exception message:" + e.getMessage());

            map.put("code", Global.CODE_DATA_INSERT_EXCEPION);
        }

        return map;
    }


    /*****************************   轨迹   **************************/

    @GetMapping("/api/NameItems/queryVisitorTrajectory")
    public HashMap<String, Object> queryVisitorTrajectory(@RequestParam(name = "visitorId") Integer visitorId) {

        logger.info("[visitor.api]-[post]-[/api/visitor/queryVisitorTrajectory] method have been called.");

        HashMap<String, Object> map = new HashMap<>();

        try {

            map.put("code", Global.CODE_SUCCESS);

        } catch (Exception e) {

            logger.warn("[visitor.api]-[post]-[/api/visitor/queryVisitorTrajectory]  " +
                    "CODE = " + Global.CODE_VISITOR_QUERY_EXCEPTION + "," +
                    "exception message:" + e.getMessage());

            map.put("code", Global.CODE_VISITOR_QUERY_EXCEPTION);

        }

        return map;
    }


    /******************************** 数据导出 *****************************/

    @GetMapping(value = "/api/visitor/downLoadVisitorCSV")
    public void downLoadZipFile(HttpServletResponse response,
                                @RequestParam(value = "visitorIds[]", required = false) Integer[] visitorIds,
                                @RequestParam(value = "isToday") String isToday) {

        List<Visitor> visitors = null;
        String tempFilePah = "D:/tem" + new Date().getTime() + "/";
        logger.info("[visitor.api]-[post]-[/api/visitor/createVisitor] method have been called.");
        if (visitorIds != null && visitorIds.length > 0) {
            visitors = visitorService.queryVisitor(Arrays.asList(visitorIds));
        } else {
            Visitor visitor = new Visitor();
            visitor.setIsToday(isToday);
            visitors = visitorService.queryVisitors(visitor);
        }
        // csv 信息
        String fileName = "来访记录" + DateUtil.putDateToTimeStr10(new Date()) + ".csv";
        String sTitle = "访客ID,访客姓名,访客身份证件类型,访客证件号,访客性别,访客名族,访客住址,照片地址,来访时间,离开时间,来访事由";
        String mapKey = "visitId,visitName,visitCardType,visitCardNumber,visitSex,visitNation,visitAddress,visitPicture,visitTime,leaveTime,visitCause";

        // 存放要保存的信息
        List<Map<String, Object>> dataList = new ArrayList<>();
        Map<String, Object> map = null;
        for (Visitor order : visitors) {
            map = new HashMap<>();

        map.put("visitId", order.getVisitId());
        map.put("visitName", order.getVisitName());
        map.put("visitCardType", order.getVisitCardType());
        map.put("visitCardNumber", "\t" + order.getVisitCardNumber());
        map.put("visitNation", order.getVisitNation());
        map.put("visitAddress", order.getVisitAddress());
        map.put("visitPicture", order.getVisitPicture());

        if (order.getVisitTime() != null) {
            map.put("visitTime", DateFormatUtils.format(order.getVisitTime(), "yyyy/MM/dd HH:mm"));
        }
        if (order.getLeaveTime() != null) {
            map.put("leaveTime", DateFormatUtils.format(order.getLeaveTime(), "yyyy/MM/dd HH:mm"));
        }
        map.put("visitCause", order.getVisitCause());
        dataList.add(map);
    }

    //保存成为csv文件
        CSVUtil.saveCsv(dataList, sTitle, mapKey, fileName, tempFilePah);

    //将文件信息写入响应流
    String zipName = "visitor_" + DateUtil.putDateToTimeStr10(new Date()) + ".zip";
        response.setContentType("APPLICATION/OCTET-STREAM");
        response.setHeader("Content-Disposition", "attachment; filename=" + zipName);
    List<String> picturePathList = new ArrayList<>();
    File file0 = null;
        try {
//            把要下载的文件放一个文件夹
            ZipOutputStream out = new ZipOutputStream(response.getOutputStream());

            for (Visitor visitor1 : visitors) {
                String visitPicture = visitor1.getVisitPicture();
                picturePathList.add("http://"+localPath+":"+serverPort+visitPicture);
            }
            if (picturePathList!=null&&picturePathList.size()>0){
                importPicture(picturePathList,file0,tempFilePah);
            }
            response.flushBuffer();
//            打包文件夹
            ZipUtils.doCompress(tempFilePah, out);
            out.close();
            boolean b = deleteFolder(tempFilePah);
            System.out.println("134134123412");
            System.out.println(b);
        } catch (Exception e) {

            logger.warn("[visitor.api]-[post]-[/api/visitor/createVisitor]  " +
                    "CODE = " + Global.CODE_VISITOR_EXPORT_DATA_EXCEPTION + "," +
                    "exception message:" + e.getMessage());

        }
    }

    /**
     * 删除文件
     */
    public boolean deleteFolder(String url) {
        File file = new File(url);
        if (!file.exists()) {
            return false;
        }
        if (file.isFile()) {
            file.delete();
            return true;
        } else {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                String root = files[i].getAbsolutePath();//得到子文件或文件夹的绝对路径
                //System.out.println(root);
                deleteFolder(root);
            }
            file.delete();
            return true;
        }
    }

    /**
     * 封装下载流程
     */
    public  void importPicture(List<String> picturePathList,File file0,String tempFilePah) throws Exception{
        String[] strings = picturePathList.toArray(new String[0]);
        FileOutputStream outStream = null;
        for (int i = 0; i < strings.length; i++) {
            URL url = new URL(strings[i]);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5 * 1000);
            InputStream inStream = conn.getInputStream();
            byte[] data = readInputStream(inStream);
            file0 = new File(tempFilePah  + "\\pic");
            if (!file0.isDirectory() && !file0.exists()) { file0.mkdirs(); }
            String str = strings[i];
            str = str.replace("http://" + localPath + ":" + serverPort + access,"");
            outStream = new FileOutputStream(file0 + "\\" + str);
            outStream.write(data);
            outStream.close();
        }
        outStream.close();

    }
    /**
     * 访客修改图片
     *参数 图片
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/api/NameItems/updateVisitor", method = RequestMethod.POST)
    public Map<String, Object> updatePersonnel(HttpServletRequest request, @RequestParam(value = "file", required = false) MultipartFile file, Visitor visitor) {
        logger.info("[visit.api]-[post]-[/api/NameItems/updateVisitor] method have been called.");
        Map<String, Object> map = new HashMap<>();
        try {
            if (file != null) {
                String str = this.fileUpload(file, request);
                //定义
                visitor.setVisitPicture(str);
            }
            String basePic = visitor.getBasePicture();
            if(basePic!=null&&!"".equals(basePic)){
                String base64 = basePic.substring(22);
                String uuid = UUID.randomUUID()+"";
                String photo = baseUrl + person + uuid + ".jpg";//8989
                String protoReal = access + uuid + ".jpg";
                visitor.setVisitPicture(protoReal);
                boolean b = base64ChangeImage(base64, photo);
                System.out.println(b);
            }
            if(visitor.getVisitPicture()!=null&&!"".equals(visitor.getVisitPicture())){
                // 更新图片
                visitorService.updateVisitor(visitor);
                map.put("code", Global.CODE_SUCCESS);
            } else {
                map.put("code", Global.CODE_SUCCESS);
            }
        } catch (Exception e) {
            map.put("code", Global.CODE_DB_QUERY_EXCEPTION);
            logger.info("[personnel.api]-[get]-[/api/personnel/updatePersonnel] CODE_DB_QUERY_EXCEPTION exception,sel_all:" + e.getMessage());
        }
        return map;
    }

    /**
     * 图像上传
     * @param file
     * @param request
     * @return
     */
    public String fileUpload(@RequestParam(value = "file") MultipartFile file, HttpServletRequest request) {
        if (file.isEmpty()) {
            System.out.println("文件为空");
        }
        String fileName = file.getOriginalFilename();  // 文件名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));  // 后缀名
        File directory = new File("");// 参数为空

        /*String courseFile = null;
        try {
            courseFile = directory.getCanonicalPath();
            courseFile = courseFile.replaceAll("\\\\","//");
            courseFile = courseFile.replaceAll("main//","");
        } catch (IOException e) {
            e.printStackTrace();
        }
        String peojectName = request.getContextPath();//项目名
        String basePath = request.getScheme()+"://" +request.getServerName()+":"+request.getServerPort()+request.getContextPath()  +"/";
        String filePath = courseFile + peojectName + "//static//img//pic//"; // 上传后的路径
      *//*  String filePath = "C://Users//xiaoz//Desktop//platform//yt_platform//static//img//pic//";*//* // 上传后的路径\*/

        ///////update by zsa 2019-06-13
        //人像库存放路径
        String realPath =  baseUrl + visit;
        File dest = new File(realPath);
        //判断文件目录是否存在
        if (!dest.isDirectory() && !dest.exists()) {
            dest.mkdirs();
        }
        try {
            //定义新的文件名称
            fileName = UUID.randomUUID() + suffixName; // 新文件名
            File per = new File(realPath + fileName);
            //上传图像文件
            file.transferTo(per);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String filename = access + fileName;
        return filename;
    }
    /**
     * BASE转图片
     *
     * @param baseStr   base64字符串
     * @param imagePath 生成的图片
     * @return
     */
    public boolean base64ChangeImage(String baseStr, String imagePath) {
        if (baseStr == null) {
            return false;
        }
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            // 解密
            byte[] b = decoder.decodeBuffer(baseStr);
            // 处理数据
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }
            OutputStream out = new FileOutputStream(imagePath);
            out.write(b);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
