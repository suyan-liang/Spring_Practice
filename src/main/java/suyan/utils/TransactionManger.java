package suyan.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

/**
 * Author:liang;
 * Date:2020/4/1;
 * Time:17:11;
 * Project Name:Spring_Practice;
 * Package Name:suyan.utils;
 * description:
 */
@Component("transaction")
public class TransactionManger {
    @Autowired
    private ConnectionUtils cus;

    /**
     * 开启事务
     * 其实就是把自动提交设置为false
     */
    public void beginTransaction(){
        try {

            cus.getThreadConnection().setAutoCommit(false);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 提交事务
     */
    public void commit(){
        try {
            cus.getThreadConnection().commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 回滚事务
     */
    public void rollback(){
        try {
            cus.getThreadConnection().rollback();
            System.out.println("roll没");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 释放资源
     * 把连接还给池子
     */
    public void release(){
        try {
            //把连接关闭
            cus.getThreadConnection().close();
            //把连接和线程解绑，否则线程回到池子中会有一个关闭的线程，下一次再取到就是带着关闭连接的线程，会出问题的
            cus.removeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
