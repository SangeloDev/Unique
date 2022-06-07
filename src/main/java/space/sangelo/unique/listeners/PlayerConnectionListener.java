package space.sangelo.unique.listeners;

import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import space.sangelo.unique.Unique;
import space.sangelo.unique.util.MessageUtil;

public class PlayerConnectionListener implements Listener {
	private final Unique plugin;
	private final MessageUtil messageUtil;

	public PlayerConnectionListener(final Unique plugin) {
		this.plugin = plugin;
		this.messageUtil = plugin.getMessageUtil();
	}

	@EventHandler
	public void onJoin(final PlayerJoinEvent event) {
		event.joinMessage(messageUtil.getMessage("join-msg", Placeholder.component("player", event.getPlayer().displayName())));
	}

	@EventHandler
	public void onLeave(final PlayerQuitEvent event) {
		event.quitMessage(messageUtil.getMessage("quit-msg", Placeholder.component("player", event.getPlayer().displayName())));
	}
}
