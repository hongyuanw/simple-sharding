package com.yuanwhy.simple.sharding.dao;

import com.yuanwhy.simple.sharding.entity.User;

import java.sql.*;

/**
 * Created by yuanwhy on 17/4/19.
 */
public class CrudUtil {


    public static void insertUser(Connection connection, User user) throws SQLException {

        String sql = "INSERT INTO user(id, name, age, role) VALUES (%d, '%s', %d, %d)";

        sql = String.format(sql, user.getId(), user.getName(), user.getAge(), user.getRole());

        try (Statement statement = connection.createStatement()) {

            statement.execute(sql);

        }
    }

    public static void insertUserWithPrepareStatement(Connection connection, User user) throws SQLException {

        String sql = "INSERT INTO user(id, name, age, role) VALUES (?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, user.getId());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setInt(3, user.getAge());
            preparedStatement.setInt(4, user.getRole());

            preparedStatement.execute();

        }
    }

    public static void updateUser(Connection connection, User user) throws SQLException {

        String sql = "UPDATE user SET age = %d , name = '%s' WHERE  id = %d and role = %d";

        sql = String.format(sql, user.getAge(), user.getName(), user.getId(), user.getRole());

        try (Statement statement = connection.createStatement()) {

            statement.execute(sql);

        }


    }

    public static void updateUserWithPrepareStatement(Connection connection, User user) throws SQLException {

        String sql = "UPDATE user SET age = ? , name = ? WHERE  id = ? and role = ?";


        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, user.getAge());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setInt(3, user.getId());
            preparedStatement.setInt(4, user.getRole());

            preparedStatement.execute();
        }


    }

    public static User selectUser(Connection connection, User user) throws SQLException {

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

    public static User selectUserWithPrepareStatement(Connection connection, User user) throws SQLException {

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

    public static User getUserFromResultSet(ResultSet resultSet) throws SQLException {
        User foundUser;
        foundUser = new User();

        foundUser.setId(resultSet.getInt(1));
        foundUser.setName(resultSet.getString(2));
        foundUser.setAge(resultSet.getInt(3));
        foundUser.setRole(resultSet.getInt(4));
        return foundUser;
    }

    public static void deleteUser(Connection connection, User user) throws SQLException {

        String sql = "DELETE FROM user WHERE  id = %d and role = %d";

        sql = String.format(sql, user.getId(), user.getRole());

        try (Statement statement = connection.createStatement()) {

            statement.execute(sql);

        }

    }

    public static void deleteUserWithPrepareStatement(Connection connection, User user) throws SQLException {

        String sql = "DELETE FROM user WHERE  id = ? and role = ?";


        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, user.getId());
            preparedStatement.setInt(2, user.getRole());

            preparedStatement.execute();

        }

    }
}
