import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import suyan.BLL.service.IAccountService;
import suyan.config.AccountConfig;
import suyan.domain.Account;
import suyan.factory.MyBeanFactory;

import javax.annotation.Resource;

/**
 * Author:liang;
 * Date:2020/4/1;
 * Time:9:58;
 * Project Name:Spring_Practice;
 * Package Name:PACKAGE_NAME;
 * description:
 */
public class test {

    private IAccountService iac;


    /**
     * 在用户层
     * 创建一个容器，读取配置类，生成注解对应对象
     *
     * 通过getBean获取业务层的一个接口
     * 调用业务层的方法
     *
     *
     */
    @Test
    public void test_ioc(){


        ApplicationContext ac=new AnnotationConfigApplicationContext(AccountConfig.class);

        Account acc=new Account();
        acc.setName("ccc");
        acc.setMoney(666);


        iac= (IAccountService) ac.getBean("ats");
        System.out.println("iac="+iac);
        iac.saveAccountService(acc);
//        iac.transfer("aaa","bbb",100);
    }
}
