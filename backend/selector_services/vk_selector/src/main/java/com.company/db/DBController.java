package com.company.db;

import com.company.impl.AppInfo;
import com.company.impl.RawItem;
import com.company.impl.RawPost;
import com.company.impl.UserInfo;
import com.company.utils.ConnectionUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class DBController {

    private ConnectionUtil db = null;
    private Connection con = null;
    public DBController(AppInfo appInfo) {
        this.db = new ConnectionUtil();
        this.con = db.connect_to_db("jurnalik", "postgres", "");
    }

    public DBController() {

    }

    public List<UserInfo> getAllUsersVKBody() throws SQLException {
        return db.selectAllUserInfo(con, "vk_table");
    }

    public UserInfo getOneUsersVKBody(int user_id) throws SQLException {
        return db.selectOneUserInfo(con, "vk_table", user_id);
    }


    public void putPost(UserInfo user, RawPost post) {
        db.insert_row_into_raw_posts(con, "raw_posts", user, post);
    }


    public void putItem(int postId, RawItem item) {
        db.insert_row_into_items(con, "items", postId, item);
    }

    public void putGroup(int group_id, String group_name, String picture, String sourse){
        db.insert_row_into_groups_and_channels(group_id, group_name, picture, sourse, con);
    }
}
