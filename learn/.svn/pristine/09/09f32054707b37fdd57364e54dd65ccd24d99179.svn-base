/**
 * 
 */
package com.yeelim.learn.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Yeelim
 * @date 2014-4-13
 * @time 上午12:24:37
 * 
 */
public class Server {

	private final static String END_FLAG = "eof";

	public static void main(String args[]) throws IOException {
		// 为了简单起见，所有的异常信息都往外抛
		int port = 8899;
		// 定义一个ServerSocket监听在端口8899上
		ServerSocket server = new ServerSocket(port);
		// server尝试接收其他Socket的连接请求，server的accept方法是阻塞式的
		Socket socket = server.accept();
		// 跟客户端建立好连接之后，我们就可以获取socket的InputStream，并从中读取客户端发过来的信息了。
		Reader reader = new InputStreamReader(socket.getInputStream());
		BufferedReader br = new BufferedReader(reader);
		StringBuilder sb = new StringBuilder();
		String line;
		int index;
		while ((line = br.readLine()) != null) {
			if ((index = line.indexOf(END_FLAG)) != -1) {// 遇到eof时就结束接收
				sb.append(line.substring(0, index));
				break;
			}
			sb.append(line);
		}
		System.out.println("from client: " + sb);
		// 读完后写一句
		Writer writer = new OutputStreamWriter(socket.getOutputStream());
		writer.write("Hello \0Client.");
		writer.write(END_FLAG);
		writer.write("\n");
		writer.flush();
		writer.close();
		reader.close();
		br.close();
		socket.close();
		server.close();
	}

	public static void main2(String args[]) throws IOException {
		// 为了简单起见，所有的异常信息都往外抛
		int port = 8899;
		// 定义一个ServerSocket监听在端口8899上
		ServerSocket server = new ServerSocket(port);
		while (true) {
			// server尝试接收其他Socket的连接请求，server的accept方法是阻塞式的
			Socket socket = server.accept();
			// 跟客户端建立好连接之后，我们就可以获取socket的InputStream，并从中读取客户端发过来的信息了。
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			StringBuilder sb = new StringBuilder();
			String line;
			int index;
			while ((line = br.readLine()) != null) {
				if ((index = line.indexOf(END_FLAG)) != -1) {// 遇到eof时就结束接收
					sb.append(line.substring(0, index));
					break;
				}
				sb.append(line);
			}
			System.out.println("from client: " + sb);
			// 读完后写一句
			Writer writer = new OutputStreamWriter(socket.getOutputStream());
			writer.write("Hello Cfsfsli\0ent.");
			writer.write('\0');
			writer.write("======");
			writer.write(END_FLAG);
			writer.write("\n");
			writer.flush();
			writer.close();
			br.close();
			socket.close();
		}
	}
}
