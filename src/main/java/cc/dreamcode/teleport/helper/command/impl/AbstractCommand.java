package cc.dreamcode.teleport.helper.command.impl;

import cc.dreamcode.teleport.helper.ChatHelper;
import cc.dreamcode.teleport.helper.command.CommandAdapter;
import lombok.Getter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

@Getter
public abstract class AbstractCommand extends Command {

    private final String name;
    private final String usage;
    private final String permission;
    private final boolean requiresPlayer;

    private final CommandAdapter commandAdapter;

    protected AbstractCommand(String name, String usage, String permission, boolean requiresPlayer, String... aliases) {
        super(name);
        this.name = name;
        this.usage = usage;
        this.permission = permission;
        this.setPermission(permission);
        this.requiresPlayer = requiresPlayer;
        this.setAliases(Arrays.asList(aliases));
        this.setPermissionMessage(new ChatHelper().colored("&cNie posiadasz uprawnień do wykonania tej czynności!"));

        this.commandAdapter = new CommandAdapter();
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        if (!(commandSender instanceof Player) && this.requiresPlayer) {
            commandSender.sendMessage("You have to been player to use this command!");
            return true;
        }

        if (!commandSender.hasPermission(this.getPermission())) {
            new ChatHelper().sendMessage(commandSender, this.getPermissionMessage());
            return false;
        }

        this.execute(commandSender, strings);

        return false;
    }

    public abstract boolean execute(CommandSender sender, String[] args);
}