package space.sangelo.unique.commands;

import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import space.sangelo.unique.Unique;
import space.sangelo.unique.util.MessageUtil;

public class FlyCommand implements CommandExecutor {
	private final MessageUtil messageUtil;
	private final Unique plugin;

	public FlyCommand(final Unique plugin) {
		this.plugin = plugin;
		this.messageUtil = plugin.getMessageUtil();
	}

	@Override
	public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
		if (sender instanceof Player player)
			if (args.length == 0) {
				if (player.hasPermission("unique.moderation.fly")) {
					if (player.getAllowFlight()) {
						player.setAllowFlight(false);
						messageUtil.sendMessageWithPrefix(player, "flight-disabled-msg");
					} else {
						player.setAllowFlight(true);
						messageUtil.sendMessageWithPrefix(player, "flight-enabled-msg");
					}
				} else {
					messageUtil.sendMessageWithPrefix(player, "no-perms-msg");
				}
			} else {
				final String playerName = args[0];
				final Player target = Bukkit.getServer().getPlayerExact(playerName);
				if (player.hasPermission("unique.moderation.fly.set")) {
					if (target == null) {
						messageUtil.sendMessageWithPrefix(player, "player-not-online-msg");
					} else {
						if (target.getAllowFlight()) {
							target.setAllowFlight(false);
							messageUtil.sendMessageWithPrefix(player, "flight-disabled-target-msg", Placeholder.component("player", player.displayName()));
							messageUtil.sendMessageWithPrefix(player, "flight-disabled-others-msg", Placeholder.component("target", target.displayName()));
						} else {
							target.setAllowFlight(true);
							messageUtil.sendMessageWithPrefix(player, "flight-enabled-target-msg", Placeholder.component("player", player.displayName()));
							messageUtil.sendMessageWithPrefix(player, "flight-enabled-others-msg", Placeholder.component("target", target.displayName()));
						}
					}
				} else {
					messageUtil.sendMessageWithPrefix(player, "no-perms-msg");
				}
			}
		return true;
	}
}
