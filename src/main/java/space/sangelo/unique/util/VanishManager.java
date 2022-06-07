package space.sangelo.unique.util;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import space.sangelo.unique.Unique;

import java.util.ArrayList;

public class VanishManager {
	private final ArrayList<String> hiddenUsernames;

	private final Unique plugin;

	public VanishManager(final Unique plugin) {
		this.plugin = plugin;
		this.hiddenUsernames = new ArrayList<>();
	}

	public boolean isVanished(final Player player) {
		return hiddenUsernames.contains(player.getName());
	}

	public void vanishPlayer(final Player p) {
		final String vanishModLogsMsg = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("prefix") + plugin.getConfig().getString("vanish-mod-logs-msg"));
		hiddenUsernames.add(p.getName());
		for (final Player p1 : plugin.getServer().getOnlinePlayers()) {
			if (p1 == p) {
				continue;
			} else if (p1.hasPermission("unique.moderation.vanish.see")) {
				p1.sendMessage(vanishModLogsMsg.replace("%p", p.getName()));
				continue;
			} else if (p1.hasPermission("unique.moderation.vanish.list")) {
				p1.hidePlayer(plugin, p);
				p1.sendMessage(vanishModLogsMsg.replace("%p", p.getName()));
				continue;
			}
			p1.hidePlayer(plugin, p);
		}
	}

	public void showPlayer(final Player player) {
		hiddenUsernames.remove(player.getName());
		for (final Player p1 : plugin.getServer().getOnlinePlayers()) {
			p1.showPlayer(plugin, player);
		}
	}

	public ArrayList<String> getHiddenUsernames() {
		return hiddenUsernames;
	}
}
