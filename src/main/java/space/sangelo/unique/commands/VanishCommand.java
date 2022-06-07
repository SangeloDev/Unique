package space.sangelo.unique.commands;

import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import space.sangelo.unique.Unique;
import space.sangelo.unique.util.MessageUtil;
import space.sangelo.unique.util.VanishManager;

public class VanishCommand implements CommandExecutor {

	private final Unique plugin;
	private final VanishManager vanishManager;
	private final MessageUtil messageUtil;

	public VanishCommand(final Unique plugin) {
		this.plugin = plugin;
		this.vanishManager = plugin.getVanishManager();
		this.messageUtil = plugin.getMessageUtil();
	}

	@Override
	public boolean onCommand(final CommandSender sender, final Command cmd, final String commandLabel, final String[] args) {
		if (!(sender instanceof final Player player)) return false;
		if (args.length > 0) {
			if (args[0].equalsIgnoreCase("list")) {
				if (!player.hasPermission("unique.moderation.vanish.list")) {
					messageUtil.sendMessageWithPrefix(player, "no-perms-msg");
					return true;
				}
				if (vanishManager.getHiddenUsernames().size() > 0) {
					final StringBuilder builder = new StringBuilder();
					for (int i = 0; i < vanishManager.getHiddenUsernames().size(); i++) {
						builder.append(vanishManager.getHiddenUsernames().get(i));
						if (i < vanishManager.getHiddenUsernames().size() - 1) {
							builder.append(", ");
						}
					}
					messageUtil.sendMessageWithPrefix(player, "vanish-hidden-list-msg", Placeholder.unparsed("playerlist", String.valueOf(builder)));
				} else {
					messageUtil.sendMessageWithPrefix(player, "vanish-hidden-none");
				}
				return true;
			} else if (args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("?")) {
				return false;
			} else if (player.hasPermission("unique.moderation.vanish.other")) {
				Player target = plugin.getServer().getPlayer(args[0]);
				if (target == null) target = plugin.getServer().getPlayerExact(args[0]);
				if (target == null) {
					messageUtil.sendMessageWithPrefix(player, "player-not-online-msg");
					return true;
				}

				if (!vanishManager.isVanished(target)) {
					vanishManager.vanishPlayer(target);
					messageUtil.sendMessageWithPrefix(player, "vanish-enabled-msg");
					messageUtil.sendMessageWithPrefix(player, "vanish-enabled-others-msg", Placeholder.component("player", player.displayName()));
					plugin.getServer().broadcast(messageUtil.getMessage("quit-msg", Placeholder.component("player", player.displayName())));
					return true;
				} else {
					vanishManager.showPlayer(target);
					messageUtil.sendMessageWithPrefix(player, "vanish-disabled-msg");
					messageUtil.sendMessageWithPrefix(player, "vanish-disabled-others-msg", Placeholder.component("player", player.displayName()));
					plugin.getServer().broadcast(messageUtil.getMessage("join-msg", Placeholder.component("player", player.displayName())));
					return true;
				}
			}
		} else if (player.hasPermission("unique.moderation.vanish.use")) {
			if (!vanishManager.isVanished(player)) {
				vanishManager.vanishPlayer(player);
				messageUtil.sendMessageWithPrefix(player, "vanish-enabled-msg");
				plugin.getServer().broadcast(messageUtil.getMessage("quit-msg", Placeholder.component("player", player.displayName())));
				return true;
			} else {
				vanishManager.showPlayer(player);
				messageUtil.sendMessageWithPrefix(player, "vanish-disabled-msg");
				plugin.getServer().broadcast(messageUtil.getMessage("join-msg", Placeholder.component("player", player.displayName())));
				return true;
			}
		}
		return false;
	}
}
