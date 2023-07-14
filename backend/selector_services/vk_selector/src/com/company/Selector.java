package com.company;

import com.company.client.VKClient;
import com.company.db.DBController;
import com.company.impl.AppInfo;
import com.company.impl.RawItem;
import com.company.impl.RawPost;
import com.company.impl.UserInfo;
import com.company.parser.JSONParser;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Selector {

    private static DBController dbController = new DBController();
    private static VKClient vkClient = new VKClient();
    Selector(){
        dbController = new DBController(new AppInfo(51676262, "9037113790371137903711371c932395519903790371137f4be8cfaab6c1fb979771ce8"));
        vkClient = new VKClient();
    }
    static HashMap<String, String> shortNameToId(HashMap<String, String> shortToId) {
        HashMap<String, String> map = new HashMap<>();
        for(Map.Entry<String, String> mp : shortToId.entrySet()){
            map.put(mp.getValue(), mp.getKey());
        }
        return map;
    }


    static void selectOne(int user_id) throws SQLException, InterruptedException, IOException {
        System.out.println("start select One_VK_Selector");

        UserInfo vkUser = dbController.getOneUsersVKBody(user_id); // возвращает лист пользователей , как User(user_id, vk_id, time= 0,  token)
        HashSet<Map.Entry<String, String>> allGroupsWithToken = vkClient.allGroups(vkUser);  // мапка short -> token
        HashMap<String, String> allGroups = vkClient.allGroupsValueToName(vkUser, dbController); // мапка id -> shortName
        HashMap<String, String> shortNameToId = shortNameToId(allGroups);  // мапка short -> id
        Map<String, List<RawPost>> groupIdToEntityMap = new HashMap<>(); // мапка shortName -> List<RawPost>
        int cnt = 0;
        for (Map.Entry<String, String> group : allGroupsWithToken) {
            groupIdToEntityMap.putAll(vkClient.getGroupItems(group, shortNameToId.get(group.getKey())));
            if (cnt % 5 == 0) {
                TimeUnit.SECONDS.sleep(1);
            }
            ++cnt;
        }
        groupIdToEntityMap.remove(""); // remove self information
        addAllInforamtionToDB(allGroups, vkUser, groupIdToEntityMap, dbController);
        System.out.println("end of execution VK Selector");
    }

    static void selectAll()throws SQLException, InterruptedException, IOException {
        System.out.println("start select All_VK_Selector");
        List<UserInfo> allVkUsers = dbController.getAllUsersVKBody(); // возвращает лист пользователей , как User(user_id, vk_id, time= 0,  token)
        HashSet<Map.Entry<String, String>> allGroupsWithToken = vkClient.allGroups(allVkUsers);  // мапка short -> token
        HashMap<String, String> allGroups = vkClient.allGroupsValueToName(allVkUsers, dbController); // мапка id -> shortName
        HashMap<String, String> shortNameToId = shortNameToId(allGroups);  // мапка short -> id
        Map<String, List<RawPost>> groupIdToEntityMap = new HashMap<>(); // мапка shortName -> List<RawPost>
        int cnt = 0;
        for (Map.Entry<String, String> group : allGroupsWithToken) {
            groupIdToEntityMap.putAll(vkClient.getGroupItems(group, shortNameToId.get(group.getKey())));
            if (cnt % 5 == 0) {
                TimeUnit.SECONDS.sleep(1);
            }
            ++cnt;
        }
        groupIdToEntityMap.remove(""); // remove self information
        addAllInforamtionToDB(allGroups, allVkUsers, groupIdToEntityMap, dbController);
        System.out.println("end of execution VK Selector");
    }

    private static void addAllInforamtionToDB(HashMap<String, String> allGroups, List<UserInfo> allVkUsers, Map<String, List<RawPost>> groupIdToEntityMap, DBController dbController) throws IOException {
        VKClient vkClient = new VKClient();
        for (UserInfo user : allVkUsers) {

            StringBuilder response = vkClient.getVkGroup(user.getUserId(), user.getToken());
            HashSet<Map.Entry<String, String>> allUserGroupIds = new JSONParser().parseUserVKGroups(response.toString(), "");
            for (Map.Entry groupWithToken : allUserGroupIds) {
                String groupId = groupWithToken.getKey().toString();
                String shortName = allGroups.get(groupId);
                List<RawPost> posts = groupIdToEntityMap.get(shortName);
                if (posts == null)
                    continue;
                for (RawPost post : posts) {
                    dbController.putPost(user, post);
                    int post_id = post.getPost_id();
                    List<RawItem> items = post.getItems();
                    for (RawItem item : items) {
                        dbController.putItem(post_id, item);
                    }
                }
            }
        }
}

    private static void addAllInforamtionToDB(HashMap<String, String> allGroups, UserInfo vkUser, Map<String, List<RawPost>> groupIdToEntityMap, DBController dbController) throws IOException {
        VKClient vkClient = new VKClient();

            StringBuilder response = vkClient.getVkGroup(vkUser.getUserId(), vkUser.getToken());
            HashSet<Map.Entry<String, String>> allUserGroupIds = new JSONParser().parseUserVKGroups(response.toString(), "");
            for (Map.Entry groupWithToken : allUserGroupIds) {
                String groupId = groupWithToken.getKey().toString();
                String shortName = allGroups.get(groupId);
                List<RawPost> posts = groupIdToEntityMap.get(shortName);
                if (posts == null)
                    continue;
                for (RawPost post : posts) {
                    dbController.putPost(vkUser, post);
                    int post_id = post.getPost_id();
                    List<RawItem> items = post.getItems();
                    for (RawItem item : items) {
                        dbController.putItem(post_id, item);
                    }
            }
        }
    }
}
