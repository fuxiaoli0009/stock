package com.stock.rabbitmq;

import java.io.IOException;
import java.nio.charset.Charset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import com.common.command.Command;
import com.common.command.CommandManager;
import com.rabbitmq.client.Channel;
import com.stock.enums.FlagEnum;

@Component
public class RabbitMqReceiver {

    private Logger logger = LoggerFactory.getLogger(RabbitMqReceiver.class);
	
	@RabbitHandler
	@RabbitListener(queues = "${rabbit.queue.init}") 
	public void process(Message message, Channel channel) {
		try {
			String json = new String(message.getBody(), Charset.forName("UTF-8"));
			Command command = new Command(FlagEnum.INIT_CONCERNED.name(), json);
			String result = (String) CommandManager.command(command);
			logger.info("result:{}", result);
			channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
		} catch (IOException e) {
			e.printStackTrace();
		}
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