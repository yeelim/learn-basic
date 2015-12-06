/**
 * 
 */
package com.yeelim.learn.websocket;

import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.CloseReason;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.apache.log4j.Logger;

/**
 * 使用注解定义的WebSocket服务端.
 * 还有一种方式也可以定义WebSocket的服务端，那就是通过继承Tomcat7提供的WebSocketServlet来定义一个支持WebSocket的Servlet与客户端进行交互，不过
 * Tomcat官方现在已经在Tomcat7中对该类进行了弃用，并在Tomcat8中删除了该类，其推荐我们使用JSR356中的标准，即本类使用的方式。
 * 
 * @author elim
 * @date 2015-4-5
 * @time 上午12:11:27 
 *
 */
@ServerEndpoint("/websocket/{user}")
public class WebSocket1 {

	private final static Map<Session, Session> sessionMap = new ConcurrentHashMap<>();
	private final static Logger logger = Logger.getLogger(WebSocket1.class);
	
	public WebSocket1() {
		logger.info("============================Init");
	}
	
	/**
	 * 当连接的Socket被打开时将回调使用@OnOpen进行注解的方法，对应的可选方法参数可以查看@OnOpen的API。
	 * @param session
	 * @param config
	 */
	@OnOpen
	public void onOpen(Session session) {
		sessionMap.put(session, session);
		logger.info("接收到了一个请求：" + session);
	}
	
	/**
	 * 接收到信息的时候将回调该方法
	 * @param message
	 * @param session
	 */
	@OnMessage
	public void onMessage(String message, Session session) {
		logger.info("接收到信息：" + message + "，来自：" + session);
		String text = "服务器的当前时间是：" + new Date();
		//异步式的发送一个消息到客户端，其会立即返回一个Future对象
		session.getAsyncRemote().sendText(text);
		try {
			//阻塞式的发送一个信息到客户端，其会阻塞直到对应的信息完全发送完成
			session.getBasicRemote().sendText(text);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 关闭对应的连接时将回调该方法
	 * @param session
	 * @param reason
	 */
	@OnClose
	public void onClose(Session session, CloseReason reason) {
		logger.info("客户端请求关闭WebSocket连接：" + session);
		sessionMap.remove(session);
	}
	
	/**
	 * 异常时将回调该方法
	 * @param session
	 * @param cause
	 */
	@OnError
	public void onError(Session session, Throwable cause) {
		logger.info("WebSocket出现异常" + session, cause);
	}

}
