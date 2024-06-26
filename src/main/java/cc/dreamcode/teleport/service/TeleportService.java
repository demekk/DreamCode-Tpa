package cc.dreamcode.teleport.service;

import cc.dreamcode.teleport.data.Teleport;
import org.bukkit.Location;
import panda.std.Option;

import java.util.*;

public class TeleportService {
    private final Map<UUID, Teleport> teleportMap = new HashMap<>();

    public void createTeleport(UUID uuid, Location startLocation, Location destinationLocation, int seconds){
        Teleport teleport = new Teleport(uuid, startLocation, destinationLocation, seconds);

        this.teleportMap.put(uuid, teleport);
    }

    public void removeTeleport(UUID uuid) {
        this.teleportMap.remove(uuid);
    }

    public Option<Teleport> findTeleport(UUID uuid) {
        return Option.of(this.teleportMap.get(uuid));
    }

    public boolean isInTeleport(UUID uuid) {
        Option<Teleport> teleportOption = this.findTeleport(uuid);

        if (teleportOption.isEmpty()) {
            return false;
        }

        Teleport teleport = teleportOption.get();
        if (System.currentTimeMillis() < teleport.getTime()) {
            return true;
        }

        this.removeTeleport(uuid);

        return false;
    }

    public Collection<Teleport> getTeleports() {
        return Collections.unmodifiableCollection(this.teleportMap.values());
    }
}