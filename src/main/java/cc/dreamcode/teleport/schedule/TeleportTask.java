package cc.dreamcode.teleport.schedule;

import cc.dreamcode.teleport.BukkitPluginAdapter;
import cc.dreamcode.teleport.data.Teleport;
import cc.dreamcode.teleport.helper.DataHelper;
import cc.dreamcode.teleport.helper.thread.Schedulable;
import cc.dreamcode.teleport.helper.thread.ThreadExecutor;

import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class TeleportTask extends BukkitPluginAdapter implements Schedulable {

    private final Server server;

    public TeleportTask(Server server) {
        this.server = server;
    }

    @Override
    public void start() {
        ThreadExecutor.SCHEDULER.scheduleAtFixedRate(() -> {
            for (Teleport teleport : this.teleportService.getTeleports()){
                Location destinationLocation = teleport.getDestinationLocation();
                Location startLocation = teleport.getStartLocation();
                UUID uuid = teleport.getUuid();
                long time = teleport.getTime();

                Player player = this.server.getPlayer(uuid);

                if (player == null) {
                    continue;
                }

                if (player.getLocation().distance(startLocation) > 0.5) {
                    this.teleportService.removeTeleport(uuid);

                    this.chatHelper.sendMessage(player, this.messagesConfig.getCancelledTeleportation());
                    continue;
                }

                if (!player.hasPermission(this.settingsData.getTeleportAdminPermission())) {
                    if (System.currentTimeMillis() < time) {
                        long actionTime = time - System.currentTimeMillis();

                        this.chatHelper.sendMessage(player, this.messagesConfig.getTeleportMessageType(), this.messagesConfig.getTeleportationInProgress().replace("{TIME}", DataHelper.durationToString(actionTime)));
                        continue;
                    }
                }

                player.teleport(destinationLocation);

                this.teleportService.removeTeleport(uuid);
                this.chatHelper.sendMessage(player, this.messagesConfig.teleportationWasBeSuccessful);
            }
        }, 1, 1, TimeUnit.SECONDS);
    }
}
