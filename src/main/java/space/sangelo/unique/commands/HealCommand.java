package space.sangelo.unique.commands;

import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import space.sangelo.unique.Unique;
import space.sangelo.unique.util.MessageUtil;

import static org.bukkit.attribute.Attribute.GENERIC_MAX_HEALTH;

public class HealCommand implements CommandExecutor {
	private final Unique plugin;
	private final MessageUtil messageUtil;

	public HealCommand(final Unique plugin) {
		this.plugin = plugin;
		this.messageUtil = plugin.getMessageUtil();
	}

	@Override
	public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
		if (sender instanceof Player player) {
			if (args.length == 0) {
				if (player.hasPermission("unique.moderation.heal")) {
					player.setHealth(player.getAttribute(GENERIC_MAX_HEALTH).getDefaultValue());
					messageUtil.sendMessageWithPrefix(player, "heal-msg");
				} else {
					messageUtil.sendMessageWithPrefix(player, "no-perms-msg");
				}
			} else {
				final String playerName = args[0];
				final Player target = Bukkit.getServer().getPlayerExact(playerName);
				if (target == null) {
					messageUtil.sendMessageWithPrefix(player, "player-not-online-msg");
				} else {
					if (player.hasPermission("unique.moderation.heal.others")) {
						target.setHealth(target.getAttribute(GENERIC_MAX_HEALTH).getDefaultValue());
						messageUtil.sendMessageWithPrefix(target, "heal-target-msg", Placeholder.component("player", player.displayName()));
						messageUtil.sendMessageWithPrefix(player, "heal-others-msg", Placeholder.component("target", target.displayName()));
					} else {
						messageUtil.sendMessageWithPrefix(player, "no-perms-msg");
					}
				}
			}
		}
		return true;
	}
}