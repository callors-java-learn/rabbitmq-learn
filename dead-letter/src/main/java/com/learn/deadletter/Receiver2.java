package com.learn.deadletter;

import com.learn.deadletter.config.RabbitMQConfig;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RabbitListener(queues = RabbitMQConfig.MY_QUEUE2_NAME)
public class Receiver2 {

    @RabbitHandler
    public void process(Object message) {
        try {
            System.out.println("---------"+new Date()+"-----queue:"+RabbitMQConfig.MY_QUEUE2_NAME+"-------接收到消息:"+message);
            String s = new String(((Message) message).getBody())+"";
            System.out.println("---------"+new Date()+"-----queue:"+RabbitMQConfig.MY_QUEUE2_NAME+"-------接收到消息:"+s);
            // 字符中出现error2 表示消费消息失败
            if (s.indexOf("error2")>=0) {
                int a = 0;
                int b = 1 / a;
            }
            System.out.println("---------"+new Date()+"-----queue:"+RabbitMQConfig.MY_QUEUE2_NAME+"-------消息已处理");
        }catch (Exception e){
            System.out.println("---------"+new Date()+"-----queue:"+RabbitMQConfig.MY_QUEUE2_NAME+"-------处理失败");
            throw new RuntimeException("处理失败");
        }
    }
}
