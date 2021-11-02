package com.atguigu.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MyCallBack implements RabbitTemplate.ConfirmCallback {
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
    } }
