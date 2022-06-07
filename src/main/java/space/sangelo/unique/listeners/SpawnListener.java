package space.sangelo.unique.listeners;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import space.sangelo.unique.Unique;

public class SpawnListener implements Listener {
	private final Unique plugin;

	public SpawnListener(final Unique plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onPlayerJoin(final PlayerJoinEvent e) {
		// Spawn the player on the spawn location on first join, if one's set.
		final Player p = e.getPlayer();
		if (!e.getPlayer().hasPlayedBefore()) {
			final Location location = plugin.getConfig().getLocation("spawn");
			if (location != null) {
				p.teleport(location);
			}
		}
	}

	@EventHandler
	public void onPlayerRespawn(final PlayerRespawnEvent e) {
		// Respawn the player at the spawn location, if one's set.
		final Location location = plugin.getConfig().getLocation("spawn");
		if (location != null) {
			if (e.getPlayer().getBedSpawnLocation() == null) {
				e.setRespawnLocation(location);
			}
		}
	}
}
