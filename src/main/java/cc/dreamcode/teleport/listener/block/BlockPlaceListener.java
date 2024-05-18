package cc.dreamcode.teleport.listener.block;

import cc.dreamcode.teleport.BukkitPluginAdapter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlaceListener extends BukkitPluginAdapter implements Listener {

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        final Player player = event.getPlayer();

        if (this.teleportService.isInTeleport(player.getUniqueId())) {
            if (this.settingsData.isCancelTeleportIfPlayerPlaceBlock()) {
                this.teleportService.removeTeleport(player.getUniqueId());

                this.chatHelper.sendMessage(player, this.messagesConfig.getCancelledTeleportation());
            }
        }
    }
}
