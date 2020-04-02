package suyan.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import suyan.BLL.service.IAccountService;
import suyan.utils.TransactionManger;

import javax.sql.DataSource;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Author:liang;
 * Date:2020/4/2;
 * Time:14:46;
 * Project Name:Spring_Practice;
 * Package Name:suyan.config;
 * description:
 */

/**
 * 配置类
 */
@Configuration//声明是一个配置类
@ComponentScan(basePackages = "suyan")//要扫描的包
public class AccountConfig {

    @Qualifier("AccountService")
    @Autowired
    private IAccountService service;//注入未加入事务管理的IAccountService，这个是被代理对象，也即被增强的对象

    @Autowired
    TransactionManger tmanger;//事务管理类

    //@Bean要和@Configuration一起使用，在方法上标注，此方法只被spring执行且执行一次，将返回值放入容器
    @Bean(name="dataSource")
    public DataSource createDataSource() {
        try {
            ComboPooledDataSource ds = new ComboPooledDataSource();
            ds.setUser("root");
            ds.setPassword("hello,mysql");
            ds.setDriverClass("com.mysql.jdbc.Driver");
            ds.setJdbcUrl("jdbc:mysql:///bank");
            return ds;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Bean(name="runner")
    public QueryRunner createQueryRunner(DataSource dataSource){
        return new QueryRunner(dataSource);
    }


    @Bean(name ="ats")
    public IAccountService getAccountService(){

        return (IAccountService) Proxy.newProxyInstance(service.getClass().getClassLoader(),service.getClass().getInterfaces(),
                new InvocationHandler() {
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        Object rt=null;
                        try {
                            tmanger.beginTransaction();
                            //不会递归，只调用一次
                            rt=method.invoke(service,args);
                            tmanger.commit();
                            return rt;
                        }
                        catch (Exception e){
                            tmanger.rollback();
                        }
                        finally {
                            tmanger.release();
                        }
                        return null;
                    }
                });
        
    }
}
