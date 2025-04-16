package top.mores.ufresh;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication(scanBasePackages = "top.mores.ufresh")
@MapperScan("top.mores.ufresh.Mappers")
@EnableConfigurationProperties
public class uFreshApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(uFreshApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(uFreshApplication.class);
    }
}
