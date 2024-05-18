package cc.dreamcode.teleport.configuration;

import cc.dreamcode.teleport.BukkitPlugin;
import cc.dreamcode.teleport.configuration.impl.MessagesConfig;
import cc.dreamcode.teleport.configuration.impl.SettingsData;
import eu.okaeri.configs.ConfigManager;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.yaml.bukkit.YamlBukkitConfigurer;
import eu.okaeri.configs.yaml.bukkit.serdes.SerdesBukkit;
import lombok.Getter;

@Getter
public class ConfigurationController {

    private final SettingsData settingsData;

    private final MessagesConfig messagesConfig;

    public ConfigurationController(BukkitPlugin plugin) {
        this.settingsData = registerConfig(plugin, SettingsData.class, "settings.yml");
        this.messagesConfig = registerConfig(plugin, MessagesConfig.class, "messages.yml");
    }

    private <T extends OkaeriConfig> T registerConfig(BukkitPlugin plugin, Class<T> configClass, String fileName) {
        return ConfigManager.create(configClass, config -> {
            config.withConfigurer(new YamlBukkitConfigurer(), new SerdesBukkit());
            config.withBindFile(plugin.getDataFolder() + "/" + fileName);
            config.saveDefaults();
            config.load(true);
        });
    }
}
