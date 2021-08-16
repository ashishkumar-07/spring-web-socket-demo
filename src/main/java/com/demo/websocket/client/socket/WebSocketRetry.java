package com.demo.websocket.client.socket;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

import com.demo.websocket.client.MyException;

@Component
public class WebSocketRetry {
	@Lazy
	@Autowired
	private SocketConnector connector;

	private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketRetry.class);
	private static int COUNTER = 0;

	@Retryable(value = { MyException.class }, maxAttempts = 4, backoff = @Backoff(value = 2000))
	public void retrySocketConnection(MyWebSocketConnectionManager connectionManager) throws MyException {

		COUNTER++;
		LOGGER.info("COUNTER = " + COUNTER);

		LOGGER.info("retrying");

		try {
			if (!connectionManager.isConnectionOpen()) {
				connector.reconnect();
			}

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw new MyException();
		}

		try {
			TimeUnit.SECONDS.sleep(5);
		} catch (InterruptedException e1) {

			e1.printStackTrace();
		}

		LOGGER.warn("connection status : " + connectionManager.isConnectionOpen());

		if (!connectionManager.isConnectionOpen()) {
			LOGGER.error("connection is not open");
			throw new MyException();
		}

		LOGGER.info("connection is now open");
		COUNTER = 0;

	}

	@Recover
	public String getBackendResponseFallback(RuntimeException e) {
        System.out.println("All retries completed, so Fallback method called!!!");
        return "All retries completed, so Fallback method called!!!";
    }

}
