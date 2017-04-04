package cn.junety.alarm.api.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by caijt on 2017/3/29.
 */
public class AlarmSender {

    private static Logger logger = LoggerFactory.getLogger(AlarmSender.class);

    private static final String ALARM_API = "http://localhost:8089/v1/alarm";

    private final BlockingQueue<String> queue;

    public AlarmSender(int queueSize) {
        queue = new ArrayBlockingQueue<>(queueSize);
        new Thread(() -> {
            logger.info("start alarm api success");
            while(true) {
                try {
                    sendPost(queue.take());
                } catch (Exception e) {
                    logger.error("send alarm message error, caused by", e);
                }
            }
        }).start();
    }

    public void send(String body) {
        if (!queue.offer(body)) {
            logger.debug("queue full, throw away, content:{}", body);
        }
    }

    private boolean sendPost(String body) {
        StringBuilder buffer = new StringBuilder();
        HttpURLConnection httpUrlConn = null;
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        try {
            URL e = new URL(ALARM_API);
            httpUrlConn = (HttpURLConnection)e.openConnection();
            httpUrlConn.setDoOutput(true);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);
            httpUrlConn.setRequestMethod("POST");
            httpUrlConn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            OutputStream statusCode = httpUrlConn.getOutputStream();
            statusCode.write(body.getBytes("UTF-8"));
            statusCode.close();

            inputStream = httpUrlConn.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            bufferedReader = new BufferedReader(inputStreamReader);

            String str;
            while((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            logger.debug("send alarm success, request:{}, response:{}", body, buffer.toString());

            httpUrlConn.disconnect();
        } catch (Exception e) {
            logger.error("send alarm error, body:{}", body, e);
        } finally {
            close(bufferedReader, inputStreamReader, inputStream);
            if (httpUrlConn != null) {
                httpUrlConn.disconnect();
            }
        }
        return false;
    }

    private void close(Closeable... closeables) {
        for (Closeable closeable : closeables) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (Exception ignore) {
                }
            }
        }
    }
}
