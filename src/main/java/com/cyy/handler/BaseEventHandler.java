package com.cyy.handler;

import com.cyy.chatGpt.ChatGpt;
import com.cyy.chatGpt.ChatGptFactory;
import com.cyy.chatGpt.MessageContext;
import com.cyy.util.BotConfig;
import kotlin.coroutines.CoroutineContext;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.*;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.*;
import net.mamoe.mirai.message.data.*;

import net.mamoe.mirai.utils.ExternalResource;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;


/**
 * @author cyy
 * @date 2022/2/16 12:18
 * @description 事件分发处理，对mirai事件进行监听
 * 机器人的功能实现依靠这些基础事件
 */
public class BaseEventHandler extends SimpleListenerHost {
    BotConfig botConfig = BotConfig.BotConfigHolder.getInstance();
    private final Long masterId = botConfig.getMasterId();
    private final Long botId = botConfig.getBotId();
    /**
     * 未处理的异常，会统一在此处理，将异常信息发送到机器主人手中(需要配置文件配置个人qq号)
     * @param context mirai上下文
     * @param exception 异常
     */
    @Override
    public void handleException(@NotNull CoroutineContext context, @NotNull Throwable exception) {

        Throwable cause = exception.getCause().getCause();  //得到真实异常
        cause.printStackTrace();    //控制台打印异常
        if(masterId == null || botId == null){
            return;
        }
        Friend master = Bot.getInstanceOrNull(botId).getFriend(masterId);  //从机器人好友中获取到master
        Objects.requireNonNull(master).sendMessage(cause+":"+cause.getMessage());   //发送异常信息
    }

    /**
     * mirai事件：接收到群信息时触发
     */
    @EventHandler
    public void onGroupMessageEvent(GroupMessageEvent groupMessageEvent) {
        MessageChain message = groupMessageEvent.getMessage();  //获取信息链

        Group group = groupMessageEvent.getGroup(); //获取触发群消息事件的群
        String key = "group:"+group.getId();    //制作key用于存储或者获取机器人
        if(message.contains(new At(botId)) && botConfig.getGrantGroups().contains(group.getId())){    //触发条件是at机器人，并且允许该群使用

            //******处理信息
            MessageChainBuilder messageChainBuilder = new MessageChainBuilder();
            message.forEach(item->{
                if(item instanceof PlainText){
                    messageChainBuilder.add(item);
                }
            });
            String question = messageChainBuilder.build().contentToString().strip(); //将纯文本信息转为string
            //*******处理结束

            if("重置".equals(question)){
                ChatGptFactory.addChatGpt(key,new ChatGpt(
                        new MessageContext(botConfig.getMessageContextMaxSize()),
                        botConfig.getApiKey()));
                group.sendMessage("ok");
            }
            else{
                ChatGpt chatGpt = ChatGptFactory.getChatGpt(key);
                if(chatGpt == null){    //第一次聊天
                    chatGpt = new ChatGpt(
                            new MessageContext(botConfig.getMessageContextMaxSize()),
                            botConfig.getApiKey());
                    ChatGptFactory.addChatGpt(key,chatGpt);
                }

                String result = chatGpt.doChat(question);
                group.sendMessage(result);
            }
        }
    }



    /**
     * mirai事件：邀请机器人进群时触发
     *      功能：
     *          用于自动同意加群请求
     * @param botInvitedJoinGroupRequestEvent 邀请机器人进群对象
     */
    @EventHandler
    public void onJoinGroup(BotInvitedJoinGroupRequestEvent botInvitedJoinGroupRequestEvent){
        if(botConfig.isAutoJoinGroup()){
            botInvitedJoinGroupRequestEvent.accept();   //同意加群请求
        }
    }

    /**
     * mirai事件：有新朋友加机器人为好友时触发
     *      功能:
     *          用于自动同意加好友请求
     * @param newFriendRequestEvent 新朋友
     */
    @EventHandler
    public void onAddFriend(NewFriendRequestEvent newFriendRequestEvent){
        if(botConfig.isAutoJoinFriend()) {
            newFriendRequestEvent.accept(); //同意加好友请求
        }
    }
}
