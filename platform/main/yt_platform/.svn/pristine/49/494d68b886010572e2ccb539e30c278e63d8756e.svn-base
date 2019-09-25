package com.san.platform.hikvison.test;

import com.alibaba.dubbo.config.annotation.Reference;
import com.san.platform.visitor.Visitor;
import com.san.platform.visitor.VisitorService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class testController {

    @Reference
    private VisitorService visitorService;
    @RequestMapping("/test")
    public List<Visitor> test(){
        return visitorService.queryAllVisitor();
    }


}
