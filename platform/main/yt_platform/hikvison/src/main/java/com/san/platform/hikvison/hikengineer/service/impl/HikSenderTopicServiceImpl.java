package com.san.platform.hikvison.hikengineer.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.san.platform.hikengineer.HikSenderTopicService;
import com.san.platform.visitor.Visitor;
import com.san.platform.visitor.VisitorService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class HikSenderTopicServiceImpl implements HikSenderTopicService{

    @Autowired
    private AmqpTemplate rabbitTemplate;
    @Reference
    private VisitorService visitorService;


    public Visitor sendTopicHik(Visitor visitor) {
        // 生产的消息
        // List<Visitor> list =visitorService.queryAllVisitor();
        // 通过erbsocket接一下小白传过来的信
        rabbitTemplate.convertAndSend("direct_queue", visitor);
        return visitor;
    }
}
