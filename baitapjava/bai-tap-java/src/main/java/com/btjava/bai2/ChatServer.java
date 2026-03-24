package com.btjava.bai2;

import org.java_websocket.server.WebSocketServer;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import java.net.InetSocketAddress;

public class ChatServer extends WebSocketServer {

    public ChatServer(InetSocketAddress address) {
        super(address);
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        System.out.println("Client mới kết nối: " + conn.getRemoteSocketAddress());
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        System.out.println("Client đã ngắt kết nối: " + conn.getRemoteSocketAddress());
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        System.out.println("Nhận được tin nhắn: " + message);
        broadcast(message);
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        ex.printStackTrace();
    }

    @Override
    public void onStart() {
        System.out.println("Server khởi động thành công trên cổng: " + getPort());
    }

    public static void main(String[] args) {
        String host = "localhost";
        int port = 8887;
        WebSocketServer server = new ChatServer(new InetSocketAddress(host, port));
        server.start();
    }
}