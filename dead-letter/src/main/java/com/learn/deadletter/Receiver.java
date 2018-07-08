package com.learn.deadletter;

import com.learn.deadletter.config.RabbitMQConfig;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RabbitListener(queues = RabbitMQConfig.ROUTING_KEY)
public class Receiver {

    @RabbitHandler
    public void process(Object message) {
        try {
            System.out.println(new Date()+"------------接收到消息:"+message);
            String s = new String(((Message) message).getBody());
            System.out.println("接收到消息:"+s);
            int a=0;
            int b=1/a;
            System.out.println("消息已处理");
        }catch (Exception e){
            System.out.println("处理失败");
            throw new RuntimeException("处理失败");
        }
    }
}
