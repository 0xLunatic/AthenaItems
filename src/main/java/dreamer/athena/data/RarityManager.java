package dreamer.athena.data;

import dreamer.athena.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;

public class RarityManager {

    private final Main plugin;
    private FileConfiguration dataConfig = null;
    private File configFile = null;

    public RarityManager(Main plugin) {
        this.plugin = plugin;
        saveDefaultConfig();
    }

    public void reloadConfig() {
        if (this.configFile != null) {
            this.configFile = new File(this.plugin.getDataFolder(), "item-rarity.yml");
            this.dataConfig = YamlConfiguration.loadConfiguration(this.configFile);

            InputStream defaultStream = this.plugin.getResource("item-rarity.yml");
            if (defaultStream != null) {
                YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultStream));
                this.dataConfig.setDefaults(defaultConfig);
            }
        }
        if (this.configFile == null) {
            this.configFile = new File(this.plugin.getDataFolder(), "item-rarity.yml");
            this.dataConfig = YamlConfiguration.loadConfiguration(this.configFile);

            InputStream defaultStream = this.plugin.getResource("item-rarity.yml");
            if (defaultStream != null) {
                YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultStream));
                this.dataConfig.setDefaults(defaultConfig);
            }
        }
    }

    public FileConfiguration getConfig() {
        if (this.dataConfig == null)
            reloadConfig();
        return this.dataConfig;
    }

    public void saveConfig() {
        if (this.dataConfig == null || this.configFile == null)
            return;
        try {
            this.getConfig().save(this.configFile);
        } catch (IOException e) {
            plugin.getLogger().log(Level.SEVERE, "Could not save config to" + this.configFile, e);
        }
    }

    public void saveDefaultConfig() {
        if(this.configFile == null)
            this.configFile = new File(this.plugin.getDataFolder(), "item-rarity.yml");
        if (!this.configFile.exists()) {
            this.plugin.saveResource("item-rarity.yml", false);
        }
    }
    public void saveResource(){
        this.configFile = new File(this.plugin.getDataFolder(), "item-rarity.yml");
        if (!this.configFile.exists()) {
            this.plugin.saveResource("item-rarity.yml", false);
        }
    }
}
