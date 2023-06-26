package com.company.db;

import com.company.impl.AppInfo;
import com.company.impl.RawItem;
import com.company.impl.RawPost;
import com.company.impl.UserInfo;
import com.company.utils.ConnectionUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBController {
    public DBController(AppInfo appInfo){}
    public List<UserInfo> getAllUsersVKBody() throws SQLException {
        ConnectionUtil db = new ConnectionUtil();
        Connection con = db.connect_to_db("Jornalik", "postgres", "Di~S8Qgs~%");
        return db.selectAllUserInfo(con, "vk_table");
    }


    public void putPost(UserInfo user, RawPost post, Connection con, ConnectionUtil db) {
        db.insert_row_into_raw_posts(con, "raw_posts", user, post);
    }


    public void putItem(int postId, RawItem item, Connection con, ConnectionUtil db) {
        db.insert_row_into_raw_items(con, "raw_items", postId, item);
    }
}
