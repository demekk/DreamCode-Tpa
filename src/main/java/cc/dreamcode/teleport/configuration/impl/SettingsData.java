package cc.dreamcode.teleport.configuration.impl;

import cc.dreamcode.teleport.type.TeleportEffectType;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.configs.annotation.Header;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Header("settings config")
@Getter
public class SettingsData extends OkaeriConfig {

    @Comment("You can add a lot of permissions!")
    public final Map<String, Integer> teleportationTimes = new HashMap<String, Integer>() {{
        put("teleport.player", 5);
        put("teleport.vip", 3);
    }};

    public final String teleportAdminPermission = "teleport.admin";

    public final boolean cancelTeleportIfPlayerHasEating = true;

    public final boolean cancelTeleportIfPlayerPlaceBlock = true;
    public final boolean cancelTeleportIfPlayerBreakBlock = true;

    public final boolean activeEffectsOnTeleportation = false;
    @Comment("Allow types: NONE")
    public final TeleportEffectType teleportEffectType = TeleportEffectType.NONE;
}
