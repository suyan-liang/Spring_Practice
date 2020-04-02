package suyan.BLL.service;

import suyan.domain.Account;

/**
 * Author:liang;
 * Date:2020/4/1;
 * Time:9:09;
 * Project Name:Spring_Practice;
 * Package Name:BLL.service;
 * description:
 */


public interface IAccountService {
    /**
     * 业务层，对account操作，并调用持久层的dao接口实现保存用户功能
     * @param account
     */
    void saveAccountService(Account account);

    /**
     * 转账
     * @param source
     * @param target
     * @param money
     */
    void transfer(String source,String target,double money);
}
