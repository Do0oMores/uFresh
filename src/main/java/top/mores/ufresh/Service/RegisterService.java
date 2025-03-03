package top.mores.ufresh.Service;

import jakarta.annotation.Resource;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import top.mores.ufresh.DAO.MybatisUtils;
import top.mores.ufresh.DAO.UserDao;
import top.mores.ufresh.POJO.APIResponse;
import top.mores.ufresh.POJO.User;
import top.mores.ufresh.Service.Mail.EmailService;

import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

@Service
public class RegisterService {

    @Autowired
    private EmailService emailService;
    @Resource
    private RedisTemplate<String, Long> redisTemplate;

    /**
     * 增加用户
     *
     * @param userName 用户名
     * @param password 密码
     * @param email    邮箱
     * @return 添加结果
     */
    public APIResponse<Void> addUser(String userName, String password, String email) {
        if (checkUser(userName)) {
            try (SqlSession sqlSession = MybatisUtils.getSqlSession()) {
                UserDao userDao = sqlSession.getMapper(UserDao.class);

                User user = new User();
                LocalDate currentTime = LocalDate.now();
                user.setUser_name(userName);
                user.setPassword(password);
                user.setRegister_time(currentTime);// 设置注册时间为当前时间
                user.setEmail(email);

                if (userDao.addUser(user) == 1) {
                    sqlSession.commit();
                    return new APIResponse<>(200, "注册成功");
                } else {
                    return new APIResponse<>(500, "注册失败");
                }
            }
        } else {
            return new APIResponse<>(401, "已存在该用户名");
        }
    }

    /**
     * 检查是否已存在该用户名
     *
     * @param userName 传入需要检查的用户名
     * @return false:不存在；true:存在
     */
    public boolean checkUser(String userName) {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserDao userDao = sqlSession.getMapper(UserDao.class);
        String name = userDao.checkUser(userName);
        return name == null;
    }

    /**
     * 发送邮件处理
     *
     * @param mail 发送的邮件
     * @return 发送结果
     */
    public APIResponse<Void> mail(String mail) {
        String redisKey = "mail:request:" + mail;
        Long lastRequestTime = redisTemplate.opsForValue().get(redisKey);

        long now = System.currentTimeMillis();

        if (lastRequestTime != null && now - lastRequestTime < 60000) {
            return new APIResponse<>(401, "请求太频繁");
        }
        redisTemplate.opsForValue().set(redisKey, now, 60, TimeUnit.SECONDS);

        try {
            String verificationCode = emailService.randomMailCode();
            String content = "<div style='font-family: Arial, sans-serif; text-align: center; padding: 20px; background-color: #f9f9f9;'>"
                    + "<div style='max-width: 500px; margin: auto; padding: 20px; background-color: #ffffff; border-radius: 10px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);'>"
                    + "<h2 style='color: #2c3e50;'>您的验证码</h2>"
                    + "<p style='font-size: 18px; color: #333;'>"
                    + "您好，您的验证码是：<strong style='color: #e74c3c; font-size: 22px;'>" + verificationCode + "</strong>"
                    + "</p>"
                    + "<p style='color: #666; font-size: 14px;'>该验证码有效期为 <strong>5 分钟</strong>，请尽快使用。</p>"
                    + "<hr style='border: none; border-top: 1px solid #ddd;'>"
                    + "<p style='font-size: 12px; color: #999;'>如果这不是您的操作，请忽略此邮件。</p>"
                    + "</div></div>";

            boolean isSent = emailService.sendEmail(mail, "【优鲜uFresh】验证码", content);

            if (isSent) {
                return new APIResponse<>(200, "验证码已发送");
            } else {
                return new APIResponse<>(500, "发送验证码失败，请稍后再试");
            }
        } catch (Exception e) {
            return new APIResponse<>(500, "发生错误：" + e.getMessage());
        }
    }

    /**
     * 返回验证码验证结果
     *
     * @param code 传入验证码
     * @return 验证结果
     */
    public APIResponse<Void> verifyCode(String code) {
        if (emailService.verifyCode(code)) {
            return new APIResponse<>(200, "验证成功");
        } else {
            return new APIResponse<>(401, "验证码错误或已过期");
        }
    }
}
