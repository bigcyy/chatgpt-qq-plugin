package com.cyy.util;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * 机器人全局配置
 */
public class BotConfig {
    private Long masterId;
    private Long botId;
    private int messageContextMaxSize;
    private String apiKey;
    private boolean autoJoinGroup;
    private boolean autoJoinFriend;

    private List<Long> grantFriends;
    private List<Long> grantGroups;

    private BotConfig() {
        Properties properties = PropertiesUtil.getPropertiesByPath("chatGptBot" + File.separator + "config.properties");
        if(properties != null) {
            masterId = properties.getProperty("masterId") == null ? null : Long.valueOf(properties.getProperty("masterId"));
            botId = properties.getProperty("botId") == null ? null : Long.valueOf(properties.getProperty("botId"));

            messageContextMaxSize = properties.getProperty("messageContextMaxSize") == null ? 7 : Integer.parseInt(properties.getProperty("messageContextMaxSize")) + 1;
            apiKey = properties.getProperty("apiKey") == null ? "" : "Bearer " + properties.getProperty("apiKey");

            autoJoinGroup = properties.getProperty("autoJoinGroup") != null && Boolean.parseBoolean(properties.getProperty("autoJoinGroup"));
            autoJoinFriend = properties.getProperty("autoJoinFriend") != null && Boolean.parseBoolean(properties.getProperty("autoJoinFriend"));
            if(properties.getProperty("grantGroups") != null){
                String grantGroupString = properties.getProperty("grantGroups");
                String[] grantGroupArray = grantGroupString.split(",");
                grantGroups = Arrays.stream(grantGroupArray)
                        .map(Long::valueOf)
                        .collect(Collectors.toList());
            }else {
                grantGroups = new ArrayList<>();
            }
            if(properties.getProperty("grantFriends") != null){
                String grantGroupString = properties.getProperty("grantFriends");
                String[] grantGroupArray = grantGroupString.split(",");
                grantFriends = Arrays.stream(grantGroupArray)
                        .map(Long::valueOf)
                        .collect(Collectors.toList());
            }else {
                grantFriends = new ArrayList<>();
            }
        }
        else {
            //没有配置文件采取默认策略
            messageContextMaxSize = 7;
            autoJoinGroup = false;
            autoJoinFriend = false;
            grantFriends = new ArrayList<>();
            grantGroups = new ArrayList<>();
        }
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public Long getMasterId() {
        return masterId;
    }

    public Long getBotId() {
        return botId;
    }

    public int getMessageContextMaxSize() {
        return messageContextMaxSize;
    }

    public boolean isAutoJoinGroup() {
        return autoJoinGroup;
    }

    public void setAutoJoinGroup(boolean autoJoinGroup) {
        this.autoJoinGroup = autoJoinGroup;
    }

    public List<Long> getGrantGroups() {
        return grantGroups;
    }

    public void setGrantGroups(List<Long> grantGroups) {
        this.grantGroups = grantGroups;
    }

    public boolean isAutoJoinFriend() {
        return autoJoinFriend;
    }

    public void setAutoJoinFriend(boolean autoJoinFriend) {
        this.autoJoinFriend = autoJoinFriend;
    }

    public List<Long> getGrantFriends() {
        return grantFriends;
    }

    public void setGrantFriends(List<Long> grantFriends) {
        this.grantFriends = grantFriends;
    }

    public void setMasterId(Long masterId) {
        this.masterId = masterId;
    }

    public void setBotId(Long botId) {
        this.botId = botId;
    }

    public void setMessageContextMaxSize(int messageContextMaxSize) {
        this.messageContextMaxSize = messageContextMaxSize;
    }


    public static class BotConfigHolder{
        private static BotConfig instance = new BotConfig();
        public static BotConfig getInstance() {
            return instance;
        }
    }
}
