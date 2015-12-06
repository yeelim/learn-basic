/**
 * 
 */
package com.yeelim.learn.jdk;

import java.io.IOException;
import java.lang.ref.SoftReference;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

/**
 * @author elim
 * @date 2015-1-2
 * @time 下午5:44:45
 * 
 */
public class SoftReferenceTest {

	@Test
	public void test() throws IOException, InterruptedException {
		Thread.sleep(20*1000);
		SoftTest softTest = new SoftTest();
		try (DirectoryStream<Path> test = Files.newDirectoryStream(Paths
				.get("D:\\test"));) {
			for (Path entry : test) {
				softTest.swithTo(entry.toString());
				softTest.useFile();
				Thread.sleep(2 * 1000);
				/*FileChannel channel = FileChannel.open(entry, StandardOpenOption.APPEND, StandardOpenOption.WRITE);
				ByteBuffer buffer = ByteBuffer.allocate(4*1000000);
				for (int i=0; i<1000000; i++) {
					buffer.putInt(i);
				}
				buffer.flip();
				channel.write(buffer);
				channel.close();*/
			}
		}
		
		System.out.println("******************************************");
		
		Map<Path, SoftTest.FileData> openedFiles = softTest.getOpenedFiles();
		for (Map.Entry<Path, SoftTest.FileData> entry : openedFiles.entrySet()) {
				System.out.println(String.format("当前文件%1$s的大小为%2$d",
						entry.getKey(),
						entry.getValue().getData().length));
		}
		Thread.sleep(20*1000);
		
	}

}

class SoftTest {
	
	public static class FileData {
		private Path filePath;
		private SoftReference<byte[]> dataRef;
//		private byte[] data;

		public FileData(Path filePath) {
			this.filePath = filePath;
			this.dataRef = new SoftReference<byte[]>(new byte[0]);
		}

		public Path getFilePath() {
			return this.filePath;
		}

		public byte[] getData() throws IOException {
			byte[] data = this.dataRef.get();
			if (data == null || data.length == 0) {
				data = Files.readAllBytes(this.filePath);
				this.dataRef = new SoftReference<byte[]>(data);
				data = null;	//去掉对应的强引用，这样对应的软引用才会生效
			}
			return this.dataRef.get();
//			if (data == null) {
//				data = Files.readAllBytes(this.filePath);
//			}
//			return data;
		}

	}

	private FileData currentFileData;
	private Map<Path, FileData> openedFiles = new HashMap<Path, FileData>();
	
	public Map<Path, FileData> getOpenedFiles() {
		return this.openedFiles;
	}

	public void swithTo(String filePath) {
		Path path = Paths.get(filePath).toAbsolutePath();
		if (openedFiles.containsKey(path)) {
			currentFileData = openedFiles.get(path);
		} else {
			currentFileData = new FileData(path);
			openedFiles.put(path, currentFileData);
		}
	}

	public void useFile() throws IOException {
		if (currentFileData != null) {
			System.out.println(String.format("当前文件%1$s的大小为%2$d",
					currentFileData.getFilePath(),
					currentFileData.getData().length));
		}
	}
	
}
