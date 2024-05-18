package cc.dreamcode.teleport.listener.player;

import cc.dreamcode.teleport.BukkitPluginAdapter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;

public class PlayerItemConsumeListener extends BukkitPluginAdapter implements Listener {

    @EventHandler
    public void onPlayerItemConsume(PlayerItemConsumeEvent event) {
        final Player player = event.getPlayer();

        if (this.teleportService.isInTeleport(player.getUniqueId())) {
            if (this.settingsData.isCancelTeleportIfPlayerHasEating()) {
                this.teleportService.removeTeleport(player.getUniqueId());

                this.chatHelper.sendMessage(player, this.messagesConfig.getCancelledTeleportation());
            }
        }
    }
}
