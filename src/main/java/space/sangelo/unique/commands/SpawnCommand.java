// Warning! Very duplicate code bc i don't have a clue.
package space.sangelo.unique.commands;

import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import space.sangelo.unique.Unique;
import space.sangelo.unique.util.MessageUtil;

import java.util.HashMap;
import java.util.UUID;

public class SpawnCommand implements CommandExecutor {
	private final Unique plugin;
	private final MessageUtil messageUtil;

	private final HashMap<UUID, Long> cooldown;

	public SpawnCommand(final Unique plugin) {
		this.plugin = plugin;
		this.messageUtil = plugin.getMessageUtil();
		this.cooldown = new HashMap<>();
	}


	@Override
	public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {

		final Location location = plugin.getConfig().getLocation("spawn");

		if (sender instanceof Player player) {
			if (!player.hasPermission("unique.moderation.bypass.cooldown")) {
				if (!this.cooldown.containsKey(player.getUniqueId())) {
					this.cooldown.put(player.getUniqueId(), System.currentTimeMillis());
					if (location != null) {
						player.teleport(location);
						messageUtil.sendMessageWithPrefix(player, "spawn-tp-msg");
					} else {
						if (!player.hasPermission("unique.moderation.setspawn")) {
							messageUtil.sendMessageWithPrefix(player, "spawn-no-location-msg");
						} else {
							messageUtil.sendMessageWithPrefix(player, "spawn-mod-no-location-msg");
						}
					}
				} else {
					final long timeElapsedMs = System.currentTimeMillis() - cooldown.get(player.getUniqueId());
					final long timeElapsed = (timeElapsedMs / 1000) % 60;
					if (timeElapsed >= plugin.getConfig().getLong("spawn-command-cooldown")) {
						this.cooldown.put(player.getUniqueId(), System.currentTimeMillis());
						if (player.hasPermission("unique.moderation.setspawn")) {
							if (location != null) {
								player.teleport(location);
								messageUtil.sendMessageWithPrefix(player, "spawn-tp-msg");
							} else {
								if (player.hasPermission("unique.moderation.setspawn")) {
									messageUtil.sendMessageWithPrefix(player, "spawn-mod-no-location-msg");
								} else {
									messageUtil.sendMessageWithPrefix(player, "spawn-no-location-msg");
								}
							}
						} else {
							messageUtil.sendMessageWithPrefix(player, "no-perms-msg");
						}
					} else {
						messageUtil.sendMessageWithPrefix(player, "spawn-mod-no-location-msg", Placeholder.unparsed("remaining", String.valueOf(plugin.getConfig().getLong("spawn-command-cooldown") - timeElapsed)));
					}
				}
			} else {
				if (player.hasPermission("unique.general.spawn")) {
					if (location != null) {
						player.teleport(location);
						messageUtil.sendMessageWithPrefix(player, "spawn-tp-msg");
					} else {
						if (player.hasPermission("unique.moderation.setspawn")) {
							messageUtil.sendMessageWithPrefix(player, "spawn-mod-no-location-msg");
						} else {
							messageUtil.sendMessageWithPrefix(player, "spawn-no-location-msg");
						}
					}
				} else {
					messageUtil.sendMessageWithPrefix(player, "no-perms-msg");
				}
			}
		}
		return true;
	}
}
