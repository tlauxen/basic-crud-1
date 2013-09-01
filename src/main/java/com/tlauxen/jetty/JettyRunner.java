package com.tlauxen.jetty;

import org.eclipse.jetty.server.Server;

public class JettyRunner {
	
	public static void main(String[] args) throws Exception {
		ServerFactory factory = new ServerFactory(new ConfigurationBuilder()
		.withContextPath("/")
		.withPort(8080)
		.withBaseResource("src/main/webapp")
		.build());

		Server server = factory.buildServer();
		server.start();
		server.join();
	}
}
