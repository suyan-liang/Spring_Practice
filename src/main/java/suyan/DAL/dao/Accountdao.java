package suyan.DAL.dao;

import org.springframework.context.ApplicationContext;
import suyan.domain.Account;

/**
 * Author:liang;
 * Date:2020/4/1;
 * Time:9:15;
 * Project Name:Spring_Practice;
 * Package Name:DAL.dao;
 * description:
 */
public interface Accountdao {
    /**
     * 向account表插入用户
     * @param account
     */
    void saveAccount(Account account);

    /**
     * 根据name更新账户余额的信息
     * @param account
     */
    void updateAccountByName(Account account);

    /**
     * 根据name获取Account
     * @param name
     * @return
     */
    Account getAccountByName(String name);

}
