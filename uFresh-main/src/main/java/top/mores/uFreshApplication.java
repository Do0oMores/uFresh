package top.mores;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

@SpringBootApplication
public class uFreshApplication {
    public static void main(String[] args) throws UnknownHostException {
        ConfigurableApplicationContext applicationContext= SpringApplication.run(uFreshApplication.class, args);
        info(applicationContext);
    }

    static void info(ConfigurableApplicationContext applicationContext) throws UnknownHostException {
        Environment env = applicationContext.getEnvironment();
        String ip= InetAddress.getLocalHost().getHostAddress();
        String port=env.getProperty("server.port");
        String active=env.getProperty("spring.application.active");
        String contextPath=env.getProperty("server.servlet.context-path");
        if (contextPath==null){
            contextPath="";
        }
        System.out.println("\n----------------------------------------------------------\n\t" +
                "程序【" + active + "】环境已启动! 地址如下:\n\t" +
                "Local: \t\thttp://localhost:" + port + contextPath + "\n\t" +
                "External: \thttp://" + ip + ':' + port + contextPath + '\n' +
                "Swagger文档: \thttp://" + ip + ":" + port + contextPath + "/doc.html\n" +
                "----------------------------------------------------------");
    }
}
