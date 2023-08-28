package com.example.crypto.jobs;

import com.example.crypto.repository.CoinRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

import java.lang.reflect.Type;

public class MyStompSessionHandler extends StompSessionHandlerAdapter {
    private Logger log = LoggerFactory.getLogger(MyStompSessionHandler.class);

    @Override
    public void afterConnected(StompSession session, StompHeaders headers){
        log.info("New session established "+session.getSessionId());
        session.subscribe("/prices",this);
        log.info("subscribed to /prices");
    }
    @Override
    public void handleException(StompSession session, StompCommand command,StompHeaders headers, byte[] payload, Throwable exception){
        log.error("Got an exception",exception);
    }
    public Type getPayLoadType(StompHeaders headers){
        return CoinRepository.class;
    }
    @Override
    public void handleFrame(StompHeaders headers,Object payload){
        CoinRepository coin= (CoinRepository) payload;
        log.info("recieved "+coin.getName()+":"+coin.getPrice());
    }


}
