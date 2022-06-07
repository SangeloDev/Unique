package space.sangelo.unique.commands;

import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import space.sangelo.unique.Unique;
import space.sangelo.unique.util.MessageUtil;

public class GodCommand implements CommandExecutor {
	private final Unique plugin;
	private final MessageUtil messageUtil;

	public GodCommand(final Unique plugin) {
		this.plugin = plugin;
		this.messageUtil = plugin.getMessageUtil();
	}

	@Override
	public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
		if (sender instanceof Player player) {
			if (args.length == 0) {
				if (player.hasPermission("unique.moderation.god.use")) {
					if (player.isInvulnerable()) {
						player.setInvulnerable(false);
						messageUtil.sendMessageWithPrefix(player, "god-disabled-msg");
					} else {
						player.setInvulnerable(true);
						messageUtil.sendMessageWithPrefix(player, "god-enabled-msg");
					}
				} else {
					messageUtil.sendMessageWithPrefix(player, "no-perms-msg");
				}
			} else {
				final String playerName = args[0];
				final Player target = Bukkit.getServer().getPlayerExact(playerName);
				if (player.hasPermission("unique.moderation.god.set")) {
					if (target == null) {
						messageUtil.sendMessageWithPrefix(player, "player-not-online");
					} else {
						if (target.isInvulnerable()) {
							target.setInvulnerable(false);
							messageUtil.sendMessageWithPrefix(target, "god-disabled-target-msg", Placeholder.component("player", player.displayName()));
							messageUtil.sendMessageWithPrefix(player, "god-disabled-others-msg", Placeholder.component("target", target.displayName()));
						} else {
							target.setInvulnerable(true);
							messageUtil.sendMessageWithPrefix(target, "god-enabled-target-msg", Placeholder.component("player", player.displayName()));
							messageUtil.sendMessageWithPrefix(player, "god-enabled-others-msg", Placeholder.component("target", target.displayName()));
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