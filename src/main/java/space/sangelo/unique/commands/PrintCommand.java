package space.sangelo.unique.commands;

import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import space.sangelo.unique.Unique;
import space.sangelo.unique.util.MessageUtil;

public class PrintCommand implements CommandExecutor {
	private final Unique plugin;

	private final MessageUtil messageUtil;

	public PrintCommand(final Unique plugin) {
		this.plugin = plugin;
		this.messageUtil = plugin.getMessageUtil();
	}

	@Override
	public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
		if (sender instanceof Player player) {
			if (args.length == 0) {
				messageUtil.sendMessageWithPrefix(player, "no-args-msg", Placeholder.unparsed("command", "print {message}"));
			} else if (args.length == 1) {
				final String printMessage = args[0];
				messageUtil.sendMessageWithPrefix(player, "print-msg", Placeholder.parsed("message", printMessage));
			} else {
				final StringBuilder builder = new StringBuilder();
				for (int i = 0; i < args.length; i++) {
					builder.append(args[i]);
					builder.append(" ");
				}
				String printFinalMessage = builder.toString();
				printFinalMessage = printFinalMessage.stripTrailing();
				messageUtil.sendMessageWithPrefix(player, "print-msg", Placeholder.parsed("message", printFinalMessage));
			}
		}
		return true;
	}
}
