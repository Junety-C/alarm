package cn.junety.alarm.api.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by caijt on 2017/3/29.
 */
public class HttpHelper {

    private static Logger logger = LoggerFactory.getLogger(HttpHelper.class);

    public static boolean sendPost(String url, String body) {
        StringBuilder buffer = new StringBuilder();
        try {
            URL e = new URL(url);
            HttpURLConnection httpUrlConn = (HttpURLConnection)e.openConnection();
            httpUrlConn.setDoOutput(true);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);
            httpUrlConn.setRequestMethod("POST");
            httpUrlConn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            OutputStream statusCode = httpUrlConn.getOutputStream();
            statusCode.write(body.getBytes("UTF-8"));
            statusCode.close();

            int statusCode1 = httpUrlConn.getResponseCode();
            if(statusCode1 == 200) {
                logger.debug("send alarm success, body:{}", body);
                httpUrlConn.disconnect();
                return true;
            }

            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String str;
            while((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }

            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            logger.error("send alarm fail, body:{}, error:{}", body, buffer.toString());
            httpUrlConn.disconnect();
        } catch (Exception e) {
            logger.error("send alarm error, body:{}", body, e);
        }
        return false;
    }
}
