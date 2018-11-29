package com.funcas.pboot.conf;

import com.funcas.pboot.module.upms.listener.QrLoginListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

/**
 * @author funcas
 * @version 1.0
 * @date 2018年11月27日
 */
@Configuration
public class RedisSubListenerConfig {

    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
                                            MessageListenerAdapter listenerAdapter) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(listenerAdapter, new PatternTopic("qrLogin"));
        return container;
    }


    @Bean
    QrLoginListener qrLoginListener() {
        return new QrLoginListener();
    }

    @Bean
    MessageListenerAdapter listenerAdapter(QrLoginListener qrLoginListener){
        return new MessageListenerAdapter(qrLoginListener);
    }

//    @Bean
//    MessageListenerAdapter listenerAdapter(RedisReceiver redisReceiver) {
//        return new MessageListenerAdapter(redisReceiver, "receiveMessage");
//    }

    @Bean
    StringRedisTemplate template(RedisConnectionFactory connectionFactory) {
        return new StringRedisTemplate(connectionFactory);
    }
}
