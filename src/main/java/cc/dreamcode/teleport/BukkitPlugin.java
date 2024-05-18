package cc.dreamcode.teleport;

import cc.dreamcode.teleport.command.TeleportCommand;
import cc.dreamcode.teleport.command.impl.TeleportAcceptCommand;
import cc.dreamcode.teleport.command.impl.TeleportDeclineCommand;
import cc.dreamcode.teleport.configuration.ConfigurationController;
import cc.dreamcode.teleport.listener.block.BlockBreakListener;
import cc.dreamcode.teleport.listener.block.BlockPlaceListener;
import cc.dreamcode.teleport.listener.player.PlayerItemConsumeListener;
import cc.dreamcode.teleport.schedule.TeleportTask;
import cc.dreamcode.teleport.service.TeleportRequestService;
import cc.dreamcode.teleport.service.TeleportService;
import lombok.Getter;

@Getter
public final class BukkitPlugin extends BukkitPluginDelegator {

    private ConfigurationController configurationController;

    private TeleportService teleportService;
    private TeleportRequestService teleportRequestService;

    @Override
    public void load() {
        this.configurationController = new ConfigurationController(this);

        this.teleportService = new TeleportService();
        this.teleportRequestService = new TeleportRequestService();
    }

    @Override
    public void enable() {
        this.registerCommands(
                new TeleportCommand(),
                new TeleportAcceptCommand(),
                new TeleportDeclineCommand()
        );

        this.registerListeners(
                new PlayerItemConsumeListener(),
                new BlockPlaceListener(),
                new BlockBreakListener()
        );

        this.registerThreads(
                new TeleportTask(this.getServer())
        );
    }

    @Override
    public void disable() {

    }

    public static BukkitPlugin getInstance() {
        return getPlugin(BukkitPlugin.class);
    }
}
