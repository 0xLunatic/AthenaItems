package dreamer.athena;

import dreamer.athena.commands.AthenaItemsCommand;
import dreamer.athena.commands.CommandCompleter;
import dreamer.athena.data.*;
import dreamer.athena.listener.ItemUse;
import dreamer.athena.addons.Mana;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Objects;

public final class Main extends JavaPlugin implements Listener {

    public DataManager data;
    public LanguageManager language;
    public OptionsManager options;
    public GUIManager gui;
    public RarityManager rarity;

    FileConfiguration config = getConfig();

    @Override
    public void onEnable() {
        // Plugin startup logic
        config.options().copyDefaults(true);
        gui = new GUIManager(this);
        data = new DataManager(this);
        language = new LanguageManager(this);
        options = new OptionsManager(this);
        rarity = new RarityManager(this);

        System.out.println("§aAthenaItems Enabled!");

        File configFile = new File(getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            saveResource("config.yml", true);
        }else{
            saveDefaultConfig();
        }

        Bukkit.getPluginManager().registerEvents(new ItemUse(this), this);
        Bukkit.getPluginManager().registerEvents(new GUIManager(this), this);
        if (Objects.requireNonNull(getConfig().getString("aurelium-api")).equalsIgnoreCase("true")){
            if(Bukkit.getPluginManager().getPlugin("AureliumSkills") != null) {
                Bukkit.getPluginManager().registerEvents(new Mana(this), this);
                new Mana(this).runTaskTimer(this, 20, 20);
            }else {
                System.out.print("AureliumSkills plugin not found!");
            }
        }

        Objects.requireNonNull(getCommand("ai")).setTabCompleter(new CommandCompleter(this));
        Objects.requireNonNull(getCommand("athenaitems")).setTabCompleter(new CommandCompleter(this));
        Objects.requireNonNull(getCommand("athenaitems")).setExecutor(new AthenaItemsCommand(this));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
    public FileConfiguration getConfigFile(){
        return config;
    }

}
