package suyan.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import suyan.BLL.service.IAccountService;
import suyan.utils.TransactionManger;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Author:liang;
 * Date:2020/4/2;
 * Time:9:43;
 * Project Name:Spring_Practice;
 * Package Name:suyan.factory;
 * description:
 */

public class MyBeanFactory {

    private IAccountService service;
    TransactionManger tmanger;
    /**
     * 获取AccountService的动态代理
     * @return
     */
    @Bean("ats")
    public IAccountService getAccountService(){

        service=(IAccountService) Proxy.newProxyInstance(service.getClass().getClassLoader(),service.getClass().getInterfaces(),
                new InvocationHandler() {
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        Object rt=null;
                        try {
                            tmanger.beginTransaction();
                            method.invoke(proxy,args);
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
        return service;
    }
}
