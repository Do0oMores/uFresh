package top.mores.ufresh.Utils;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "message")
public class NotificationMessage {
    private OrderFinish orderFinish;

    public OrderFinish getOrderFinish() {
        return orderFinish;
    }

    public void setOrderFinish(OrderFinish orderFinish) {
        this.orderFinish = orderFinish;
    }

    public static class OrderFinish {
        private String title;
        private String content;
        private String type;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
