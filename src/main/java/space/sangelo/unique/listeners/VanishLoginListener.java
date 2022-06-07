package space.sangelo.unique.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import space.sangelo.unique.Unique;
import space.sangelo.unique.util.VanishManager;

public class VanishLoginListener implements Listener {
	private final Unique plugin;
	private final VanishManager vanishManager;

	public VanishLoginListener(final Unique plugin) {
		this.plugin = plugin;
		this.vanishManager = plugin.getVanishManager();
	}

	@EventHandler
	public void handleLogin(final PlayerLoginEvent event) {
		final Player player = event.getPlayer();

		if (vanishManager.isVanished(player)) {
			vanishManager.showPlayer(player);
		}
		if (player.hasPermission("unique.moderation.vanish.see")) return;
		for (final Player p1 : plugin.getServer().getOnlinePlayers()) {
			if (vanishManager.isVanished(p1) && !player.equals(p1)) {
				player.hidePlayer(plugin, p1);
			}
		}
	}

	@EventHandler
	public void handleQuit(final PlayerQuitEvent event) {
		final Player player = event.getPlayer();

		if (vanishManager.isVanished(player)) {
			vanishManager.showPlayer(player);
		}
	}
}