package com.company.parser;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.company.db.DBController;
import com.company.impl.RawItem;
import com.company.impl.RawPost;
import org.json.*;

public class JSONParser {
    public HashSet<Map.Entry<String, String>> parseUserVKGroups(String jsonResponse, String token) {
        HashSet<Map.Entry<String, String>> items = new HashSet<>();

        Pattern pattern = Pattern.compile("\"items\":\\[([^\\]]+)\\]");
        Matcher matcher = pattern.matcher(jsonResponse);


        if (matcher.find()) {
            String itemsString = matcher.group(1);
            String[] itemValues = itemsString.split(",");

            for (String item : itemValues) {
                items.add(new AbstractMap.SimpleEntry<>(item.trim(), token));
            }
        }

        return items;
    }

    public Map.Entry<String, String> parseShortVKName(String jsonResponse, String token) {
        Pattern pattern = Pattern.compile("\"screen_name\":\"(\\w+)\",");
        Matcher matcher = pattern.matcher(jsonResponse);

        if (matcher.find()) {
            String screenName = matcher.group(1);
            return new AbstractMap.SimpleEntry<>(screenName, token);
        } else {
            return new AbstractMap.SimpleEntry<>("", token);
        }
    }

    public List<RawPost> parseGroupPosts(String jsonResponse, String group_id) {
        List<RawPost> posts = new ArrayList<>();
        try {
            JSONArray jsonObjects = (new JSONObject(new JSONObject(jsonResponse).get("response").toString())).getJSONArray("items");
            for (Object postObj : jsonObjects) {
                JSONObject post = new JSONObject(postObj.toString());
                int post_id = post.getInt("id");
                String text = post.getString("text");
                JSONArray photos = post.getJSONArray("attachments");
                List<RawItem> rawItems = new ArrayList<>();
                for (Object photoItemObj : photos) {
                    try {
                        JSONArray photoItem = (new JSONObject(photoItemObj.toString())).getJSONObject("photo").getJSONArray("sizes");
                        JSONObject sizeObj = photoItem.optJSONObject(photoItem.length() - 1);
                        String url = sizeObj.getString("url");
                        rawItems.add(new RawItem(url, "url"));
                    } catch (JSONException e) {
                    }
                }
                rawItems.add(new RawItem(text, "text"));
                posts.add(new RawPost(rawItems, post_id, Integer.parseInt(group_id)));
            }
            return posts;
        } catch (JSONException e) {
            return posts;
        }
    }

    public HashMap<String, String> parseShortVKNameId(String jsonResponse, String id, DBController dbController) {
        HashMap<String, String> map = new HashMap<>();
        Pattern patternId = Pattern.compile("\"id\":(\\w+),");
        Matcher matcherId = patternId.matcher(jsonResponse);
        Pattern patternName = Pattern.compile("\"name\":\"(\\w+)\",");
        Matcher matcherName = patternName.matcher(jsonResponse);
        Pattern patternPhoto = Pattern.compile("\"photo_200\":\"(.+)\"}");
        Matcher matcherPhoto = patternPhoto.matcher(jsonResponse);
        Pattern pattern = Pattern.compile("\"screen_name\":\"(\\w+)\",");
        Matcher matcher = pattern.matcher(jsonResponse);

        if (matcher.find()) {
            String screenName = matcher.group(1);
            String name = "";
            String photo = "";
            int groupId = 0;

            if (matcherName.find())
                name = matcherName.group(1);
            if (matcherPhoto.find())
                photo = matcherPhoto.group(1);
            if (matcherId.find())
                groupId = Integer.parseInt(matcherId.group(1));
            if(name.equals(""))
                name = screenName;
            dbController.putGroup(groupId, name, photo, "vk");

            map.put(id, screenName);
            return map;
        }
        return map;
    }
}
