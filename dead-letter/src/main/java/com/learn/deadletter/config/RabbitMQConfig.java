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
    public final static String MY_QUEUE_NAME ="myQueue";
    public final static String MY_QUEUE2_NAME ="myQueue2";
    public final static String DEAD_QUEUE_NAME ="deadQueue";
    @Bean
    public Queue myQueue() {
        Map<String,Object> args=new HashMap<>();
        // 设置该Queue的死信的信箱
        args.put("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE);
        // 设置死信routingKey
        args.put("x-dead-letter-routing-key", MY_QUEUE_NAME);
        return new Queue(MY_QUEUE_NAME,true,false,false,args);
    }

    @Bean
    public Queue myQueue2() {

        Map<String,Object> args=new HashMap<>();
        args.put("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE);
        args.put("x-dead-letter-routing-key", MY_QUEUE2_NAME);
        return new Queue(MY_QUEUE2_NAME,true,false,false,args);
    }

    @Bean
    public FanoutExchange myFanoutExchange(){
        return new FanoutExchange("myFanoutExchange");
    }

    @Bean
    public Binding myQueueBinding() {
        return BindingBuilder.bind(myQueue()).to(myFanoutExchange());
    }

    @Bean
    public Binding myQueue2Binding() {
        return BindingBuilder.bind(myQueue2()).to(myFanoutExchange());
    }

    /**
     * 死信队列
     * @return
     */
    @Bean
    public Queue deadLetterQueue(){
        Map<String,Object> args=new HashMap<>();
        // 触发死信时投递的交换机（默认交换机名称为空串）
        args.put("x-dead-letter-exchange", "");
        args.put("x-message-ttl", 60*1000);//设置队列过期时间
        return new Queue(DEAD_QUEUE_NAME,true,false,false,args);
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
