package cc.dreamcode.teleport;

import cc.dreamcode.teleport.configuration.ConfigurationController;
import cc.dreamcode.teleport.configuration.impl.MessagesConfig;
import cc.dreamcode.teleport.configuration.impl.SettingsData;
import cc.dreamcode.teleport.helper.ChatHelper;
import cc.dreamcode.teleport.service.TeleportRequestService;
import cc.dreamcode.teleport.service.TeleportService;
import lombok.Getter;

@Getter
public class BukkitPluginAdapter {

    public ConfigurationController configurationController;

    //Sections with configuration
    public SettingsData settingsData;
    public MessagesConfig messagesConfig;

    public TeleportService teleportService;
    public TeleportRequestService teleportRequestService;

    public ChatHelper chatHelper;

    public BukkitPluginAdapter() {
        this.configurationController = BukkitPlugin.getInstance().getConfigurationController();

        this.settingsData = this.configurationController.getSettingsData();
        this.messagesConfig = this.configurationController.getMessagesConfig();

        this.teleportService = BukkitPlugin.getInstance().getTeleportService();
        this.teleportRequestService = BukkitPlugin.getInstance().getTeleportRequestService();

        this.chatHelper = new ChatHelper();
    }
}
