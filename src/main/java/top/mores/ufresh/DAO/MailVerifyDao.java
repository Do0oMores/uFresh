package top.mores.ufresh.DAO;

import org.springframework.stereotype.Repository;

@Repository
public interface MailVerifyDao {
    String getVerifyCode(String email);

    int saveMailCode(String email, String verifyCode);

    String getMail(String email);

    int saveNewCode(String code,String email);

    int saveTime(Long time,String code);

    Long getTime(String code);

    int setNull(String code);
}
