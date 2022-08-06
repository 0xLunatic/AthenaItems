package dreamer.athena.data;

import dreamer.athena.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;

public class LanguageManager {

    private final Main plugin;
    private FileConfiguration dataConfig = null;
    private File configFile = null;

    public LanguageManager(Main plugin) {
        this.plugin = plugin;
        saveDefaultConfig(plugin.getConfig().getString("language"));
    }

    public void reloadConfig(String lang) {
        if (this.configFile != null) {
            this.configFile = new File(this.plugin.getDataFolder(), "language/"+lang);

            this.dataConfig = YamlConfiguration.loadConfiguration(this.configFile);

            InputStream defaultStream = this.plugin.getResource("language/"+lang);
            if (defaultStream != null) {
                YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultStream));
                this.dataConfig.setDefaults(defaultConfig);
            }
        }
    }

    public FileConfiguration getConfig(String lang) {
        if (this.dataConfig == null)
            reloadConfig(lang);
        return this.dataConfig;
    }

    public void saveConfig(String lang) {
        if (this.dataConfig == null || this.configFile == null)
            return;
        try {
            this.getConfig(lang).save(this.configFile);
        } catch (IOException e) {
            plugin.getLogger().log(Level.SEVERE, "Could not save config to" + this.configFile, e);
        }
    }

    public void saveDefaultConfig(String lang) {
        if(this.configFile == null)
            this.configFile = new File(this.plugin.getDataFolder(), "language/"+lang);
        if (!this.configFile.exists()) {
            this.plugin.saveResource("language/"+lang, false);
        }
    }
}
