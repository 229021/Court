package com.san.platform.hikvison.hikengineer;

import org.springframework.amqp.core.Queue;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootConfiguration
public class RqbbitMQConfig {

    @Bean
    public Queue helloQueue() {
        return new Queue("guest");
    }

    public static final String DIRECT_QUEUE = "direct_queue";
    public static final String TOPIC_QUEUE_1 = "topic_queue_1";
    public static final String TOPIC_QUEUE_2 = "topic_queue_2";
    public static final String TOPIC_EXCHANGE = "topic_exchange";
    public static final String ROUTINE_KEY_1 = "topic.key1";


    public static final String ROUTINE_KEY_2 = "topic.*";
    public static final String FANOUT_QUEUE_1 = "fanout_queue_1";
    public static final String FANOUT_QUEUE_2 = "fanout_queue_2";
    public static final String FANOUT_EXCHANGE = "fanout_exchange";
    public static final String HEADERS_QUEUE = "headers_queue";
    public static final String HEADERS_EXCHANGE = "headers_exchange";


    @Bean
    public Queue directQueue() {
        return new Queue(DIRECT_QUEUE, true);
    }

    @Bean
    public Queue TopicQueue() {
        return new Queue(TOPIC_QUEUE_1, true);
    }

    @Bean
    public Queue Topic2Queue() {
        return new Queue(TOPIC_QUEUE_2, true);
    }

 /*   // 新建三个队列
    @Bean
    public Queue AMessage() {
        return new Queue("fanout.A");
    }

    @Bean
    public Queue BMessage() {
        return new Queue("fanout.B");
    }

    @Bean
    public Queue CMessage() {
        return new Queue("fanout.C");
    }

    // 新建一个交换机
    @Bean
    FanoutExchange fanoutExchange() {
        return new FanoutExchange("fanoutExchange");
    }

    // 将队列绑定到交换机上

    @Bean
    Binding bindingExchangeA(Queue AMessage, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(AMessage).to(fanoutExchange);
    }

    @Bean
    Binding bindingExchangeB(Queue BMessage, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(BMessage).to(fanoutExchange);
    }

    @Bean
    Binding bindingExchangeC(Queue CMessage, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(CMessage).to(fanoutExchange);
    }*/
}
