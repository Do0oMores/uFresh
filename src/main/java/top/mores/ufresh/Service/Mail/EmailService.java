package top.mores.ufresh.Service.Mail;

import jakarta.annotation.Resource;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;
    private static final long EXPIRATION_TIME = 5 * 60 * 1000;  // 验证码有效时间（5分钟）
    @Resource
    private RedisTemplate<String, String> redisTemplate;
    private static final String VERIFICATION_CODE_KEY_PREFIX = "verification:code:";
    @Value("${spring.mail.username}")
    private String fromEmail;

    /**
     * 发送邮件
     *
     * @param to      收件人邮箱
     * @param subject 主题
     * @param content 内容
     * @return 是否成功发送
     */
    public boolean sendEmail(String to, String subject, String content) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            helper.setFrom(fromEmail);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);

            mailSender.send(mimeMessage);
            System.out.println("邮件发送成功："+to);
            return true;
        } catch (MailException | MessagingException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * 生成随机的6位数验证码
     *
     * @return 生成的验证码
     */
    public String randomMailCode() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            int number = random.nextInt(characters.length());
            sb.append(characters.charAt(number));
        }

        String code = sb.toString();
        saveCurrentCode(code);
        return code;
    }

    /**
     * 保存验证码到 Redis
     *
     * @param code 验证码
     */
    private void saveCurrentCode(String code) {
        String redisKey = VERIFICATION_CODE_KEY_PREFIX + code;
        redisTemplate.opsForValue().set(redisKey, code, EXPIRATION_TIME, TimeUnit.MILLISECONDS);  // 设置验证码过期时间
    }

    /**
     * 验证验证码
     *
     * @param code 传入验证码进行验证
     * @return 验证结果
     */
    public boolean verifyCode(String code) {
        String redisKey = VERIFICATION_CODE_KEY_PREFIX + code;
        // 获取 Redis 中的验证码
        String storedCode = redisTemplate.opsForValue().get(redisKey);

        if (storedCode == null) {
            return false;  // 未找到验证码
        }
        // 验证成功，删除已使用的验证码
        redisTemplate.delete(redisKey);
        return true;
    }
}
