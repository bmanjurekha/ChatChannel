package com.example.chatchannel.WS;

import com.example.chatchannel.Model.Channel;
import com.example.chatchannel.Model.ChannelStateDetails;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
@Component
public class ChatSocketHandler extends TextWebSocketHandler {

    private List<WebSocketSession> sessions = new ArrayList<>();

    public void broadcast(String channel, Channel newChannel) {
        broadcastJson(channel, newChannel);
    }
    public void broadcastJson(String channel, Object object) {
        Gson gson = new Gson();
        broadcast(channel, gson.toJson(object));
    }

    public void broadcast(String channel, String message) {
        try {
            for (WebSocketSession webSession : sessions) { // broadcast
                String channelCreate = webSession.getHandshakeHeaders().getFirst("chat");
                if(channel.equals(channelCreate)) {
                    webSession.sendMessage(new TextMessage(message));
                }
            }
        } catch(IOException ex) {
            ex.printStackTrace();
        }
    }
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
        System.out.println("New session created ");
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
        System.out.println("Session was removed");
    }
}
