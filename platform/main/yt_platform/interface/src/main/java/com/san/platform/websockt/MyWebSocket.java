package com.san.platform.websockt;



import javax.websocket.OnMessage;
import javax.websocket.Session;
import java.io.IOException;

public interface MyWebSocket {
    void sendMessageBasedOnUsers(String message, Session session) throws IOException;

    void sendInfo(String message) throws IOException;



}
