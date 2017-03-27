package com.yuanwhy.simple.sharding.datasource;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.sql.DataSource;

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
