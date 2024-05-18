package cc.dreamcode.teleport;

import cc.dreamcode.teleport.helper.ReflectionHelper;
import cc.dreamcode.teleport.helper.thread.Schedulable;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public class BukkitPluginDelegator extends JavaPlugin {

    private final PluginManager pluginManager = Bukkit.getPluginManager();

    private final SimpleCommandMap commandMap = ReflectionHelper.getField(PluginManager.class, "commandMap", SimpleCommandMap.class).get(Bukkit.getPluginManager());;

    public void load() {}
    public void enable() {}
    public void disable() {}

    @Deprecated
    @Override
    public void onEnable() {
        this.enable();
    }

    @Deprecated
    @Override
    public void onDisable() {
        this.disable();
    }

    @Deprecated
    @Override
    public void onLoad() {
        this.load();
    }

    public void registerListeners(Listener... listeners) {
        for (Listener listener : listeners) {
            this.pluginManager.registerEvents(listener, this);
        }
    }

    public void registerCommands(Command... commands) {
        for (Command command : commands) {
            this.commandMap.register("dreamcode", command);
        }
    }

    public void registerThreads(Schedulable... threads) {
        Arrays.stream(threads).forEach(Schedulable::start);
    }
}


