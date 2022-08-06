package dreamer.athena.commands;

import dreamer.athena.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CommandCompleter implements TabCompleter {
    private final Main plugin;


    public CommandCompleter(Main plugin){
        this.plugin=plugin;

    }
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> type = new ArrayList<>(plugin.options.getConfig("item_options.yml").getStringList("type"));
        if (args.length == 1) {
            return StringUtil.copyPartialMatches(args[0], Arrays.asList("reload", "create", "delete", "give", "help", "browse"), new ArrayList<>());
        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("create")) {
                if (sender.hasPermission("athenaitems.create")) {
                    return StringUtil.copyPartialMatches(args[1], type, new ArrayList<>());
                }
            }
        }
        if (args[0].equalsIgnoreCase("delete")) {
            if (args.length == 2) {
                return StringUtil.copyPartialMatches(args[1], type, new ArrayList<>());
            }
            else if (args.length == 3) {
                List<String> list = new ArrayList<>(plugin.data.getConfig("items/"+args[1]+".yml").getKeys(false));
                return StringUtil.copyPartialMatches(args[2], list, new ArrayList<>());
            }
        }
        if (args[0].equalsIgnoreCase("give")){
            if (args.length == 2){
                return StringUtil.copyPartialMatches(args[1], type, new ArrayList<>());
            }
            else if (args.length == 3){
                List<String> list = new ArrayList<>(plugin.data.getConfig("items/"+args[1]+".yml").getKeys(false));
                return StringUtil.copyPartialMatches(args[2], list, new ArrayList<>());
            }
        }
        else {
            return Collections.emptyList();
        }
        return Collections.emptyList();
    }
}
