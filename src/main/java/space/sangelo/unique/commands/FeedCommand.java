package space.sangelo.unique.commands;

import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import space.sangelo.unique.Unique;
import space.sangelo.unique.util.MessageUtil;

public class FeedCommand implements CommandExecutor {
	private final MessageUtil messageUtil;

	public FeedCommand(final Unique plugin) {
		this.messageUtil = plugin.getMessageUtil();
	}

	@Override
	public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
		if (sender instanceof Player player) {
			if (args.length == 0) {
				if (player.hasPermission("unique.moderation.feed")) {
					player.setFoodLevel(20);
					player.setSaturation(20);
					messageUtil.sendMessageWithPrefix(player, "feed-msg");
				} else {
					messageUtil.sendMessageWithPrefix(player, "no-perms-msg");
				}
			} else {
				final String playerName = args[0];
				final Player target = Bukkit.getServer().getPlayerExact(playerName);
				if (target == null) {
					messageUtil.sendMessageWithPrefix(player, "player-not-online-msg");
				} else {
					if (player.hasPermission("unique.moderation.feed.others")) {
						target.setFoodLevel(20);
						target.setSaturation(20);
						messageUtil.sendMessageWithPrefix(target, "feed-target-msg", Placeholder.component("player", player.displayName()));
						messageUtil.sendMessageWithPrefix(player, "feed-others-msg", Placeholder.component("target", target.displayName()));
					} else {
						messageUtil.sendMessageWithPrefix(player, "no-perms-msg");
					}
				}
			}
		}
		return true;
	}
}