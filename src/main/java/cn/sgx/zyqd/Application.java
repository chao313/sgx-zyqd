package cn.sgx.zyqd;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;

import static jdk.nashorn.internal.runtime.regexp.joni.Syntax.Java;

@SpringBootApplication
public class Application {
    private static Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) throws FileNotFoundException {
        System.setProperty("java.net.preferIPv4Stack","true");
        SpringApplication.run(Application.class, args);
    }
}
