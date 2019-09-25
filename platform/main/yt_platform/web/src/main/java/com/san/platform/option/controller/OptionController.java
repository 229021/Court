package com.san.platform.option.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.san.platform.BaseController;
import com.san.platform.config.Global;
import com.san.platform.option.Option;
import com.san.platform.option.OptionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @Author: Zhouzhongqing
 * @Date: 2019/4/18 11:31
 * @Description: 选项Controller
 */
//@Controller
@RestController
public class OptionController extends BaseController {

    @Reference
    private OptionService optionService;

    /* **
     * @Description: 查询全部选项事由
     * @Author: Zhouzhongqing
     * @Date: 2019/4/18 11:40
     * @Param: []
     * @Return: java.util.List<com.san.platform.option.Option>
     * @Exception:
     */
    //@ResponseBody
    @RequestMapping(value = "/api/option/queryAllOption")
    public HashMap<String, Object> queryAll() {
        logger.info("[option.api]-[get]-[/api/option/queryAllOption] method have been called.");
        List<Option> option = null;
        HashMap<String, Object> map = new HashMap<>();
        try {
            option = optionService.queryAllOption();
            map.put("results", option);
            map.put("code", Global.CODE_SUCCESS);
        } catch (Exception e) {
            map.put("code", Global.CODE_DB_QUERY_EXCEPTION);
            logger.info("[option.api]-[get]-[/api/option/queryAllOption] CODE_DB_QUERY_EXCEPTION exception,sel_all:" + e.getMessage());
        }
        return map;
    }

    /* **
     * @Description:  查询所有分页显示
     * @Author: Zhouzhongqing
     * @Date: 2019/4/20 18:14
     * @Param: [option]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Exception:
     */
    @ResponseBody
    @GetMapping(value = "/api/option/queryOptionPage")
    public Map<String, Object> selectOptionPage(Option option) {
        logger.info("[option.api]-[get]-[/api/option/queryOptionPage] method have been called.");
        HashMap<String, Object> map = new HashMap<>();
        try {
            map = optionService.selectOptionPage(option);
            System.out.println(map);
            map.put("code", Global.CODE_SUCCESS);
        } catch (Exception e) {
            map.put("code", Global.CODE_DB_QUERY_EXCEPTION);
            logger.info("[option.api]-[get]-[/api/option/queryOptionPage] CODE_DB_QUERY_EXCEPTION exception,sel_all:" + e.getMessage());
        }
        return map;
    }

    /* **
     * @Description: 增加选项
     * @Author: Zhouzhongqing
     * @Date: 2019/4/18 13:53
     * @Param: [option]
     * @Return: int
     * @Exception:
     */
    @ResponseBody
    @RequestMapping(value = "/api/option/createOption")
    public Map<String, Object> createOption(@RequestBody Option option) {
        logger.info("[option.api]-[get]-[/api/option/createOption] method have been called.");
        HashMap<String, Object> map = new HashMap<>();
        try {
            Option option1 = new Option();
            option1.setOptionName(option.getOptionName());
            Option option2 = optionService.selectByOptionId(option1);
            if (option2 != null) {
                map.put("code", Global.CODE_OPTION_NAME_IS_EXIST);
            } else {
                option.setOptionCreateTime(new Date());
                //option.setOptionModTime(new Date());
                int i = optionService.saveOption(option);
                if (i != 0) {
                    map.put("code", Global.CODE_SUCCESS);
                } else {
                    map.put("code", Global.CODE_DATA_INSERT_EXCEPION);
                }
            }
        } catch (Exception e) {
            map.put("code", Global.CODE_DB_QUERY_EXCEPTION);
            logger.info("[option.api]-[get]-[/api/option/createOption] method have been called.");
        }
        return map;
    }

    /* **
     * @Description: 根据id删除选项 (单删)
     * @Author: Zhouzhongqing
     * @Date: 2019/4/18 14:30
     * @Param: [optionId]
     * @Return: int
     * @Exception:
     */
    @ResponseBody
    @RequestMapping(value = "/api/option/deleteByOptionId")
    public Map<String, Object> deleteByOptionId(@RequestBody Option optionId) {
        logger.info("[option.api]-[get]-[/api/option/deleteByOptionId] method have been called.");
        HashMap<String, Object> map = new HashMap<>();
        try {
            optionService.deleteByOptionId(optionId);
            map.put("code", Global.CODE_SUCCESS);
        } catch (Exception e) {
            map.put("code", Global.CODE_DB_QUERY_EXCEPTION);
            logger.info("[option.api]-[get]-[/api/option/deleteByOptionId] CODE_DB_QUERY_EXCEPTION exception,sel_all:" + e.getMessage());
        }
        return map;
    }

    /* **
     * @Description: 获取ID删除多个选项事由
     * @Author: Zhouzhongqing
     * @Date: 2019/4/19 12:40
     * @Param: [optionIdd]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Exception:
     */
    @ResponseBody
    @RequestMapping(value = "/api/option/deleteOption")
    public Map<String, Object> deleteOption(@RequestBody List<Integer> optionIdd) {
        logger.info("[option.api]-[get]-[/api/option/deleteOption] method have been called.");
        HashMap<String, Object> map = new HashMap<>();
        try {
            for (Integer id : optionIdd) {
                optionService.deleteOption(id);
            }
            map.put("code", Global.CODE_SUCCESS);
        } catch (Exception e) {
            map.put("code", Global.CODE_DB_QUERY_EXCEPTION);
            logger.info("[option.api]-[get]-[/api/option/deleteOption] CODE_DB_QUERY_EXCEPTION exception,sel_all:" + e.getMessage());
        }
        return map;
    }


    /* **
     * @Description: 修改选项信息
     * @Author: Zhouzhongqing
     * @Date: 2019/4/18 17:56
     * @Param: [option]
     * @Return: java.util.HashMap<java.lang.String,java.lang.Object>
     * @Exception:
     */
    @ResponseBody
    @RequestMapping(value = "/api/option/updateOption")
    public HashMap<String, Object> updateOption(@RequestBody Option option) {
        logger.info("[option.api]-[get]-[/api/option/updateOption] method have been called.");
        HashMap<String, Object> map = new HashMap<>();
        try {
//            option.setOptionCreateTime(new Date());
            option.setOptionModTime(new Date());
            optionService.updateOption(option);
            map.put("code", Global.CODE_SUCCESS);
        } catch (Exception e) {
            map.put("code", Global.CODE_DATA_UPDATE_EXCEPION);
            logger.info("[option.api]-[get]-[/api/option/updateOption] CODE_DB_QUERY_EXCEPTION exception,sel_all:" + e.getMessage());
        }
        return map;
    }

    /* **
     * @Description: 根据ID查询选项信息
     * @Author: Zhouzhongqing
     * @Date: 2019/4/18 18:08
     * @Param: [optionId]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Exception:
     */

    @ResponseBody
    @RequestMapping(value = "/api/option/selectByOptionId")
    public Map<String, Object> selectByOptionId(@RequestBody Option option) {
        System.out.println(option.getOptionId());
        logger.info("[option.api]-[get]-[/api/option/selectByOptionId] method have been called.");
        HashMap<String, Object> map = new HashMap<>();
        Option option1 = null;
        try {
            option1 = optionService.selectByOptionId(option);
            List<Option> list = new ArrayList<>();
            list.add(option1);
            map.put("code", Global.CODE_SUCCESS);
            map.put("results", list);
        } catch (Exception e) {
            map.put("code", Global.CODE_DB_QUERY_EXCEPTION);
            logger.info("[option.api]-[get]-[/api/option/selectByOptionId] CODE_DB_QUERY_EXCEPTION exception,sel_all:" + e.getMessage());
        }
        return map;
    }


}
