/**
 * 
 */
package com.yeelim.learn.java.nio;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.List;

import org.junit.Test;

/**
 * @author elim
 * @date 2014-12-28
 * @time 下午10:35:01
 * 
 */
public class WatchServiceTest {

	@Test
	public void test() {
		Path target = Paths.get("D:\\test");
		try {
			WatchService watchService = FileSystems.getDefault()
					.newWatchService();
			target.register(watchService, StandardWatchEventKinds.ENTRY_CREATE,
					StandardWatchEventKinds.ENTRY_DELETE,
					StandardWatchEventKinds.ENTRY_MODIFY,
					StandardWatchEventKinds.OVERFLOW);
			int i=0;
			while (i++<50) {
				WatchKey key = watchService.take();
				List<WatchEvent<?>> events = key.pollEvents();
				for (WatchEvent<?> event : events) {
					System.out.println(i + "-----event--------" + event);
					System.out.println(i + "-------event.kind---------" + event.kind());
					System.out.println(i + "--------event.context--------" + event.context());
					System.out.println(i + "--------event.kind.name--------" + event.kind().name());
					System.out.println(i + "------event.kind.type-------------" + event.kind().type());
					if (event.kind().equals(StandardWatchEventKinds.ENTRY_CREATE)) {
						System.out.println(i + "**********新建了一个文件，文件的路径是：" + target.resolve((Path)event.context()));
					} else if (event.kind().equals(StandardWatchEventKinds.ENTRY_MODIFY)) {
						System.out.println(i + "**********修改了一个文件，文件的路径是：" + target.resolve((Path)event.context()));
					} else if (event.kind().equals(StandardWatchEventKinds.ENTRY_DELETE)) {
						System.out.println(i + "*********删除了一个文件，文件的路径是：" + target.resolve((Path)event.context()));
					} else if (event.kind().equals(StandardWatchEventKinds.OVERFLOW)) {	//表示事件已经丢失了。
						System.out.println(i + "**********丢失了对应的事件，文件的路径是：" + target.resolve((Path)event.context()));
					} else {
						System.out.println(i + "*****************ERROR******************");
					}
				}
				key.reset();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
