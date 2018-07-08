package com.learn.deadletter.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitMQConfig {
    // 死信的交换机名
    public final static String DEAD_LETTER_EXCHANGE="dead_exchange";
    public final static String ROUTING_KEY="myQueue";
    public final static String ROUTING_KEY2="myQueue2";
    public final static String DEAD_ROUTING_KEY="deadQueue";
    @Bean
    public Queue maintainQueue() {
        Map<String,Object> args=new HashMap<>();
        // 设置该Queue的死信的信箱
        args.put("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE);
        // 设置死信routingKey
        args.put("x-dead-letter-routing-key", ROUTING_KEY);
        //return new Queue(ROUTING_KEY,true,false,false,args);
        return new Queue(ROUTING_KEY,true,false,false,args);
    }

    @Bean
    public FanoutExchange myFanoutExchange(){
        return new FanoutExchange("myFanoutExchange");
    }

    @Bean
    public Binding maintainBinding() {
        return BindingBuilder.bind(maintainQueue()).to(myFanoutExchange());
    }

    @Bean
    public Queue deadLetterQueue(){
        Map<String,Object> args=new HashMap<>();
        // 设置该Queue的死信的信箱
        args.put("x-dead-letter-exchange", "");
        args.put("x-message-ttl", 10*1000);
        return new Queue(DEAD_ROUTING_KEY,true,false,false,args);
    }

    @Bean
    public FanoutExchange deadLetterExchange() {
        return new FanoutExchange(DEAD_LETTER_EXCHANGE, true, false);
    }

    @Bean
    public Binding deadLetterBinding(){
        return BindingBuilder.bind(deadLetterQueue()).to(deadLetterExchange());
    }


}
