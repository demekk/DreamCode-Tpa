package cc.dreamcode.teleport.helper.command.impl;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.CommandMap;

import java.lang.reflect.Field;

@Getter
@Setter
public class CommandManager {

    private CommandMap commandMap = tryGetCommandMap();

    public void registerNewCommand(AbstractCommand command) {
        this.commandMap.register(command.getName(), command);
    }

    private CommandMap tryGetCommandMap() {
        try {
            Server server = Bukkit.getServer();
            Field commandMapField = server.getClass().getDeclaredField("commandMap");
            commandMapField.setAccessible(true);
            CommandMap commandMap = (CommandMap) commandMapField.get(server);
            if (commandMap == null) {
                throw new IllegalStateException("command map instance is not yet set.");
            }

            return commandMap;
        } catch (IllegalAccessException | NoSuchFieldException e) {
            throw new IllegalStateException("cannot get the command map.", e);
        }
    }
}