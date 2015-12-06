/**
 * 
 */
package com.yeelim.learn.java.nio;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;

import org.junit.Test;

/**
 * @author elim
 * @date 2014-12-22
 * @time 下午8:51:08 
 *
 */
public class PathTest {

	@Test
	public void test() {
		Path path1 = Paths.get("D:\\A\\B", "C\\D\\E");//D:\\A\\B\\C\\D\\E
		System.out.println(path1.toString());
	}
	
	@Test
	public void test2() {
		Path path1 = Paths.get("D:\\A\\B\\C");
		Path path2 = Paths.get("D:\\D\\E\\F");
		System.out.println(path1.resolve(path2));
		System.out.println(path1.relativize(path2));
	}
	
	@Test
	public void test3() throws IOException {
		Path dir = Paths.get("D:\\develop");
		try (DirectoryStream<Path> ds = Files.newDirectoryStream(dir);) {
			for (Path entry : ds) {
				System.out.println(entry + "======" + entry.toFile().isDirectory());
			}
		}
		
	}
	
	@Test
	public void test4() throws IOException {
		Path dir = Paths.get("D:\\develop");
		Files.walkFileTree(dir, new FileVisitor<Path>() {

			public FileVisitResult preVisitDirectory(Path dir,
					BasicFileAttributes attrs) throws IOException {
				System.out.println("在访问目标目录之前：" + dir);
				return FileVisitResult.CONTINUE;
			}

			public FileVisitResult visitFile(Path file,
					BasicFileAttributes attrs) throws IOException {
				System.out.println(file);
				return FileVisitResult.CONTINUE;
			}

			public FileVisitResult visitFileFailed(Path file, IOException exc)
					throws IOException {
				System.out.println("访问目标文件失败：" + file);
				return FileVisitResult.CONTINUE;
			}

			public FileVisitResult postVisitDirectory(Path dir, IOException exc)
					throws IOException {
				System.out.println("访问目标目录之后：" + dir);
				return FileVisitResult.CONTINUE;
			}
			
		});
		
		
	}
	
}
