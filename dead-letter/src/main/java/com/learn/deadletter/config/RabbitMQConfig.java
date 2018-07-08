package com.learn.deadletter.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;
import java.util.Map;

public class RabbitMQConfig {
    // 死信的交换机名
    final String DEAD_LETTER_EXCHANGE="dead_exchange";
    final String q_mytest_name="mytest";
    @Bean
    public Queue mytestQueue() {
        Map<String,Object> args=new HashMap<>();
        // 设置该Queue的死信的信箱
        args.put("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE);
        // 设置死信routingKey
        args.put("x-dead-letter-routing-key", q_mytest_name);
        return new Queue(q_mytest_name,true,false,false,args);
    }

    @Bean
    public Binding maintainBinding() {
        return BindingBuilder.bind(mytestQueue()).to(DirectExchange.DEFAULT)
                .with(SysContants.ROUTING_KEY);
    }

    @Bean
    public Queue deadLetterQueue(){
        return new Queue(SysContants.DEAD_ROUTING_KEY);
    }

    @Bean
    public DirectExchange deadLetterExchange() {
        return new DirectExchange(DEAD_LETTER_EXCHANGE, true, false);
    }

    @Bean
    public Binding deadLetterBindding(){
        return BindingBuilder.bind(deadLetterQueue()).to(deadLetterExchange()).with(SysContants.DEAD_ROUTING_KEY);
    }

}
