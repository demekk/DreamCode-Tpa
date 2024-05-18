package cc.dreamcode.teleport.data;

import lombok.Getter;
import org.bukkit.Location;

import java.util.UUID;

@Getter
public class Teleport {

    private final UUID uuid;

    private final Location startLocation;
    private final Location destinationLocation;

    private final long time;

    public Teleport(UUID uuid, Location startLocation, Location destinationLocation, int seconds) {
        this.uuid = uuid;

        this.startLocation = startLocation;
        this.destinationLocation = destinationLocation;

        this.time = System.currentTimeMillis() + 1000L * seconds;
    }
}
