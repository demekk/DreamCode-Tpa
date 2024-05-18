package cc.dreamcode.teleport.listener.block;

import cc.dreamcode.teleport.BukkitPluginAdapter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreakListener extends BukkitPluginAdapter implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        final Player player = event.getPlayer();

        if (this.teleportService.isInTeleport(player.getUniqueId())) {
            if (this.settingsData.isCancelTeleportIfPlayerBreakBlock()) {
                this.teleportService.removeTeleport(player.getUniqueId());

                this.chatHelper.sendMessage(player, this.messagesConfig.getCancelledTeleportation());
            }
        }
    }
}
