package space.sangelo.unique.commands;

import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import space.sangelo.unique.Unique;
import space.sangelo.unique.util.MessageUtil;

public class GamemodeSCommand implements CommandExecutor {
	private final Unique plugin;
	private final MessageUtil messageUtil;

	public GamemodeSCommand(final Unique plugin) {
		this.plugin = plugin;
		this.messageUtil = plugin.getMessageUtil();
	}

	@Override
	public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
		if (sender instanceof Player player && args.length == 0) {
			survivalPlayerSelf(player);
		} else if (sender instanceof Player player) {
			final Player target = Bukkit.getPlayerExact(args[0]);
			if (player.hasPermission("unique.moderation.gamemode.survival.others")) {
				if (player == target) {
					player.setGameMode(GameMode.SURVIVAL);
					messageUtil.sendMessageWithPrefix(player, "gamemode-switcher-msg", Placeholder.unparsed("gamemode", "survival"));
				} else {
					target.setGameMode(GameMode.SURVIVAL);
					messageUtil.sendMessageWithPrefix(player, "gamemode-switcher-others-msg", Placeholder.unparsed("gamemode", "survival"), Placeholder.component("target", target.displayName()));
					messageUtil.sendMessageWithPrefix(target, "gamemode-switcher-msg", Placeholder.unparsed("gamemode", "survival"));
				}
			}
		}
		if (sender instanceof ConsoleCommandSender && args.length == 0) {
			messageUtil.sendMessageWithPrefix(sender, "console-specify-player");
		}
		return true;
	}

	public void survivalPlayerSelf(final Player player) {
		if (player.getGameMode() == GameMode.SURVIVAL) {
			if (player.hasPermission("unique.moderation.gamemode.survival.self")) {
				messageUtil.sendMessageWithPrefix(player, "gamemode-already-set-msg", Placeholder.unparsed("gamemode", "survival"));
			} else {
				messageUtil.sendMessageWithPrefix(player, "no-perms-msg");
			}
		} else if (player.hasPermission("unique.moderation.gamemode.survival.self")) {
			player.setGameMode(GameMode.SURVIVAL);
			messageUtil.sendMessageWithPrefix(player, "gamemode-switcher-msg", Placeholder.unparsed("gamemode", "survival"));
		} else {
			messageUtil.sendMessageWithPrefix(player, "no-perms-msg");
		}
	}
}
