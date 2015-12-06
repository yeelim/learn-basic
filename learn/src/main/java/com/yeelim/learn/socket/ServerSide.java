/**
 * 
 */
package com.yeelim.learn.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Yeelim
 * @date 2014-4-12
 * @time 下午11:50:00
 * 
 */
public class ServerSide {

	public static void main(String args[]) throws IOException {
		// 为了简单起见，所有的异常信息都往外抛
		int port = 8899;
		// 定义一个ServerSocket监听在端口8899上
		ServerSocket server = new ServerSocket(port);
		// server尝试接收其他Socket的连接请求，server的accept方法是阻塞式的
		Socket socket = server.accept();
		// 跟客户端建立好连接之后，我们就可以获取socket的InputStream，并从中读取客户端发过来的信息了。
		BufferedReader br = new BufferedReader(new InputStreamReader(
				socket.getInputStream(), "UTF-8"));
		StringBuilder sb = new StringBuilder();
		String temp;
		int index;
		while ((temp = br.readLine()) != null) {
			if ((index = temp.indexOf("eof")) != -1) {// 遇到eof时就结束接收
				sb.append(temp.substring(0, index));
				break;
			}
			sb.append(temp);
		}
		System.out.println("from client: " + sb);
		// 读完后写一句
		Writer writer = new OutputStreamWriter(socket.getOutputStream());
		writer.write("hello,client");
		writer.write("eof\n");
		writer.flush();
		writer.close();
		br.close();
		socket.close();
		server.close();
	}

}
