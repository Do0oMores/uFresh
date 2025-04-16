package top.mores.ufresh.Utils;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.io.ClassPathResource;

import java.util.Properties;

public class YamlLoader {

    /**
     * 加载配置文件数据
     *
     * @return YAML文件内容
     */
    public static Properties loadYaml() {
        YamlPropertiesFactoryBean factory = new YamlPropertiesFactoryBean();
        factory.setResources(new ClassPathResource("../resources/NotificationMessage.yml"));
        return factory.getObject();
    }
}
