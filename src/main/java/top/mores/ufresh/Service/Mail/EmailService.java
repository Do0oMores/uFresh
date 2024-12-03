package top.mores.ufresh.Service.Mail;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;
    private static final long EXPIRATION_TIME = 5 * 60 * 1000;  // 验证码有效时间（5分钟）
    private List<Map<String, Object>> verificationCodes = new ArrayList<>();  // 用于存储验证码

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

            helper.setFrom("ufresh_job@163.com");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);

            mailSender.send(mimeMessage);
            System.out.println("邮件发送成功");
            return true;
        } catch (MailException | MessagingException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
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
     * 保存验证码到内存中的列表
     *
     * @param code 验证码
     */
    private void saveCurrentCode(String code) {
        Map<String, Object> codeData = new HashMap<>();
        codeData.put("code", code);
        codeData.put("timestamp", System.currentTimeMillis());
        verificationCodes.add(codeData);
    }

    /**
     * 验证验证码
     *
     * @param code 传入验证码进行验证
     * @return 验证结果
     */
    public boolean verifyCode(String code) {
        // 查找验证码并验证
        Map<String, Object> codeData = findCodeData(code);
        if (codeData == null) {
            return false;  // 未找到验证码
        }

        long time = (long) codeData.get("timestamp");
        if (System.currentTimeMillis() - time > EXPIRATION_TIME) {
            verificationCodes.remove(codeData);  // 清除过期的验证码
            return false;  // 验证码已过期
        }

        // 验证成功，删除已使用的验证码
        verificationCodes.remove(codeData);
        return true;
    }

    /**
     * 查找验证码对应的数据
     *
     * @param code 验证码
     * @return 验证码数据，或者null
     */
    private Map<String, Object> findCodeData(String code) {
        for (Map<String, Object> data : verificationCodes) {
            if (data.get("code").equals(code)) {
                return data;
            }
        }
        return null;
    }
}
