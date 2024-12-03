package top.mores.ufresh.Service.Mail;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import top.mores.ufresh.DAO.MailVerifyDao;
import top.mores.ufresh.DAO.MybatisUtils;

import java.util.Random;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;
    private static final long EXPIRATION_TIME = 5 * 60 * 1000;

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
     * @return 生成的验证码并保存到Map
     */
    public String randomMailCode() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            int number = random.nextInt(characters.length());
            sb.append(characters.charAt(number));
        }
        saveCurrentTime(System.currentTimeMillis(), sb.toString());
        return sb.toString();
    }

    /**
     * 验证码验证
     *
     * @param code 传入验证码进行验证
     * @return 验证结果
     */
    public boolean verifyCode(String code) {
        Long time = getSaveTime(code);
        if (time == null) {
            return false;
        }
        if (System.currentTimeMillis() - time > EXPIRATION_TIME) {
            setCodeNull(code);
            return false;
        }
        return true;
    }

    /**
     * 保存验证码和其生成时间
     *
     * @param time 时间
     * @param code 验证码
     */
    public void saveCurrentTime(Long time, String code) {
        SqlSession session = MybatisUtils.getSqlSession();
        MailVerifyDao mailVerifyDao = session.getMapper(MailVerifyDao.class);
        if (mailVerifyDao.saveTime(time, code) == 1) {
            session.commit();
        } else {
            session.rollback();
            System.out.println(code + "时间更新失败");
        }
    }

    /**
     * 获取验证码生成时间
     *
     * @param code 验证码
     * @return 生成时的时间戳
     */
    public Long getSaveTime(String code) {
        SqlSession session = MybatisUtils.getSqlSession();
        MailVerifyDao mailVerifyDao = session.getMapper(MailVerifyDao.class);
        return mailVerifyDao.getTime(code);
    }

    /**
     * 设置验证码为空
     *
     * @param code 验证码
     */
    public void setCodeNull(String code) {
        SqlSession session = MybatisUtils.getSqlSession();
        MailVerifyDao mailVerifyDao = session.getMapper(MailVerifyDao.class);
        if (mailVerifyDao.setNull(code) == 1) {
            session.commit();
        } else {
            session.rollback();
            System.out.println(code + "清空失败");
        }
    }
}
