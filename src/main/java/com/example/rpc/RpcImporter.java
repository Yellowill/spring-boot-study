package com.example.rpc;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.net.Socket;

import com.example.rpc.impl.EchoServiceImpl;

public class RpcImporter<S> {
	@SuppressWarnings("unchecked")
	public S importer(final Class<?> serviceClass, final InetSocketAddress addr) {
		
		return (S) Proxy.newProxyInstance(serviceClass.getClassLoader(),
				new Class<?>[] {serviceClass.getInterfaces()[0]}, new InvocationHandler() {
					@Override
					public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
						Socket socket = null;
						ObjectOutputStream output = null;
						ObjectInputStream input = null;
						try {
							socket = new Socket();
							socket.connect(addr);
							output = new ObjectOutputStream(socket.getOutputStream());
							output.writeUTF(serviceClass.getName());
							output.writeUTF(method.getName());
							output.writeObject(method.getParameterTypes());
							output.writeObject(args);
							input = new ObjectInputStream(socket.getInputStream());
							return input.readObject();
						} finally {
							if(socket != null) {
								socket.close();
							}
							if(output != null) {
								output.close();
							}
							if(input != null) {
								input.close();
							}
						}
					}
				});
	}
	//rpcTest-client启动
	public static void main(String[] args) throws Exception{
//		new Thread(new Runnable() {
//			@Override
//			public void run() {
//				try {
//					RpcExporter.exporter("localhost",8088);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		}).start();
		RpcImporter<EchoService> impoter = new RpcImporter<EchoService>();
		EchoService echo = impoter.importer(EchoServiceImpl.class, new InetSocketAddress("localhost",8088));
		System.out.println(echo.echo("Are you ok ? I am from importer!"));
	}
}
