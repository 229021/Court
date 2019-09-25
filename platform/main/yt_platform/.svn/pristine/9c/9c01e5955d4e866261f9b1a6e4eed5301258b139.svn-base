package com.san.platform.device;

import com.alibaba.dubbo.config.annotation.Reference;
import com.san.platform.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class AbilityController extends BaseController {

    @Reference
    private AbilityService abilityService;

    @ResponseBody
    @RequestMapping(value = "/api/ability/queryAllAbility")
    public HashMap<String, Object> queryAllAbility() {
        logger.info("[ability.api]-[get]-[/api/device/queryAllAbility] method have been called.");
        HashMap<String, Object> map = new HashMap<>();
        return abilityService.queryAllAbility();
    }
}
