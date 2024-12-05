package top.mores.ufresh.Service;

import jakarta.annotation.Resource;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import top.mores.ufresh.DAO.MybatisUtils;
import top.mores.ufresh.DAO.UserDao;
import top.mores.ufresh.POJO.User;
import top.mores.ufresh.Service.Mail.EmailService;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
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
    public Map<Integer, String> addUser(String userName, String password, String email) {
        Map<Integer, String> response = new HashMap<>();

        if (checkUser(userName)) {
            SqlSession sqlSession = MybatisUtils.getSqlSession();
            UserDao userDao = sqlSession.getMapper(UserDao.class);

            User user = new User();

            LocalDateTime currentTime = LocalDateTime.now();
            user.setUser_name(userName);
            user.setPassword(password);
            user.setRegister_time(currentTime);// 设置注册时间为当前时间
            user.setEmail(email);

            if (userDao.addUser(user) == 1) {
                //提交事务
                sqlSession.commit();
                response.put(200, "注册成功");
            } else {
                response.put(500, "注册失败");
            }
            sqlSession.close();
        } else {
            response.put(404, "已存在该用户名");
        }
        return response;
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
    public Map<Integer, String> mail(String mail) {
        Map<Integer, String> response = new HashMap<>();

        String redisKey = "mail:request:" + mail;
        Long lastRequestTime = redisTemplate.opsForValue().get(redisKey);

        long now = System.currentTimeMillis();

        if (lastRequestTime != null && now - lastRequestTime < 60000) {
            response.put(500, "请求太频繁");
            return response;
        }

        redisTemplate.opsForValue().set(redisKey, now, 60, TimeUnit.SECONDS);

        try {
            String verificationCode = emailService.randomMailCode();
            String content = "<h1>您的验证码是：" + verificationCode + "，五分钟内有效" + "</h1>";
            boolean isSent = emailService.sendEmail(mail, "【优鲜uFresh】", content);

            if (isSent) {
                response.put(200, "验证码已发送");
            } else {
                response.put(500, "发送验证码失败，请稍后再试");
            }
        } catch (Exception e) {
            response.put(500, "发生错误：" + e.getMessage());
        }
        return response;
    }

    /**
     * 返回验证码验证结果
     *
     * @param code 传入验证码
     * @return 验证结果
     */
    public Map<Integer, String> verifyCode(String code) {
        Map<Integer, String> response = new HashMap<>();
        if (emailService.verifyCode(code)) {
            response.put(200, "验证成功");
        } else {
            response.put(400, "验证码错误或已过期");
        }
        return response;
    }
}
