package com.cyy;

import com.cyy.command.GrandCommand;
import com.cyy.handler.BaseEventHandler;
import net.mamoe.mirai.console.command.CommandManager;
import net.mamoe.mirai.console.plugin.jvm.JavaPlugin;
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescriptionBuilder;
import net.mamoe.mirai.event.GlobalEventChannel;

public final class ChatGptQqPlugin extends JavaPlugin {
    public static final ChatGptQqPlugin INSTANCE = new ChatGptQqPlugin();

    private ChatGptQqPlugin() {
        super(new JvmPluginDescriptionBuilder("com.cyy.chatgpt", "0.1.3")
                .name("chatgpt-qq-plugin")
                .author("CYY")
                .build());
    }

    @Override
    public void onEnable() {
        getLogger().info("插件加载成功!");
        //注册事件处理器
        GlobalEventChannel.INSTANCE.registerListenerHost(new BaseEventHandler());

        CommandManager.INSTANCE.registerCommand(GrandCommand.INSTANCE, true);
    }
}