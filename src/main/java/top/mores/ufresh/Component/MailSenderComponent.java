package top.mores.ufresh.Component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.mores.ufresh.Service.Mail.EmailService;

@Component
public class MailSenderComponent {

    private final EmailService emailService;

    @Autowired
    public MailSenderComponent(EmailService emailService) {
        this.emailService = emailService;
    }

    /**
     * 邮件组件
     *
     * @param to   收件人
     * @param code 验证码
     * @return 是否发送成功
     */
    public boolean sendMail(String to, String code) {
        String subject = "【优鲜】激活您的账户";
        String content = "<h1>您的激活验证码是：" + code + "</h1>";
        return emailService.sendEmail(to, subject, content);
    }
}
