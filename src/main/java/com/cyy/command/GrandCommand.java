package com.cyy.command;

import com.cyy.ChatGptQqPlugin;
import com.cyy.util.BotConfig;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.console.command.CommandOwner;
import net.mamoe.mirai.console.command.CommandSender;
import net.mamoe.mirai.console.command.java.JSimpleCommand;
import net.mamoe.mirai.console.plugin.Plugin;
import net.mamoe.mirai.contact.Friend;
import net.mamoe.mirai.contact.Group;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

/**
 * @author CYY
 * @date 2023年02月15日 下午10:57
 * @description
 */
public final class GrandCommand extends JSimpleCommand {
    public static final GrandCommand INSTANCE = new GrandCommand();
    private final BotConfig botConfig = BotConfig.BotConfigHolder.getInstance();
    public GrandCommand() {
        super(ChatGptQqPlugin.INSTANCE, "grant");
        setDescription("这是一个授权群或者好友使用chatGpt的指令");
        setPermission(ChatGptQqPlugin.INSTANCE.getParentPermission());
        setPrefixOptional(true);
    }
    @Handler
    public void onCommand(CommandSender sender, String target, String id) {
        List<Long> grantGroups = botConfig.getGrantGroups();
        List<Long> grantFriends = botConfig.getGrantFriends();
        Bot bot = Bot.getInstanceOrNull(botConfig.getBotId());
        if(bot == null) {
            sender.sendMessage("请在mirai目录下创建文件夹chatGptBot再创建配置文件config.properties,在配置文件中配置botId=您的机器人qq号。");
            return;
        }
        if("group".equals(target)){
            Group group = bot.getGroup(Long.parseLong(id));
            if(group == null){
                sender.sendMessage("您的机器人没有加入该群。");
                return;
            }
            grantGroups.add(Long.valueOf(id));
        } else if ("friend".equals(target)) {
            Friend friend = bot.getFriend(Long.parseLong(id));
            if(friend == null){
                sender.sendMessage("您的机器人没有加该好友。");
                return;
            }
            grantFriends.add(Long.valueOf(id));
        }
        //todo 持久化
    }
}
