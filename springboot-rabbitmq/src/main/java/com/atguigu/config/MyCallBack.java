package com.atguigu.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MyCallBack implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnsCallback{
    /**
        交换机确认回调方法，不论成功失败，都会回调
            1.发消息   交换机接收到了 回调
                correlationData     保存回调消息的ID及相关信息
                交换机收到消息     ack = true
                cause   null
            2.发消息   交换机接受失败了 回调
                correlationData     保存回调消息的ID及相关信息
                交换机收到消息     ack = false
                cause   失败的原因
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        String id=correlationData!=null?correlationData.getId():"";
        if(ack){
            log.info("交换机已经收到 id 为:{}的消息",id);
        }else{
            log.info("交换机还未收到 id 为:{}消息,由于原因:{}",id,cause);
        }
    }
    /**
     *     可以在当消息传递过程中不可送达目的地时将消息返回给生产者
     *     只有不可达目的地的时候  才进行回退
     */
//    @Override
//    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
//        log.error(" 消 息 {}, 被交换机 {} 退回，退回原因 :{}, 路 由 key:{}",
//                new String(message.getBody()),exchange,replyText,routingKey);
//    }

    @Override
    public void returnedMessage(ReturnedMessage returnedMessage) {
        log.error(" 消 息 {}, 被交换机 {} 退回，退回原因 :{}, 路 由 key:{}",
                new String(returnedMessage.getMessage().getBody()),returnedMessage.getExchange(),
                returnedMessage.getReplyText(), returnedMessage.getRoutingKey());
    }


}
