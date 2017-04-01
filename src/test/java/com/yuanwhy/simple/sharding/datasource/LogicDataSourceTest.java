package com.yuanwhy.simple.sharding.datasource;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by yuanwhy on 17/3/27.
 */
public class LogicDataSourceTest {




    @Test
    public void testCreateLogicDataSource() throws Exception{

        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("datasource.xml");

        DataSource dataSource = (DataSource) applicationContext.getBean("dataSource");

        Connection connection = dataSource.getConnection();
        Statement statement = connection.createStatement();
        statement.execute("select id, name, age from user where id = 10 and role = 0");

        ResultSet resultSet = statement.getResultSet();

        while (resultSet.next()) {
            Assert.assertEquals("john", resultSet.getString(2));
            Assert.assertEquals(20, resultSet.getInt(3));
        }

        statement.execute("select id, name, age from user where id = 25 and role = 1");

        ResultSet resultSetDb1 = statement.getResultSet();

        while (resultSetDb1.next()) {
            Assert.assertEquals("jack", resultSetDb1.getString(2));
            Assert.assertEquals(40, resultSetDb1.getInt(3));
        }

    }

}
