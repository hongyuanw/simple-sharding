package com.yuanwhy.simple.sharding.jdbc;

import com.yuanwhy.simple.sharding.dao.CrudUtil;
import com.yuanwhy.simple.sharding.entity.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.*;

/**
 * Created by yuanwhy on 17/3/27.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:datasource.xml"
})
public class LogicDataSourceTest {

    @Autowired
    private DataSource dataSource;

    /**
     * 测试Statement下的CURD和单库事务
     * @throws Exception
     */
    @Test
    public void testCreateLogicDataSourceAndStatement() throws Exception {

        Connection connection1 = dataSource.getConnection();
        Connection connection2 = dataSource.getConnection();

        connection1.setAutoCommit(false);

        User user = new User(123, "yuanwhy", 18, User.Role.BUYER.getId());
        CrudUtil.insertUser(connection1, user);
        User foundUserFromConnection1 = CrudUtil.selectUser(connection1, user);
        Assert.assertTrue(user.equals(foundUserFromConnection1));

        User foundUserFromConnection2 = CrudUtil.selectUser(connection2, user);
        Assert.assertTrue(foundUserFromConnection2 == null); // 事务隔离,connection2一定读不到connection1的数据

        connection1.commit();
        User foundOneUserFromConnection2 = CrudUtil.selectUser(connection2, user);
        Assert.assertTrue(foundOneUserFromConnection2.equals(user)); // connection1的事务提交后,connection2可以读到持久化的数据



        user.setAge(20);
        CrudUtil.updateUser(connection1, user);
        foundUserFromConnection1 = CrudUtil.selectUser(connection1, user);

        Assert.assertTrue(user.equals(foundUserFromConnection1));

        CrudUtil.deleteUser(connection1, user);
        foundUserFromConnection1 = CrudUtil.selectUser(connection1, user);
        Assert.assertTrue(foundUserFromConnection1 == null);
        foundUserFromConnection2 = CrudUtil.selectUser(connection2, user);
        Assert.assertTrue(foundUserFromConnection2 == null);  //确定真的删除成功


    }


    /**
     * 测试PrepareStatement下的CRUD
     * @throws Exception
     */
    @Test
    public void testCreateLogicDataSourceAndPrepareStatement() throws Exception {


        try (Connection connection = dataSource.getConnection()) {


            User user = new User(123, "yuanwhy", 18, User.Role.BUYER.getId());

            CrudUtil.insertUserWithPrepareStatement(connection, user);
            User foundUser = CrudUtil.selectUserWithPrepareStatement(connection, user);
            Assert.assertTrue(user.equals(foundUser));

            user.setAge(20);
            CrudUtil.updateUserWithPrepareStatement(connection, user);
            foundUser = CrudUtil.selectUserWithPrepareStatement(connection, user);

            Assert.assertTrue(user.equals(foundUser));

            CrudUtil.deleteUserWithPrepareStatement(connection, user);
            foundUser = CrudUtil.selectUserWithPrepareStatement(connection, user);
            Assert.assertTrue(foundUser == null);

        }

    }



}
