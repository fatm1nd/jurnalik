package com.company;

import com.company.impl.AppInfo;
import com.company.db.DBController;
import com.company.client.VKClient;
import com.company.impl.RawItem;
import com.company.impl.RawPost;
import com.company.impl.UserInfo;
import com.company.parser.JSONParser;
import com.company.utils.ConnectionUtil;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Runner {
    public void run() throws SQLException, InterruptedException, IOException {
        DBController dbController = new DBController(new AppInfo(51676262, "9037113790371137903711371c932395519903790371137f4be8cfaab6c1fb979771ce8"));
        VKClient vkClient = new VKClient();
        List<UserInfo> allVkUsers = dbController.getAllUsersVKBody();
        HashSet<Map.Entry<String, String>> allGroupsWithToken = vkClient.allGroups(allVkUsers);
        HashMap<String, String> allGroups = vkClient.allGroupsValueToName(allVkUsers); // мапка id -> shortName
        Map<String, List<RawPost>> groupIdToEntityMap = new HashMap<>(); // мапка shortName -> List<RawPost>
        int cnt = 0;
        for(Map.Entry<String, String> group : allGroupsWithToken){
            groupIdToEntityMap.putAll(vkClient.getGroupItems(group));
            if(cnt % 5 == 0) {
                TimeUnit.SECONDS.sleep(1);
            }
            ++cnt;
        }
        groupIdToEntityMap.remove(""); // remove self information
        addAllInforamtionToDB(allGroups, allVkUsers, groupIdToEntityMap);
        int a = 1;
        System.out.println("for find");
    }

    private void addAllInforamtionToDB(HashMap<String, String> allGroups, List<UserInfo> allVkUsers, Map<String, List<RawPost>> groupIdToEntityMap) throws IOException {
        ConnectionUtil db = new ConnectionUtil();
        Connection con = db.connect_to_db("Jornalik", "postgres", "Di~S8Qgs~%");
        DBController dbController = new DBController(new AppInfo(51676262, "9037113790371137903711371c932395519903790371137f4be8cfaab6c1fb979771ce8"));
        VKClient vkClient = new VKClient();
        for(UserInfo user : allVkUsers){

            StringBuilder response = vkClient.getVkGroup(user.getUserId(), user.getToken());
            HashSet<Map.Entry<String, String>> allUserGroupIds = new JSONParser().parseUserVKGroups(response.toString(), "");
            for(Map.Entry groupWithToken : allUserGroupIds){
                String groupId = groupWithToken.getKey().toString();
                String shortName = allGroups.get(groupId);
                List<RawPost> posts = groupIdToEntityMap.get(shortName);
                if(posts == null)
                    continue;
                for(RawPost post : posts){
                    dbController.putPost(user, post, con, db);
                    int post_id = post.getPost_id();
                    List<RawItem> items = post.getItems();
                    for(RawItem item : items){
                        dbController.putItem(post_id, item, con, db);
                    }

                }
                /// Далее имея group_id поличить shortName
                /// имея shortName получить List<RawPosts>
                /// и далее запихивать всё в БД

            }
        }
    }
}
