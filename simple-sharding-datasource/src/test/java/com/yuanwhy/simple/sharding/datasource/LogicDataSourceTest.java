package com.yuanwhy.simple.sharding.datasource;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.sql.DataSource;
import java.util.Map;

/**
 * Created by yuanwhy on 17/3/27.
 */
public class LogicDataSourceTest {




    @Test
    public void testCreateLogicDataSource(){


        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("datasource.xml");

        DataSource dataSource = (DataSource) applicationContext.getBean("dataSource");




    }

}
