package cc.dreamcode.teleport.helper;

import cc.dreamcode.teleport.type.TeleportMessageType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public final class ChatHelper {

    private String nmsVersion;

    public boolean sendMessage(Player player, String s) {
        player.sendMessage(colored(s));
        return false;
    }

    public boolean sendMessage(List<Player> players, String s) {
        players.forEach(player -> player.sendMessage(colored(s)));
        return false;
    }

    public boolean sendMessage(CommandSender sender, String s) {
        sender.sendMessage(colored(s));
        return false;
    }

    public boolean sendMessage(Player player, TeleportMessageType messageType, String s) {
        switch (messageType) {
            case CHAT: {
                player.sendMessage(this.colored(s));
                break;
            }
            case ACTIONBAR: {
                this.sendActionBar(player, this.colored(s));
                break;
            }
        }
        return false;
    }

    public String colored(String message) {
        return ChatColor.translateAlternateColorCodes('&', message.replace(">>", "»"));
    }

    public List<String> colored(List<String> texts) {
        return texts.stream().map(this::colored).collect(Collectors.toList());
    }

    public String colored(Set<String> texts) {
        return texts.stream().map(this::colored).collect(Collectors.joining());
    }

    public boolean isInteger(final String string) {
        return Pattern.matches("-?[0-9]+", string.subSequence(0, string.length()));
    }

    public void sendActionBar(Player player, String message) {
        if(nmsVersion == null) {
            nmsVersion = (nmsVersion = Bukkit.getServer().getClass().getPackage().getName()).substring(nmsVersion.lastIndexOf(".") + 1);
        }
        try {
            Class<?> c1 = Class.forName("org.bukkit.craftbukkit." + nmsVersion + ".entity.CraftPlayer");
            Object p = c1.cast(player);
            Object ppoc;
            Class<?> c2, c3,
                    c4 = Class.forName("net.minecraft.server." + nmsVersion + ".PacketPlayOutChat"),
                    c5 = Class.forName("net.minecraft.server." + nmsVersion + ".Packet");
            Object o;
            if ((nmsVersion.equalsIgnoreCase("v1_8_R1") || !nmsVersion.startsWith("v1_8_")) && !nmsVersion.startsWith("v1_9_")) {
                c2 = Class.forName("net.minecraft.server." + nmsVersion + ".ChatSerializer");
                c3 = Class.forName("net.minecraft.server." + nmsVersion + ".IChatBaseComponent");
                Method m3 = c2.getDeclaredMethod("a", String.class);
                o = c3.cast(m3.invoke(c2, "{\"text\": \"" + message + "\"}"));
            } else {
                c2 = Class.forName("net.minecraft.server." + nmsVersion + ".ChatComponentText");
                c3 = Class.forName("net.minecraft.server." + nmsVersion + ".IChatBaseComponent");
                o = c2.getConstructor(new Class<?>[]{String.class}).newInstance(message);
            }
            ppoc = c4.getConstructor(new Class<?>[]{c3, byte.class}).newInstance(o, (byte) 2);
            Method m1 = c1.getDeclaredMethod("getHandle");
            Object h = m1.invoke(p);
            Field f1 = h.getClass().getDeclaredField("playerConnection");
            Object pc = f1.get(h);
            Method m5 = pc.getClass().getDeclaredMethod("sendPacket", c5);
            m5.invoke(pc, ppoc);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
