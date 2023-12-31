package com.company.client;

import com.company.db.DBController;
import com.company.impl.RawPost;
import com.company.impl.UserInfo;
import com.company.parser.JSONParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class VKClient {

    public HashSet<Map.Entry<String, String>> allGroups(List<UserInfo> allVkUsers) {
        HashSet<Map.Entry<String, String>> allGroups = new HashSet<>();
        int cnt = 0;
        for (UserInfo vkUser : allVkUsers) {
            try {
                if (cnt % 5 == 0) {
                    TimeUnit.SECONDS.sleep(1);
                }
                ++cnt;
                StringBuilder response = getVkGroup(vkUser.getUserId(), vkUser.getToken());

                HashSet<Map.Entry<String, String>> allUserGroupIds = new JSONParser().parseUserVKGroups(response.toString(), vkUser.getToken());
                HashSet<Map.Entry<String, String>> allGroupShortNames = shortNamesWithTokens(allUserGroupIds);
                allGroups.addAll(allGroupShortNames);


            } catch (IOException e) {
                System.out.println("some_broken get all group");
                e.printStackTrace();
            } catch (InterruptedException e) {
                System.out.println("some_broken get all group");
                throw new RuntimeException(e);
            }
        }
        return allGroups;
    }

    public HashSet<Map.Entry<String, String>> allGroups(UserInfo vkUser) {
        HashSet<Map.Entry<String, String>> allGroups = new HashSet<>();
            try {
                StringBuilder response = getVkGroup(vkUser.getUserId(), vkUser.getToken());

                HashSet<Map.Entry<String, String>> allUserGroupIds = new JSONParser().parseUserVKGroups(response.toString(), vkUser.getToken());
                HashSet<Map.Entry<String, String>> allGroupShortNames = shortNamesWithTokens(allUserGroupIds);
                allGroups.addAll(allGroupShortNames);


            } catch (IOException e) {
                e.printStackTrace();
            }
        return allGroups;
    }

    public StringBuilder getVkGroup(int user_id, String user_token) throws IOException {
        String urlStr = "https://api.vk.com/method/groups.get?user_ids=" + user_id + "&fields=bdate&v=5.131&access_token=" + user_token;
        URL url = new URL(urlStr);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line;
        StringBuilder response = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();
        connection.disconnect();
        return response;
    }

    public HashMap<String, String> allGroupsValueToName(List<UserInfo> allVkUsers, DBController dbController) {
        HashMap<String, String> allGroups = new HashMap<>();
        int cnt = 0;
        for (UserInfo vkUser : allVkUsers) {
            try {
                if (cnt % 5 == 0) {
                    TimeUnit.SECONDS.sleep(1);
                }
                ++cnt;
                String urlStr = "https://api.vk.com/method/groups.get?user_ids=" + vkUser.getVkId() + "&fields=bdate&v=5.131&access_token=" + vkUser.getToken();
                URL url = new URL(urlStr);

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                StringBuilder response = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
                connection.disconnect();

                HashSet<Map.Entry<String, String>> allUserGroupIds = new JSONParser().parseUserVKGroups(response.toString(), vkUser.getToken());
                HashMap<String, String> allGroupShortNames = IdToShortNameMap(allUserGroupIds, dbController);
                allGroups.putAll(allGroupShortNames);


            } catch (IOException e) {
                System.out.println("some_broken get all group");
                e.printStackTrace();
            } catch (InterruptedException e) {
                System.out.println("some_broken get all group");
                throw new RuntimeException(e);
            }
        }
        return allGroups;
    }

    public HashMap<String, String> allGroupsValueToName(UserInfo vkUser, DBController dbController) {
        HashMap<String, String> allGroups = new HashMap<>();
        int cnt = 0;
            try {
                if (cnt % 5 == 0) {
                    TimeUnit.SECONDS.sleep(1);
                }
                ++cnt;
                String urlStr = "https://api.vk.com/method/groups.get?user_ids=" + vkUser.getVkId() + "&fields=bdate&v=5.131&access_token=" + vkUser.getToken();
                URL url = new URL(urlStr);

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                StringBuilder response = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
                connection.disconnect();

                HashSet<Map.Entry<String, String>> allUserGroupIds = new JSONParser().parseUserVKGroups(response.toString(), vkUser.getToken());
                HashMap<String, String> allGroupShortNames = IdToShortNameMap(allUserGroupIds, dbController);
                allGroups.putAll(allGroupShortNames);


            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        return allGroups;
    }

    private HashMap<String, String> IdToShortNameMap(HashSet<Map.Entry<String, String>> ids, DBController dbController) {
        HashMap<String, String> allNames = new HashMap<>();
        int cnt = 0;
        for (Map.Entry<String, String> id : ids) {
            try {
                if (cnt % 5 == 0) {
                    TimeUnit.SECONDS.sleep(1);
                }
                ++cnt;
                String urlStr = "https://api.vk.com/method/groups.getById?group_id=" + id.getKey() + "&v=5.131&access_token=" + id.getValue();
                URL url = new URL(urlStr);

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                StringBuilder response = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
                connection.disconnect();

                allNames.putAll(new JSONParser().parseShortVKNameId(response.toString(), id.getKey(), dbController));


            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return allNames;
    }


    public HashSet<Map.Entry<String, String>> shortNamesWithTokens(HashSet<Map.Entry<String, String>> ids) {
        HashSet<Map.Entry<String, String>> allNames = new HashSet<>();
        int cnt = 0;
        for (Map.Entry<String, String> id : ids) {
            try {
                if (cnt % 5 == 0) {
                    TimeUnit.SECONDS.sleep(1);
                }
                ++cnt;
                String urlStr = "https://api.vk.com/method/groups.getById?group_id=" + id.getKey() + "&v=5.131&access_token=" + id.getValue();
                URL url = new URL(urlStr);

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                StringBuilder response = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
                connection.disconnect();

                allNames.add(new JSONParser().parseShortVKName(response.toString(), id.getValue()));


            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return allNames;
    }

    public Map<String, List<RawPost>> getGroupItems(Map.Entry<String, String> groupWithToken, String id) {
        Map<String, List<RawPost>> groupToPostsItemsMap = new HashMap<>();
        try {
            TimeUnit.SECONDS.sleep(1);
            String urlStr = "https://api.vk.com/method/wall.get?domain=" + groupWithToken.getKey() + "&fields=bdate&v=5.131&access_token=" + groupWithToken.getValue();
            URL url = new URL(urlStr);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            connection.disconnect();
            if (id == null) return groupToPostsItemsMap;
            List<RawPost> rawPosts = new JSONParser().parseGroupPosts(response.toString(), id);
            groupToPostsItemsMap.put(groupWithToken.getKey(), rawPosts);


        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return groupToPostsItemsMap;
    }
}
