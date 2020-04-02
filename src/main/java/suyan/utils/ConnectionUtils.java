package suyan.utils;

import com.mchange.v2.c3p0.DataSources;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;

/**
 * Author:liang;
 * Date:2020/4/1;
 * Time:17:01;
 * Project Name:Spring_Practice;
 * Package Name:suyan.utils;
 * description:
 * 工具类，获取连接。
 * 其中有唯一的ThreadLocal
 *
 */
@Component("conn")
public class ConnectionUtils {
    private ThreadLocal<Connection> t=new ThreadLocal<Connection>();
    @Autowired
    private DataSource dataSource;

    /**
     * 获取当前线程对应的connection
     *
     * 获取当前线程对应ThreadLocal中的连接
     * 如果没有的话，从数据库获取一个然后放在ThreadLocal里
     * 有的话直接返回
     * @return 连接
     */
    public Connection getThreadConnection(){
        Connection conn=t.get();
        try {
            if (conn==null)
            {
                conn=dataSource.getConnection();
                t.set(conn);
            }
            return conn;
        }catch (Exception e)
        {
            e.printStackTrace();
        }
       return null;
    }

    /**
     * 把连接和线程解绑
     */
    public void removeConnection(){
        t.remove();
    }
}
