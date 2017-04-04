package com.yuanwhy.simple.sharding.datasource;

import com.yuanwhy.simple.sharding.entity.User;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.sql.DataSource;
import java.sql.*;

/**
 * Created by yuanwhy on 17/3/27.
 */
public class LogicDataSourceTest {


    @Test
    public void testCreateLogicDataSourceAndStatement() throws Exception {

        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("datasource.xml");

        DataSource dataSource = (DataSource) applicationContext.getBean("dataSource");

        try (Connection connection = dataSource.getConnection()) {


            User user = new User(123, "yuanwhy", 18, User.Role.BUYER.getId());

            testInsert(connection, user);
            testSelect(connection, user);
//            testUpdate(connection);
//            testDelete(connection);
        }

    }

    private void testInsert(Connection connection, User user) throws SQLException {

        String sql = "INSERT INTO USER(id, name, age, role) VALUES (%d, '%s', %d, %d)";

        sql = String.format(sql, user.getId(), user.getName(), user.getAge(), user.getRole());

        try (Statement statement = connection.createStatement()) {

            boolean result = statement.execute(sql);

            Assert.assertTrue(result);

        }
    }

    private void testUpdate(Connection connection) throws SQLException {
        // TODO: 17/4/4  
    }

    private void testSelect(Connection connection, User user) throws SQLException {

        String sql = "select id, name, age, role from user where id = %d and role = %d";

        sql = String.format(sql, user.getId(), user.getRole());

        try (Statement statement = connection.createStatement()) {

            statement.execute(sql);

            ResultSet resultSet = statement.getResultSet();

            while (resultSet.next()) {
                Assert.assertEquals(user.getId(), resultSet.getInt(1));
                Assert.assertEquals(user.getName(), resultSet.getString(2));
                Assert.assertEquals(user.getAge(), resultSet.getString(3));
                Assert.assertEquals(user.getRole(), resultSet.getInt(4));
            }
        }
    }

    private void testDelete(Connection connection) throws SQLException {
        // TODO: 17/4/4
    }

}
