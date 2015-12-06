/**
 * 
 */
package com.yeelim.learn.netty.decoder;

import org.apache.log4j.Logger;

import com.google.protobuf.InvalidProtocolBufferException;

/**
 * 
 * 对Protobuf的简单测试
 * 
 * @author elim
 * @date 2015-3-28
 * @time 下午10:58:08 
 *
 */
public class ProtoBufTest {

	private final static Logger logger = Logger.getLogger(ProtoBufTest.class);
	
	/**
	 * @param args
	 * @throws InvalidProtocolBufferException 
	 */
	public static void main(String[] args) throws InvalidProtocolBufferException {
		UserProto.User.Builder builder = UserProto.User.newBuilder();
		builder.setId(1);
		builder.setName("张三");
		UserProto.User user = builder.build();
		logger.info("构建出来的对象是：" + user);
		byte[] encoder1 = encode(user);
		logger.info("编码后：" + encoder1.length);
		UserProto.User user2 = decode(encoder1);
		logger.info("解码后：" + user2);
		logger.info("user.equals(user2) = " + user.equals(user2));
		logger.info(user.getName());
	}
	
	/**
	 * 把一个Protobuf对象编码为对应的二进制数组
	 * @param user
	 * @return
	 */
	private static byte[] encode(UserProto.User user) {
		return user.toByteArray();
	}
	
	/**
	 * 从二进制数组中解码出对应的Protobuf对象
	 * @param data
	 * @return
	 * @throws InvalidProtocolBufferException
	 */
	private static UserProto.User decode(byte[] data) throws InvalidProtocolBufferException {
		return UserProto.User.parseFrom(data);
	}

}
