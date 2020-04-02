package suyan.DAL.impl;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import suyan.DAL.dao.Accountdao;
import suyan.domain.Account;
import suyan.utils.ConnectionUtils;

import java.sql.SQLException;
import java.util.List;

/**
 * Author:liang;
 * Date:2020/4/1;
 * Time:9:16;
 * Project Name:Spring_Practice;
 * Package Name:DAL.domain;
 * description:
 */
@Repository("Accountdao")
public class AccountdaoImpl implements Accountdao {
    @Autowired
    private QueryRunner runner;
    @Autowired
    private ConnectionUtils cus;
    public void saveAccount(Account account) {

        String sql="insert into account(name,money) values(?,?)";

        try {

            runner.update(cus.getThreadConnection(),sql,account.getName(),account.getMoney());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateAccountByName(Account account) {
        String sql="update account set money=? where name=?";
        try {
            runner.update(cus.getThreadConnection(),sql,account.getMoney(),account.getName());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Account getAccountByName(String name) {
        String sql="select * from account where name=?";
        try {
            List<Account> ans = runner.query(cus.getThreadConnection(),sql, new BeanListHandler<Account>(Account.class), name);
            if (ans.size()>1)
                throw new RuntimeException("查询到的账户多于一个，数据异常");
            if (ans.size()==0)
                return null;
            return ans.get(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
