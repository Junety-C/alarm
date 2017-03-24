package cn.junety.alarm.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by caijt on 2017/3/7.
 */
@SpringBootApplication
public class Bootstrap {

    private static final Logger logger = LoggerFactory.getLogger(Bootstrap.class);

    public static void main(String[] args) {
        try {
            SpringApplication.run(Bootstrap.class);
            logger.info("start alarm server success");
        } catch (Exception e) {
            logger.error("start alarm server error, caused by", e);
        }
    }
}
