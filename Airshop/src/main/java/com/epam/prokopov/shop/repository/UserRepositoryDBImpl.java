package com.epam.prokopov.shop.repository;

import com.epam.prokopov.shop.model.User;
import com.epam.prokopov.shop.model.UserLoginInfo;
import com.epam.prokopov.shop.model.UserRoles;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepositoryDBImpl implements UserRepository {

    private static final String INSERT_USER_QUERY = "INSERT INTO user (email, password, name, surname, photo, role, delivery, last_visit_time, tries, block_end_time) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String EXIST_USER_QUERY = "SELECT COUNT(*) AS count  from user WHERE email = ?;";
    private static final String GET_USER_QUERY = "SELECT * from user WHERE email = ?";
    private static final String DELETE_USER_QUERY = "DELETE FROM user WHERE email = ?;";
    private static final String UPDATE_USER = "UPDATE user SET email = ?, password = ?, name = ?, surname = ?, photo = ?, role = ?, delivery = ?, last_visit_time = ?, block_end_time = ?, tries = ? WHERE email = ?;";


    @Override
    public void addNewUser(Connection connection, User user) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_USER_QUERY)) {
            int i = 0;

            statement.setString(++i, user.getEmail());
            statement.setString(++i, user.getPass());
            statement.setString(++i, user.getName());
            statement.setString(++i, user.getSurname());
            statement.setString(++i, user.getPhoto());
            statement.setString(++i, user.getRole().name());
            statement.setBoolean(++i, user.getDelivery());
            statement.setLong(++i, -1);
            statement.setInt(++i, 0);
            statement.setLong(++i, -1);

            statement.executeUpdate();
        }
    }

    @Override
    public boolean exist(Connection connection, String email) throws SQLException {
        int rowCount;
        try (PreparedStatement statement = connection.prepareStatement(EXIST_USER_QUERY)) {
            statement.setString(1, email);
            ResultSet rs = statement.executeQuery();
            rs.next();
            rowCount = rs.getInt("count");
        }
        return rowCount != 0;
    }

    @Override
    public void removeUser(Connection connection, String email) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_USER_QUERY)) {
            statement.setString(1, email);
            statement.executeUpdate();
        }
    }

    @Override
    public void editUser(Connection connection, User user, UserLoginInfo userLoginInfo) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_USER)) {
            int i = 0;
            statement.setString(++i, user.getEmail());
            statement.setString(++i, user.getPass());
            statement.setString(++i, user.getName());
            statement.setString(++i, user.getSurname());
            statement.setString(++i, user.getPhoto());
            statement.setString(++i, user.getRole().name());
            statement.setBoolean(++i, user.getDelivery());
            statement.setLong(++i, userLoginInfo.getLastVisitTime());
            statement.setLong(++i, userLoginInfo.getTimeOfTheEnd());
            statement.setInt(++i, userLoginInfo.getTries());
            statement.setString(++i, user.getEmail());

            statement.executeUpdate();
        }
    }

    @Override
    public User getUser(Connection connection, String email) throws SQLException{
        try (PreparedStatement statement = connection.prepareStatement(GET_USER_QUERY)) {
            statement.setString(1, email);
            ResultSet rs = statement.executeQuery();
            if(rs.next()) {
                User user = new User();
                user.setEmail(rs.getString("email"));
                user.setDelivery(rs.getBoolean("delivery"));
                user.setName(rs.getString("name"));
                user.setPass(rs.getString("password"));
                user.setRole(UserRoles.valueOf(rs.getString("role").toUpperCase()));
                user.setSurname(rs.getString("surname"));
                user.setPhoto(rs.getString("photo"));
                return user;
            }
        }
        return null;
    }
}
