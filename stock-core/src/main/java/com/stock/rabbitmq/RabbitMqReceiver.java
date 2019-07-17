package com.stock.rabbitmq;

import java.nio.charset.Charset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;

@Component
public class RabbitMqReceiver {

    private Logger logger = LoggerFactory.getLogger(RabbitMqReceiver.class);
	
	@RabbitHandler
	@RabbitListener(queues = "${rabbit.queue.init}") 
	public void process(Message message, Channel channel) {
		String json = new String(message.getBody(), Charset.forName("UTF-8"));
        logger.info("result:"+json);
	}
	 
    /**
     * 字符串
     * @param message
     */
	/*
	 * @RabbitHandler
	 * 
	 * @RabbitListener(queues = "${rabbit.test}") public void process(String
	 * message) { try { System.out.println("message:"+message); } catch (Exception
	 * e) { } }
	 */
}