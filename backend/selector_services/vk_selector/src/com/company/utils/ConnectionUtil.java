package com.company.utils;


import com.company.impl.RawItem;
import com.company.impl.RawPost;
import com.company.impl.UserInfo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ConnectionUtil {

    public Connection connect_to_db(String dbname, String user, String pass) {
        String url = "jdbc:postgresql://localhost:5432/" + dbname;
        Properties props = new Properties();
        props.setProperty("user", user);
        props.setProperty("password", pass);
        try {
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(url, props);
            if (conn != null) {
                System.out.println("Connection Established");
            } else {
                System.out.println("Connection Failed");
            }
            return conn;
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void insert_row_into_raw_posts(Connection conn, String table_name, UserInfo user, RawPost post) {
        Statement statement;
        try {
            String query = "INSERT INTO " + table_name + " (post_id, source, user_id) VALUES (" + post.getPost_id() + ", 'vk', " + user.getUserId() + ");";
            statement = conn.createStatement();
            statement.executeUpdate(query);

        } catch (Exception e) {
        }
    }

    public void insert_row_into_raw_items(Connection conn, String table_name, int post_id, RawItem item) {
        Statement statement;
        try {
            String query = "INSERT INTO " + table_name + " (post_id, item, type) VALUES (" + post_id + ", '" + item.getItem() + "', '" + item.getType() + "');";
            statement = conn.createStatement();
            statement.executeUpdate(query);

        } catch (Exception e) {
        }
    }

    public List<UserInfo> selectAllUserInfo(Connection conn, String table_name) throws SQLException {
        Statement statement;
        List<UserInfo> userInfoList = new ArrayList<>();
        try {
            String query = "SELECT * FROM " + table_name + " JOIN FULL_USER_IDS ON VK_TABLE.vk_id = FULL_USER_IDS.vk_id;";
            statement = conn.createStatement();

            ResultSet result = statement.executeQuery(query);

            while (result.next()) {
                int vk_id = result.getInt("vk_id");
                String vk_token = result.getString("vk_token");
                int user_id = result.getInt("user_id");
                UserInfo userInfo = new UserInfo(user_id, vk_id, 0, vk_token);
                userInfoList.add(userInfo);
            }
        } catch (Exception e) {
        }
        return userInfoList;
    }

    public void delete(Connection conn, int idm) {
        Statement statement;
        try {
            String query = "DELETE FROM RECOVERY WHERE id=" + idm + ";";
            statement = conn.createStatement();
            statement.executeUpdate(query);
            System.out.println("account with id " + idm + " deleted" + "\n");
        } catch (Exception e) {
            System.out.println(e);
        }

    }
}