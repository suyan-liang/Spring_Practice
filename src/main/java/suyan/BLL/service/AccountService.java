package suyan.BLL.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import suyan.DAL.dao.Accountdao;
import suyan.domain.Account;
import suyan.utils.TransactionManger;

/**
 * Author:liang;
 * Date:2020/4/1;
 * Time:9:09;
 * Project Name:Spring_Practice;
 * Package Name:BLL.service;
 * description:
 */
@Service("AccountService")
public class AccountService implements IAccountService{
    @Autowired
    private Accountdao accountdao;

    @Autowired
    private TransactionManger tmanger;
    public void saveAccountService(Account account) {
        accountdao.saveAccount(account);
    }

    public void transfer(String source, String target, double money) {
        try {
            tmanger.beginTransaction();
            Account from=accountdao.getAccountByName(source);
            Account to =accountdao.getAccountByName(target);
            from.setMoney(from.getMoney()-money);
            to.setMoney(to.getMoney()+money);
            accountdao.updateAccountByName(from);
//            int i=1/0;
            accountdao.updateAccountByName(to);
            tmanger.commit();
        }
        catch (Exception e){
            tmanger.rollback();
        }
        finally {
            tmanger.release();
        }

    }
}
