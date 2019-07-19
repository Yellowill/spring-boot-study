package com.example.rpc.impl;

import com.example.rpc.EchoService;

public class EchoServiceImpl implements EchoService {

	@Override
	public String echo(String ping) {
		
		return ping != null ? ping + "-->I am ok.I am form Exporter!" : "I am ok.";
	}

}
