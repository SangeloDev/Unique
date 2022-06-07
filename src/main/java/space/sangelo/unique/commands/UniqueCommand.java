package space.sangelo.unique.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import space.sangelo.unique.Unique;

public class UniqueCommand implements CommandExecutor {
	private final Unique plugin;

	public UniqueCommand(final Unique plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
		if (sender instanceof Player player) {
//			p.sendMessage(ChatColor.DARK_GREEN + "-=-=-=-=-=-=-");
			player.sendMessage(ChatColor.DARK_GREEN + "Unique - " + ChatColor.GOLD + "v" + plugin.getDescription().getVersion());
//			p.sendMessage(ChatColor.DARK_GREEN + "-=-=-=-=-=-=-");
			player.sendMessage(ChatColor.DARK_GRAY + "Author: " + ChatColor.AQUA + "Sangelo");
			player.sendMessage(ChatColor.DARK_GRAY + "Website: " + ChatColor.BLUE + "" + ChatColor.UNDERLINE + "https://sangelo.space");
			player.sendMessage(ChatColor.DARK_GREEN + "For help, type " + ChatColor.GOLD + "/help unique");
		}
		return true;
	}
}
