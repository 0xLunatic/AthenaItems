package dreamer.athena.data;

import dreamer.athena.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;

public class OptionsManager {

    private final Main plugin;
    private FileConfiguration dataConfig = null;
    private File configFile = null;

    public OptionsManager(Main plugin) {
        this.plugin = plugin;
        saveDefaultConfig("item_options.yml");
    }

    public void reloadConfig(String options) {
        if (this.configFile != null) {
            this.configFile = new File(this.plugin.getDataFolder(), "options/"+options);

            this.dataConfig = YamlConfiguration.loadConfiguration(this.configFile);

            InputStream defaultStream = this.plugin.getResource("options/"+options);
            if (defaultStream != null) {
                YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultStream));
                this.dataConfig.setDefaults(defaultConfig);
            }
        }
    }

    public FileConfiguration getConfig(String options) {
        if (this.dataConfig == null)
            reloadConfig(options);
        return this.dataConfig;
    }

    public void saveConfig(String options) {
        if (this.dataConfig == null || this.configFile == null)
            return;
        try {
            this.getConfig(options).save(this.configFile);
        } catch (IOException e) {
            plugin.getLogger().log(Level.SEVERE, "Could not save config to" + this.configFile, e);
        }
    }

    public void saveDefaultConfig(String options) {
        if(this.configFile == null)
            this.configFile = new File(this.plugin.getDataFolder(), "options/"+options);
        if (!this.configFile.exists()) {
            this.plugin.saveResource("options/"+options, false);
        }
    }

}
