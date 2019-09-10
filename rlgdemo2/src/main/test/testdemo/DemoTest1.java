package testdemo;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DemoTest1 {
    @Test
    public void test1(){
        ApplicationContext ac = new ClassPathXmlApplicationContext("springmybatis.xml");
        ComboPooledDataSource dataSource = ac.getBean("dataSource", ComboPooledDataSource.class);
        System.out.println(dataSource.getJdbcUrl());
    }
}
