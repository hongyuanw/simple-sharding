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

            insertUser(connection, user);
            User foundUser = selectUser(connection, user);
            Assert.assertTrue(user.equals(foundUser));

            user.setAge(20);
            updateUser(connection, user);
            foundUser = selectUser(connection, user);

            Assert.assertTrue(user.equals(foundUser));

            deleteUser(connection, user);
            foundUser = selectUser(connection, user);
            Assert.assertTrue(foundUser == null);

        }

    }


    @Test
    public void testCreateLogicDataSourceAndPrepareStatement() throws Exception {

        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("datasource.xml");

        DataSource dataSource = (DataSource) applicationContext.getBean("dataSource");

        try (Connection connection = dataSource.getConnection()) {


            User user = new User(123, "yuanwhy", 18, User.Role.BUYER.getId());

            insertUserWithPrepareStatement(connection, user);
            User foundUser = selectUserWithPrepareStatement(connection, user);
            Assert.assertTrue(user.equals(foundUser));

            user.setAge(20);
            updateUserWithPrepareStatement(connection, user);
            foundUser = selectUserWithPrepareStatement(connection, user);

            Assert.assertTrue(user.equals(foundUser));

            deleteUserWithPrepareStatement(connection, user);
            foundUser = selectUserWithPrepareStatement(connection, user);
            Assert.assertTrue(foundUser == null);

        }

    }

    private void insertUser(Connection connection, User user) throws SQLException {

        String sql = "INSERT INTO USER(id, name, age, role) VALUES (%d, '%s', %d, %d)";

        sql = String.format(sql, user.getId(), user.getName(), user.getAge(), user.getRole());

        try (Statement statement = connection.createStatement()) {

            statement.execute(sql);

        }
    }

    private void insertUserWithPrepareStatement(Connection connection, User user) throws SQLException {

        String sql = "INSERT INTO USER(id, name, age, role) VALUES (?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, user.getId());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setInt(3, user.getAge());
            preparedStatement.setInt(4, user.getRole());

            preparedStatement.execute();

        }
    }

    private void updateUser(Connection connection, User user) throws SQLException {

        String sql = "UPDATE USER SET age = %d , name = '%s' WHERE  id = %d and role = %d";

        sql = String.format(sql, user.getAge(), user.getName(), user.getId(), user.getRole());

        try (Statement statement = connection.createStatement()) {

            statement.execute(sql);

        }


    }

    private void updateUserWithPrepareStatement(Connection connection, User user) throws SQLException {

        String sql = "UPDATE USER SET age = ? , name = ? WHERE  id = ? and role = ?";


        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, user.getAge());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setInt(3, user.getId());
            preparedStatement.setInt(4, user.getRole());

            preparedStatement.execute();
        }


    }

    private User selectUser(Connection connection, User user) throws SQLException {

        String sql = "select id, name, age, role from user where id = %d and role = %d";

        sql = String.format(sql, user.getId(), user.getRole());

        User foundUser = null;
        try (Statement statement = connection.createStatement()) {

            statement.execute(sql);

            ResultSet resultSet = statement.getResultSet();

            while (resultSet.next()) {

                foundUser = getUserFromResultSet(resultSet);
            }
        }

        return foundUser;
    }

    private User selectUserWithPrepareStatement(Connection connection, User user) throws SQLException {

        String sql = "select id, name, age, role from user where id = ? and role = ?";

        User foundUser = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, user.getId());
            preparedStatement.setInt(2, user.getRole());

            preparedStatement.execute();

            ResultSet resultSet = preparedStatement.getResultSet();

            while (resultSet.next()) {

                foundUser = getUserFromResultSet(resultSet);
            }
        }

        return foundUser;
    }

    private User getUserFromResultSet(ResultSet resultSet) throws SQLException {
        User foundUser;
        foundUser = new User();

        foundUser.setId(resultSet.getInt(1));
        foundUser.setName(resultSet.getString(2));
        foundUser.setAge(resultSet.getInt(3));
        foundUser.setRole(resultSet.getInt(4));
        return foundUser;
    }

    private void deleteUser(Connection connection, User user) throws SQLException {

        String sql = "DELETE FROM USER WHERE  id = %d and role = %d";

        sql = String.format(sql, user.getId(), user.getRole());

        try (Statement statement = connection.createStatement()) {

            statement.execute(sql);

        }

    }

    private void deleteUserWithPrepareStatement(Connection connection, User user) throws SQLException {

        String sql = "DELETE FROM USER WHERE  id = ? and role = ?";


        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, user.getId());
            preparedStatement.setInt(2, user.getRole());

            preparedStatement.execute();

        }

    }

}
