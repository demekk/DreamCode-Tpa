package cc.dreamcode.teleport.command.impl;

import cc.dreamcode.teleport.helper.command.impl.AbstractCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.UUID;

public class TeleportAcceptCommand extends AbstractCommand {

    public TeleportAcceptCommand() {
        super("tpaccept", "/tpaccept <nick/all>", "", true);
    }

    @Override
    public boolean execute(@NotNull CommandSender commandSender, @NotNull String[] args) {
        final Player player = (Player) commandSender;

        if (args[0].equalsIgnoreCase("all")) {
            List<UUID> requesters = this.getCommandAdapter().getTeleportRequestService().findRequests(player.getUniqueId());

            if (requesters.isEmpty()) {
                return this.getCommandAdapter().getChatHelper().sendMessage(player, this.getCommandAdapter().getMessagesConfig().getHaveNotAnyRequest());
            }

            teleportRequesters(player, requesters);
            return true;
        }

        final Player requester = Bukkit.getPlayer(args[0]);

        if (requester == null) {
            return this.getCommandAdapter().getChatHelper().sendMessage(player, this.getCommandAdapter().getMessagesConfig().getReceiverIsNotExists());
        }

        if (this.getCommandAdapter().getTeleportRequestService().hasRequest(requester.getUniqueId(), player.getUniqueId())) {
            return this.getCommandAdapter().getChatHelper().sendMessage(player, this.getCommandAdapter().getMessagesConfig().getSuccessfulAcceptedInvite().replace("{REQUESTER}", requester.getName()));
        }

        this.getCommandAdapter().getSettingsData().getTeleportationTimes().entrySet().stream()
                .filter(entry -> requester.hasPermission(entry.getKey()))
                .forEach(entry -> {
                    this.getCommandAdapter().getTeleportService().createTeleport(
                            requester.getUniqueId(),
                            requester.getLocation(),
                            player.getLocation(),
                            entry.getValue()
                    );
                });

        return false;
    }

    private void teleportRequesters(Player player, List<UUID> requesters) {
        requesters.forEach(uuid -> {
            Player requester = Bukkit.getPlayer(uuid);

            if (requester == null) {
                this.getCommandAdapter().getChatHelper().sendMessage(player, this.getCommandAdapter().getMessagesConfig().getReceiverIsNotExists());
                return;
            }

            this.getCommandAdapter().getSettingsData().getTeleportationTimes().entrySet().stream()
                    .filter(entry -> requester.hasPermission(entry.getKey()))
                    .forEach(entry -> {
                        this.getCommandAdapter().getTeleportService().createTeleport(
                                requester.getUniqueId(),
                                requester.getLocation(),
                                player.getLocation(),
                                entry.getValue()
                        );
                    });
        });
    }
}
