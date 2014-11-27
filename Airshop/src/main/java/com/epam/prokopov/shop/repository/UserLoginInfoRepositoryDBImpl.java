package com.epam.prokopov.shop.repository;

import com.epam.prokopov.shop.model.UserLoginInfo;
import com.epam.prokopov.shop.model.UserRoles;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserLoginInfoRepositoryDBImpl implements UserLoginInfoRepository {

    private static final String GET_USER_QUERY = "SELECT * from user WHERE email = ?";
    private static final String SET_USER_INFO = "UPDATE user SET last_visit_time = ?, block_end_time = ?, tries = ? WHERE email = ?;";

    @Override
    public UserLoginInfo getUserLoginInfo(Connection connection, String email) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(GET_USER_QUERY)) {
            statement.setString(1, email);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                UserLoginInfo userInfo = new UserLoginInfo();
                userInfo.setLogin(rs.getString("email"));
                userInfo.setPass(rs.getString("password"));
                userInfo.setRole(UserRoles.valueOf(rs.getString("role").toUpperCase()));
                userInfo.setLastVisitTime(rs.getLong("last_visit_time"));
                userInfo.setTimeOfTheEnd(rs.getLong("block_end_time"));
                userInfo.setTries(rs.getInt("tries"));
                return userInfo;
            }
        }
        return null;
    }

    @Override
    public void setUserLoginInfo(Connection connection, UserLoginInfo userLoginInfo) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(SET_USER_INFO)) {
            statement.setLong(1, userLoginInfo.getLastVisitTime());
            statement.setLong(2, userLoginInfo.getTimeOfTheEnd());
            statement.setInt(3, userLoginInfo.getTries());
            statement.setString(4, userLoginInfo.getLogin());
            statement.executeUpdate();
        }
    }
}
