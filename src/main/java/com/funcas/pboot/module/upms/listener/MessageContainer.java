package com.funcas.pboot.module.upms.listener;

import com.google.common.collect.Maps;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.ConcurrentMap;

/**
 * @author funcas
 * @version 1.0
 * @date 2018年11月27日
 */
@Component
public class MessageContainer<T> {

    private ConcurrentMap<String, DeferredResult<T>> userMessages = Maps.newConcurrentMap();

    public ConcurrentMap<String, DeferredResult<T>> getUserMessages() {
        return userMessages;
    }
}
