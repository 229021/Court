package com.san.platform;

import org.springframework.amqp.core.Queue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@SpringBootApplication
public class WebApplication extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure (SpringApplicationBuilder applicationBuilder) {
        return applicationBuilder.sources(WebApplication.class);
    }
    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }


    public static final String TOPIC_VISIT = "topic_visit";
    public static final String TOPIC_FACEADD = "topic_face_add";
    public static final String TOPIC_FACEUPDATE = "topic_face_update";
    public static final String TOPIC_FACESNAP = "topic_face_snap";
    public static final String TOPIC_REPAIRCARD = "topic_repair_card";
    public static final String TOPIC_LATER = "topic_later";
    public static final String TOPIC_REPAIRCARDS = "topic_repair_cards";


    public static final String ROUTINE_KEY_2 = "topic.*";
    public static final String FANOUT_QUEUE_1 = "fanout_queue_1";
    public static final String FANOUT_QUEUE_2 = "fanout_queue_2";
    public static final String FANOUT_EXCHANGE = "fanout_exchange";
    public static final String HEADERS_QUEUE = "headers_queue";
    public static final String HEADERS_EXCHANGE = "headers_exchange";


    @Bean
    public Queue directQueue() {
        return new Queue(TOPIC_VISIT, true);
    }

    @Bean
    public Queue TopicQueue() {
        return new Queue(TOPIC_FACEADD, true);
    }

    @Bean
    public Queue Topic6Queue() { return new Queue(TOPIC_LATER, true); }

    @Bean
    public Queue Topic2Queue() {
        return new Queue(TOPIC_FACEUPDATE, true);
    }

    @Bean
    public Queue TopicFaceSnap() {
        return new Queue(TOPIC_FACESNAP, true);
    }

    @Bean
    public Queue TopicRepairCard() {
        return new Queue(TOPIC_REPAIRCARD, true);
    }
    // 76 照片
    @Bean
    public Queue TopicRepairCards() {
        return new Queue(TOPIC_REPAIRCARDS, true);
    }

    @Bean
    public FilterRegistrationBean registrationBean() {
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.addUrlPatterns("/*");
        bean.setFilter(new UrlFilter());
        return bean;
    }
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}
