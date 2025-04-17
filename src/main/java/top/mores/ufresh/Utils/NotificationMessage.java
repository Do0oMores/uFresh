package top.mores.ufresh.Utils;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "message")
public class NotificationMessage {
    private OrderFinish orderFinish;
    private AfterSalesFinish afterSalesFinish;
    private OrderSubmit orderSubmit;
    private AfterSalesSubmit afterSalesSubmit;

    public OrderFinish getOrderFinish() {
        return orderFinish;
    }

    public void setOrderFinish(OrderFinish orderFinish) {
        this.orderFinish = orderFinish;
    }

    public AfterSalesFinish getAfterSalesFinish() {
        return afterSalesFinish;
    }

    public void setAfterSalesFinish(AfterSalesFinish afterSalesFinish) {
        this.afterSalesFinish = afterSalesFinish;
    }

    public OrderSubmit getOrderSubmit() {
        return orderSubmit;
    }

    public void setOrderSubmit(OrderSubmit orderSubmit) {
        this.orderSubmit = orderSubmit;
    }

    public AfterSalesSubmit getAfterSalesSubmit() {
        return afterSalesSubmit;
    }

    public void setAfterSalesSubmit(AfterSalesSubmit afterSalesSubmit) {
        this.afterSalesSubmit = afterSalesSubmit;
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

    public static class AfterSalesFinish {
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

    public static class OrderSubmit {
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

    public static class AfterSalesSubmit {
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
