package com.yeelim.learn.websocket;

import java.io.IOException;
import java.net.URI;
import java.util.Date;

import javax.websocket.ClientEndpoint;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

import org.apache.log4j.Logger;

@ClientEndpoint
public class Client {
	
	private final static Logger logger = Logger.getLogger(Client.class);
	
	@OnOpen
	public void onOpen(Session session) {
		logger.info("客户端连接上了服务端！");
		try {
			session.getBasicRemote().sendText("Hello Server");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@OnClose
	public void onClose() {
		logger.info("连接关闭了！");
	}
	
	@OnMessage
	public void onMessage(String message, Session session) {
		logger.info("客户端接收到来自服务端的消息：" + message);
	}
	
	public static void main(String args[]) {
		WebSocketContainer container = ContainerProvider.getWebSocketContainer();
		String uri = "ws://localhost:8080/learn/websocket1";
		try (Session session = container.connectToServer(Client.class, URI.create(uri))) {
			session.getBasicRemote().sendText("Hello Server. current time is: " + new Date());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DeploymentException e) {
			e.printStackTrace();
		};
	}
	
}
