package space.sangelo.unique.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import space.sangelo.unique.Unique;
import space.sangelo.unique.util.VanishManager;

public class VanishPlayerListener implements Listener {

	private final VanishManager vanishManager;

	public VanishPlayerListener(final Unique plugin) {
		this.vanishManager = plugin.getVanishManager();
	}

	@EventHandler
	public void onEntityTarget(final EntityTargetEvent event) {
		if (event.getEntity() instanceof Player player && vanishManager.isVanished(player)) {
			event.setCancelled(true);
		}
	}


	@EventHandler
	public void onDamageByBlock(final EntityDamageByBlockEvent event) {
		if (event.getEntity() instanceof Player player && vanishManager.isVanished(player)) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onDamageByEntity(final EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof Player player && vanishManager.isVanished(player)) {
			event.setCancelled(true);
		} else if (event.getEntity() instanceof Player player && vanishManager.isVanished(player)) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onItemPickup(final EntityPickupItemEvent event) {
		if (event.getEntity() instanceof Player player && vanishManager.isVanished(player)) {
			event.setCancelled(true);
		}
	}
}
