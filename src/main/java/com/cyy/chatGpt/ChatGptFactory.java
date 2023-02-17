package com.cyy.chatGpt;

import java.util.HashMap;
import java.util.Map;

/**
 * @author CYY
 * @date 2023年02月11日 下午10:10
 * @description chatGpt工厂
 */
public class ChatGptFactory {
    private static final Map<String,ChatGpt> chatGptMap = new HashMap<>();

    public static ChatGpt getChatGpt(String key){
        //还没有chatGpt或者已经过时返回null
        if(chatGptMap.get(key) == null || chatGptMap.get(key).isValid()) return null;
        return chatGptMap.get(key);
    }
    public static void addChatGpt(String key,ChatGpt chatGpt){
        chatGptMap.put(key,chatGpt);
    }
}
