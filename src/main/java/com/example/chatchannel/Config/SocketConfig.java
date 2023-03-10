package com.example.chatchannel.Config;

import com.example.chatchannel.WS.ChannelSocketHandler;
import com.example.chatchannel.WS.ChatSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class SocketConfig implements WebSocketConfigurer {

    @Autowired
    private ChannelSocketHandler channelSocketHandler;
    @Autowired
    private ChatSocketHandler chatSocketHandler;

    private final static String SOCKET_PREFIX = "/sub";

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(channelSocketHandler, SOCKET_PREFIX + "/channel");
        registry.addHandler(chatSocketHandler, SOCKET_PREFIX + "/chat");
    }
}