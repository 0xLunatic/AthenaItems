package dreamer.athena.commands;

import dreamer.athena.Main;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class AthenaItemsCommand implements CommandExecutor, Listener {
    private final Main plugin;

    public AthenaItemsCommand(Main plugin){
        this.plugin=plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, Command command, @NotNull String label, String[] args) {
        if(command.getName().equalsIgnoreCase("athenaitems") || command.getName().equalsIgnoreCase("ai")){
            if(!sender.hasPermission("athenaitems.use")){
                sender.sendMessage("§cYou don't have permission to do this!");
                return true;
            }
            if(args.length == 0 ){
                for (String msg : plugin.getConfig().getStringList("help.message")) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            msg));
                }
                return true;
            }
            else {
                if (args[0].equalsIgnoreCase("reload")) {
                    if (sender.hasPermission(Objects.requireNonNull(plugin.getConfig().getString("reload.permission")))) {
                        for (String msg : plugin.getConfig().getStringList("reload.message")) {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                    msg));
                        }
                        plugin.reloadConfig();
                        plugin.data.reloadConfig("items/AXE.yml");
                        plugin.data.reloadConfig("items/LONG_SWORD.yml");
                        plugin.data.reloadConfig("items/SWORD.yml");
                        plugin.data.reloadConfig("data.yml");
                        plugin.language.reloadConfig(plugin.getConfig().getString("language"));
                        plugin.options.reloadConfig("item_options.yml");
                    } else {
                        sender.sendMessage("§cYou don't have permission to do this!");
                    }
                }
                else if (args[0].equalsIgnoreCase("create")) {
                    if (args.length >= 3) {
                        if (plugin.options.getConfig("item_options.yml").getStringList("type").contains(args[1])) {
                            if (!args[2].isEmpty()) {
                                if (!plugin.data.getConfig("items/" + args[1] + ".yml").contains(args[2])) {
                                    sender.sendMessage(plugin.getConfig().getString("prefix") + plugin.language.getConfig(plugin.getConfig().getString("language")).getString("items-created") + args[2] + "!");
                                    plugin.data.getConfig("items/" + args[1] + ".yml").set(args[2] + ".base.material", "WOODEN_SWORD");
                                    plugin.data.getConfig("items/" + args[1] + ".yml").set(args[2] + ".base.name", "&f" + args[2]);
                                    plugin.data.saveConfig("items/" + args[1] + ".yml");
                                } else {
                                    sender.sendMessage(plugin.getConfig().getString("prefix") + plugin.language.getConfig(plugin.getConfig().getString("language")).getString("items-available"));
                                }
                            }
                        }
                    } else {
                        for (String msg : plugin.getConfig().getStringList("help.message")) {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                    msg));
                        }
                    }
                }
                else if (args[0].equalsIgnoreCase("delete")){
                    if (args.length == 3){
                        if (plugin.data.getConfig("items/"+args[1]+".yml").contains(args[2])){
                            plugin.data.getConfig("items/"+args[1]+".yml").set(args[2], null);
                            plugin.data.saveConfig("items/"+args[1]+".yml");
                            sender.sendMessage(plugin.getConfig().getString("prefix") + plugin.language.getConfig(plugin.getConfig().getString("language")).getString("items-deleted"));

                        }
                        else{
                            sender.sendMessage(plugin.getConfig().getString("prefix") + plugin.language.getConfig(plugin.getConfig().getString("language")).getString("items-unavailable"));
                        }
                    }
                }
                else if (args[0].equalsIgnoreCase("give")){
                    if (args.length == 3){
                        if (sender instanceof Player) {
                            Player p = (Player) sender;
                            if (plugin.data.getConfig("items/" + args[1] + ".yml").contains(args[2])) {
                                Material material = Material.valueOf(plugin.data.getConfig("items/"+args[1]+".yml").getString(args[2]+".base.material"));
                                ItemStack item = new ItemStack(material, 1);
                                ItemMeta itemmeta = item.getItemMeta();
                                assert itemmeta != null;
                                itemmeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(plugin.data.getConfig("items/" + args[1] + ".yml").getString(args[2] + ".base.name"))));
                                item.setItemMeta(itemmeta);
                                p.getInventory().addItem(item);
                            }
                        }
                        else{
                            sender.sendMessage(plugin.getConfig().getString("prefix") + plugin.language.getConfig(plugin.getConfig().getString("language")).getString("items-unavailable"));
                        }
                    }
                }
                else if (args[0].equalsIgnoreCase("browse")){
                    if (sender instanceof Player) {
                        Player p = (Player) sender;
                        plugin.gui.browseGUI(p);
                    }
                }
            }
        }
        return false;
    }
    public static boolean isInt(String str) {

        if(!str.isEmpty()) {
            try {
                @SuppressWarnings("unused")
                int x = Integer.parseInt(str);
                return true; //String is an Integer
            } catch (NumberFormatException e) {
                return false; //String is not an Integer
            }

        }else{
            System.out.print("Integer not detected, Creation failed!");
        }
        return false;
    }
}
