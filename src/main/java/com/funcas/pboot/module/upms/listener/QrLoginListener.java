package com.funcas.pboot.module.upms.listener;

import com.funcas.pboot.common.util.FastJsonUtil;
import com.funcas.pboot.module.upms.entity.QrLoginMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.Map;

/**
 * @author funcas
 * @version 1.0
 * @date 2018年11月27日
 */
@Slf4j
public class QrLoginListener implements MessageListener {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    MessageContainer<QrLoginMessage> messageContainer;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        byte[] body = message.getBody();
        String itemValue = (String)redisTemplate.getValueSerializer().deserialize(body);

        QrLoginMessage msg = FastJsonUtil.getBean(itemValue, QrLoginMessage.class);
        Map<String, DeferredResult<QrLoginMessage>> msgContainer = messageContainer.getUserMessages();
        DeferredResult<QrLoginMessage> deferredResult = msgContainer.get(msg.getId());
        if(deferredResult != null) {
            deferredResult.setResult(msg);
        }

    }


}
