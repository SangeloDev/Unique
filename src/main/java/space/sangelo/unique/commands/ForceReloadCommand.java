package space.sangelo.unique.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import space.sangelo.unique.Unique;
import space.sangelo.unique.util.MessageUtil;


public class ForceReloadCommand implements CommandExecutor {
	private final Unique plugin;
	private final MessageUtil messageUtil;

	public ForceReloadCommand(final Unique plugin) {
		this.plugin = plugin;
		this.messageUtil = plugin.getMessageUtil();
	}

	@Override
	public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
		if (sender.hasPermission("unique.admin.forcereload")) {
			messageUtil.sendMessageWithPrefix(sender, "force-reload-msg");
			plugin.reloadConfig();
			messageUtil.sendMessageWithPrefix(sender, "force-reload-complete-msg");
		}
		return true;
	}
}
