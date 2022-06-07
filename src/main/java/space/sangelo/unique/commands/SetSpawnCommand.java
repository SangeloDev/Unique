package space.sangelo.unique.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import space.sangelo.unique.Unique;
import space.sangelo.unique.util.MessageUtil;

public class SetSpawnCommand implements CommandExecutor {
	private final Unique plugin;
	private final MessageUtil messageUtil;

	public SetSpawnCommand(final Unique plugin) {
		this.plugin = plugin;
		this.messageUtil = plugin.getMessageUtil();
	}

	@Override
	public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
		if (sender instanceof Player player) {
			if (player.hasPermission("unique.moderation.setspawn")) {
				plugin.getConfig().set("spawn", player.getLocation());
				plugin.saveConfig();
				messageUtil.sendMessageWithPrefix(player, "setspawn-msg");
			} else {
				messageUtil.sendMessageWithPrefix(player, "no-perms-msg");
			}
		} else {
			messageUtil.sendMessageWithPrefix(sender, "console-not-allowed-msg");
		}
		return true;
	}
}