package cc.dreamcode.teleport.command;

import cc.dreamcode.teleport.helper.command.impl.AbstractCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TeleportCommand extends AbstractCommand {

    public TeleportCommand() {
        super("tpa", "/tpa <nick gracza>", "", true);
    }

    @Override
    public boolean execute(@NotNull CommandSender commandSender, @NotNull String[] args) {
        final Player player = (Player) commandSender;

        final Player receiver = Bukkit.getPlayer(args[0]);

        if (receiver == null) {
            return this.getCommandAdapter().getChatHelper().sendMessage(player, this.getCommandAdapter().getMessagesConfig().getReceiverIsNotExists());
        }

        if (this.getCommandAdapter().getTeleportRequestService().hasRequest(player.getUniqueId(), receiver.getUniqueId())) {
            return this.getCommandAdapter().getChatHelper().sendMessage(player, this.getCommandAdapter().getMessagesConfig().getYourRequestAlreadyExists());
        }

        //exists
        this.getCommandAdapter().getTeleportRequestService().createRequest(player.getUniqueId(), receiver.getUniqueId());

        this.getCommandAdapter().getChatHelper().sendMessage(player, this.getCommandAdapter().getMessagesConfig().getSuccessfulSentInvite().replace("{RECEIVER}", receiver.getName()));
        this.getCommandAdapter().getMessagesConfig().getReceiverHasRequest().forEach(message -> this.getCommandAdapter().getChatHelper().sendMessage(receiver, message.replace("{REQUESTER}", player.getName())));
        return false;
    }
}
