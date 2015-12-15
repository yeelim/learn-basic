package com.yeelim.learn.cxf.service;

import javax.jws.WebService;

@WebService
public interface HelloWorldService {

	public void sayHello(String who);
	
}
