package cc.dreamcode.teleport.command.impl;

import cc.dreamcode.teleport.helper.command.impl.AbstractCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TeleportDeclineCommand extends AbstractCommand {

    public TeleportDeclineCommand() {
        super("tpadeny", "/tpadeny <nick/all>", "", true);
    }

    @Override
    public boolean execute(@NotNull CommandSender commandSender, @NotNull String[] args) {
        final Player player = (Player) commandSender;

        final Player requester = Bukkit.getPlayer(args[0]);

        if (requester == null) {
            return this.getCommandAdapter().getChatHelper().sendMessage(player, this.getCommandAdapter().getMessagesConfig().getReceiverIsNotExists());
        }

        boolean hasRequest = this.getCommandAdapter().getTeleportRequestService().hasRequest(requester.getUniqueId(), player.getUniqueId());

        if (hasRequest) {
            this.getCommandAdapter().getTeleportRequestService().removeRequest(requester.getUniqueId());
            this.getCommandAdapter().getChatHelper().sendMessage(requester, this.getCommandAdapter().getMessagesConfig().getYourRequestHasDecline().replace("{RECEIVER}", player.getName()));
        } else {
            this.getCommandAdapter().getChatHelper().sendMessage(player, this.getCommandAdapter().getMessagesConfig().getHaveNotRequest());
        }

//        if (this.getCommandAdapter().getTeleportRequestService().hasRequest(requester.getUniqueId(), player.getUniqueId())) {
//            this.getCommandAdapter().getChatHelper().sendMessage(player, this.getCommandAdapter().getMessagesConfig().getSuccessfulAcceptedInvite().replace("{REQUESTER}", requester.getName()));
//        } else {
//            return this.getCommandAdapter().getChatHelper().sendMessage(player, this.getCommandAdapter().getMessagesConfig().getHaveNotRequest());
//        }

//        this.getCommandAdapter().getChatHelper().sendMessage(player, hasRequest ? this.getCommandAdapter().getMessagesConfig().getSuccessfulAcceptedInvite().replace("{REQUESTER}", requester.getName()) : this.getCommandAdapter().getMessagesConfig().getHaveNotRequest());

        return false;
    }
}
